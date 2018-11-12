package br.com.jezzrusso.huechain.util.crypto.beans;

public class Signature {

	public Signature(final String r, final String s, final Integer recoveryParam) {
		this.r = r;
		this.s = s;
		this.recoveryParam = recoveryParam;
	}

	private final String r;
	private final String s;
	private final Integer recoveryParam;

	public String getR() {
		return r;
	}

	public String getS() {
		return s;
	}

	public Integer getRecoveryParam() {
		return recoveryParam;
	}

	@Override
	public String toString() {
		return "{Signature:{r:" + r + ",s:" + s + ",recoveryParam:" + recoveryParam + "}}";
	}

}
