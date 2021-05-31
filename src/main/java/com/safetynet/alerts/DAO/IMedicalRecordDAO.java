package com.safetynet.alerts.DAO;

import java.util.List;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 *  Interface that manage methods CRUD of entity MedicalRecord
 *  
 * @author Christine Duarte
 *
 */
public interface IMedicalRecordDAO {
	/**
	 * Method that get the list of medicalRecords
	 * 
	 * @return An ArrayList of MedicalRecords
	 */
	public List<MedicalRecord> getMedicalRecords();
	
	/**
	 * Method that get the list of medicalRecords by list of Persons in parameter
	 * 
	 * @param listPerson - A list of persons
	 * @return The  ArrayList of MedicalRecords with the persons input in parameter
	 */
	public List<MedicalRecord> getListMedicalRecordForAListOfPerson(List<Person> listPerson);

	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName recorded in the medicalRecord
	 * @param lastName  - the lastName recorded in the medicalRecord
	 * @return An instance of MedicalRecord
	 */
	public MedicalRecord get(String firstName, String lastName);

	/**
	 * Method that save a medicalRecord in the ArrayList
	 * 
	 * @param index - The position where will be saved the medicalRecord
	 * @return  MedicalRecord that was saved in the arrayList
	 */
	public MedicalRecord save(int index, MedicalRecord medicalRecord);

	/**
	 * Method that delete a medicalRecord from the ArrayList
	 * 
	 * @param medicalRecord - MedicalRecord we want deleted
	 * @return A String to confirm the deletion
	 */
	public String delete(MedicalRecord medicalRecord);
}
