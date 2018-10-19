package br.com.jezzrusso.huechain.exceptions;

public class UnmodifiedBlockchainException extends Exception {

	private static final long serialVersionUID = -5867357625601721706L;
	
	public UnmodifiedBlockchainException() {
		super("Blockchain não foi alterado.");
	}

}
