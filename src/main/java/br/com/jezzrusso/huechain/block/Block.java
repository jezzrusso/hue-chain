package br.com.jezzrusso.huechain.block;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;

public class Block {

	private Long timestamp;
	private String lastHash;
	private String hash;
	private String data;

	public Block(Long timestamp, String lastHash, String hash, String data) {
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

}
