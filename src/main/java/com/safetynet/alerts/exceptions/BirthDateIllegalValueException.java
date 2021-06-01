package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a BirthDateIllegalValueException
 * is handle
 * 
 * when the bithDate has an illegal value
 * 
 * @author Christine Duarte
 *
 */
public class BirthDateIllegalValueException extends RuntimeException {

	/**
	 * Attribute that give an IDat the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public BirthDateIllegalValueException(String message) {
		super(message);
	}
	
	

}
