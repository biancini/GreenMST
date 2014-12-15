package it.garr.greenmst.algorithms;

public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 8580254167842354528L;
	
	public AlgorithmException() {
		super();
	}
	
	public AlgorithmException(String message) {
		super(message);
	}
	
	public AlgorithmException(Throwable origin) {
		super(origin);
	}
	
}
