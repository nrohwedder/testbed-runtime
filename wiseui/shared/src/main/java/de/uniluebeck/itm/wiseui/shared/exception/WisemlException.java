package de.uniluebeck.itm.wiseui.shared.exception;

public class WisemlException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4313625292150681976L;

	public WisemlException() {
		
	}
	
	public WisemlException(final Throwable throwable) {
		super(throwable);
	}
	
	public WisemlException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
