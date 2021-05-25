package com.safetynet.alerts.exceptions;

/**
 * Class that manages the shipment of a message when a
 * MedicalRecordAlreadyExistException is handle
 * 
 * when the medicalRecord we want save already exist
 * 
 * @author Christine Duarte
 *
 */
public class MedicalRecordAlreadyExistException extends RuntimeException {

	/**
	 * Attribute that give an ID at the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public MedicalRecordAlreadyExistException(String message) {
		super(message);
	}
	
	

}
