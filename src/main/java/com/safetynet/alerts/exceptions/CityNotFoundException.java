package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a CityNotFoundException
 * is handle
 * 
 * when the city is not found
 * 
 * @author Christine Duarte
 *
 */
public class CityNotFoundException extends RuntimeException{

	/**
	 * Attribute that give an IDat the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public CityNotFoundException(String message) {
		super(message);
	}
	
	
	
	
	
	

}
