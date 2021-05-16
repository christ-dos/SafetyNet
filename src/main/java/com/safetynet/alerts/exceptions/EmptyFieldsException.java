package com.safetynet.alerts.exceptions;

public class EmptyFieldsException extends Exception {

	/**
	 * Attribute that give an ID at the exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public EmptyFieldsException(String message) {
		super(message);
	}

}
