package com.safetynet.alerts.DAO;

import java.util.List;

import com.safetynet.alerts.model.MedicalRecord;

/**
 *  Interface that manage methods CRUD of entity MedicalRecord
 *  
 * @author Christine Duarte
 *
 */
public interface IMedicalRecordDAO {
	/**
	 * Method that get the list of MedicalRecords
	 * 
	 * @return an ArrayList of MedicalRecords
	 */
	public List<MedicalRecord> getMedicalRecords();

	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of MedicalRecord
	 */
	public MedicalRecord get(String firstName, String lastName);

	/**
	 * Method that save a MedicalRecord in the ArrayList
	 * 
	 * @param index - the position where will be saved the medicalRecord
	 * @return - MedicalRecord that was saved in the arrayList
	 */
	public MedicalRecord save(int index, MedicalRecord medicalRecord);

	/**
	 * Method that delete a MedicalRecord from the ArrayList
	 * 
	 * @param medicalRecord - MedicalRecord we want deleted
	 * @return a String to confirm the deletion
	 */
	public String delete(MedicalRecord medicalRecord);

	

}
