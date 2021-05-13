package com.safetynet.alerts.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND )
public class PersonNotFoundException extends RuntimeException {

	/**
	 * Attribute that give an IDat the exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the exceptionS
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public PersonNotFoundException(String message) {
				super(message);
	}

}
