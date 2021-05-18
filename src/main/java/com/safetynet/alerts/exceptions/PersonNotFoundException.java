package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a PersonNotFoundException
 * is handle
 * 
 * when the person is not found
 * 
 * @author Christine Duarte
 *
 */
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
