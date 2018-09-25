package br.com.jezzrusso.huechain.block;

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
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getLastHash() {
		return lastHash;
	}
	
	public void setLastHash(String lastHash) {
		this.lastHash = lastHash;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	

	

}
