package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.MedicalRecord;

/**
 * An interface that manage methods CRUD of the service of the entity MedicalRecord
 * 
 * @author Christine Duarte
 *
 */
public interface IMedicalRecordService {
	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName recorded in the medicalRecord
	 * @param lastName  - the lastName recorded in the medicalRecord
	 * @return an instance of medicalRecord getted
	 * @throws EmptyFieldsException when the field firstName or lastName is empty
	 */
	public MedicalRecord getMedicalRecord(String firstName, String lastName);

	/**
	 * Method that add a medicalRecord
	 * 
	 * @param person - an instance of MedicalRecord
	 * @return the medicalRecord added
	 * @throws  MedicalRecordAlreadyExist when the medicalRecord that we want added
	 *                                     already exist
	 */
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Method that delete a medicalRecord
	 * 
	 * @param firstName - the firstName recorded in the medicalRecord
	 * @param lastName  - the lastName recorded in the medicalRecord
	 * @return a String to confirm or deny the deletion
	 */
	public String deleteMedicalRecord(String firstName, String lastName);

	/**
	 * Method that update a medicalRecord
	 * 
	 * @param medicalRecord - an instance of MedicalRecord
	 * @return the medicalRecord updated
	 */
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

}
