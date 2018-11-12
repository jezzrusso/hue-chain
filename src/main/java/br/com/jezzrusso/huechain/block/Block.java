package br.com.jezzrusso.huechain.block;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.jezzrusso.huechain.config.HuechainProperties;
import br.com.jezzrusso.huechain.util.crypto.CryptoUtils;

public class Block {

	private final Long timestamp;
	private final String lastHash;
	private final String hash;
	private final String data;
	private final Long nonce;
	private final Integer difficulty;

	@JsonCreator
	public Block(@JsonProperty("timestamp") Long timestamp, @JsonProperty("lastHash") String lastHash,
			@JsonProperty("hash") String hash, @JsonProperty("data") String data, @JsonProperty("nonce") Long nonce,
			@JsonProperty("difficulty") Integer difficulty) {
		super();
		this.timestamp = timestamp;
		this.lastHash = lastHash;
		this.hash = hash;
		this.data = data;
		this.nonce = nonce;
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "{Block:{timestamp:" + timestamp + ",lastHash:" + lastHash + ",hash:" + hash + ",data:" + data
				+ ",nonce:" + nonce + ",difficulty:" + difficulty + "}}";
	}

	public static Block genesis() {
		return new Block(0L, "------", "g3n3515", "[]", 0L, 3);
	}

	public static Block mineBlock(final Block lastBlock, String data) {
		Integer difficulty = lastBlock.getDifficulty();
		String initObrigatory = new String(new char[difficulty]).replace("\0", "0");

		final String lastHash = lastBlock.getHash();
		Long timestamp;
		String hash;
		Long nonce = 0L;

		do {
			nonce++;
			timestamp = Calendar.getInstance().getTimeInMillis();
			difficulty = adjustDifficulty(lastBlock, timestamp, HuechainProperties.MINE_RATE);
			initObrigatory = new String(new char[difficulty]).replace("\0", "0");
			hash = CryptoUtils.hash(timestamp, lastHash, data, nonce, difficulty);
		} while (!initObrigatory.equals(hash.substring(0, difficulty)));

		return new Block(timestamp, lastBlock.getHash(), CryptoUtils.hash(timestamp, lastBlock.getHash(), data, nonce, difficulty),
				data, nonce, difficulty);
	}

	public static String blockHash(Block block) {
		return CryptoUtils.hash(block.getTimestamp(), block.getLastHash(), block.getData(), block.getNonce(),
				block.difficulty);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getLastHash() {
		return lastHash;
	}

	public String getHash() {
		return hash;
	}

	public String getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((lastHash == null) ? 0 : lastHash.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Block other = (Block) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (hash == null) {
			if (other.hash != null) {
				return false;
			}
		} else if (!hash.equals(other.hash)) {
			return false;
		}
		if (lastHash == null) {
			if (other.lastHash != null) {
				return false;
			}
		} else if (!lastHash.equals(other.lastHash)) {
			return false;
		}
		if (timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!timestamp.equals(other.timestamp)) {
			return false;
		}
		return true;
	}

	public static Integer adjustDifficulty(final Block lastBlock, final Long currentTime, Long mineRate) {
		if (mineRate == null) {
			mineRate = 3000L;
		}

		Integer difficulty = lastBlock.getDifficulty();

		difficulty = lastBlock.timestamp + mineRate > currentTime ? difficulty + 1 : difficulty - 1;
		return difficulty;
	}

	public Long getNonce() {
		return nonce;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

}
