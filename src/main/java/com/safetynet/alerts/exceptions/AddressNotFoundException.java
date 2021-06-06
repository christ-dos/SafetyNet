package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a AddressNotFoundException
 * is handle
 * 
 * when the address is not found
 * 
 * @author Christine Duarte
 *
 */
public class AddressNotFoundException extends RuntimeException {
	/**
	 * Attribute that give an IDat the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public AddressNotFoundException(String message) {
		super(message);
	}
}
