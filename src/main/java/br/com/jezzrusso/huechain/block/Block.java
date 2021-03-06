package br.com.jezzrusso.huechain.block;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Block {

	private final Long timestamp;
	private final String lastHash;
	private final String hash;
	private final String data;

	@JsonCreator
	public Block(@JsonProperty("timestamp") Long timestamp, @JsonProperty("lastHash") String lastHash,
			@JsonProperty("hash") String hash, @JsonProperty("data") String data) {
		super();
		this.timestamp = timestamp;
		this.lastHash = lastHash;
		this.hash = hash;
		this.data = data;
	}

	@Override
	public String toString() {
		return "{Block:{timestamp:" + timestamp + ",lastHash:" + lastHash + ",hash:" + hash + ",data:" + data + "}}";
	}

	public static Block genesis() {
		return new Block(0L, "------", "g3n3515", "[]");
	}

	public static Block mineBlock(String lastHash, String data) {
		final Long timestamp = Calendar.getInstance().getTimeInMillis();
		return new Block(timestamp, lastHash, hash(timestamp, lastHash, data), data);
	}

	public static String hash(Long timestamp, String lastHash, String data) {
		return DigestUtils.sha256Hex(timestamp.toString().concat(lastHash).concat(data));
	}

	public static String blockHash(Block block) {
		return hash(block.getTimestamp(), block.getLastHash(), block.getData());
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


}
