package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a
 * FireStationAlreadyExistException is handle
 * 
 * when the fireStation we want save already exist
 * 
 * @author Christine Duarte
 *
 */
public class FireStationAlreadyExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FireStationAlreadyExistException(String message) {
		super(message);
	}
	
	

}
