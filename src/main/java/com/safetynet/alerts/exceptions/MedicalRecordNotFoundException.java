package com.safetynet.alerts.exceptions;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * Class that manages the shipment of a message when a {@link MedicalRecordNotFoundException}
 * is handle
 * 
 * when the {@link MedicalRecord} is not found
 * 
 * @author Christine Duarte
 *
 */
public class MedicalRecordNotFoundException extends RuntimeException {

	/**
	 * Attribute that give an ID at the exception
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the exception
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public MedicalRecordNotFoundException(String message) {
		super(message);
	}
	
}
