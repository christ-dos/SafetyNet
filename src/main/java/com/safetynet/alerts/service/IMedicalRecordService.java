package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * An interface that manage methods CRUD of the service of the entity
 * MedicalRecord
 * 
 * @author Christine Duarte
 *
 */
public interface IMedicalRecordService {

	/**
	 * Method that get a list of medicalRecords
	 * 
	 * @return An ArrayList of medicalRecords
	 */
	public List<MedicalRecord> getListMedicalRecords();
	
	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * 
	 * @param firstName - The firstName recorded in the medicalRecord
	 * @param lastName  - The lastName recorded in the medicalRecord
	 * @return an instance of medicalRecord getted
	 */
	public MedicalRecord getMedicalRecord(String firstName, String lastName);

	/**
	 * Method that add a medicalRecord
	 * 
	 * @param medicalRecord - an instance of MedicalRecord
	 * @return The medicalRecord added
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
	 * @param medicalRecord - An instance of MedicalRecord
	 * @return The medicalRecord updated
	 */
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

}
