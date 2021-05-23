package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a
 * PersonAlreadyExistException is handle
 * 
 * when the person we want update already exist
 * 
 * @author Christine Duarte
 *
 */
public class PersonAlreadyExistException extends Exception {

	/**
	 * Attribute that give an ID at the exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public PersonAlreadyExistException(String message) {
		super(message);
	}

}
