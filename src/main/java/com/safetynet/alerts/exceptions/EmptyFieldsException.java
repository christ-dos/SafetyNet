package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a EmptyFieldsException is handle
 * 
 * when the field firstName or LastName is empty
 * 
 * @author Christine Duarte
 *
 */
public class EmptyFieldsException extends RuntimeException {

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
