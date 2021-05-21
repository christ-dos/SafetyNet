package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a FireStationNotFoundException
 * is handle
 * 
 * when the fireStation is not found
 * 
 * @author Christine Duarte
 *
 */
public class FireStationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public FireStationNotFoundException(String message) {
		super(message);
	}

}
