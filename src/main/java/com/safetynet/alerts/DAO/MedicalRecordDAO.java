package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the CRUD methods and implement IMedicalRecordDAO interface
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Builder
@AllArgsConstructor
public class MedicalRecordDAO implements IMedicalRecordDAO {
	
	/**
	 * Attribute that contain the list of medicalRecords that provide from data.json
	 */
	@Autowired
	private List<MedicalRecord> listMedicalRecords;
	
	/**
	 * Method that get the list of medicalRecords
	 * 
	 * @return An ArrayList of medicalRecords
	 */
	@Override
	public List<MedicalRecord> getMedicalRecords() {
		return listMedicalRecords;
	}

	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * 
	 * @param firstName - The firstName recorded in the medicalRecord
	 * @param lastName  - The lastName recorded in the medicalRecord
	 * @return An instance of MedicalRecord or null if the medicalRecord not exist
	 */
	@Override
	public MedicalRecord get(String firstName, String lastName) {
		for (MedicalRecord medicalRecord : listMedicalRecords) {
			if (medicalRecord.getFirstName().equalsIgnoreCase(firstName) && medicalRecord.getLastName().equalsIgnoreCase(lastName)) {
				log.debug("DAO - MedicalRecord found  for person: " + firstName + " " + lastName);
				return medicalRecord;
			}
		}
		return null;
	}
	
	/**
	 * Method that save a medicalRecord in the ArrayList
	 * 
	 * @param index - An integer containing the position where will be saved the medicalRecord
	 * @param medicalRecord - An instance of MedicalRecord
	 * @return The MedicalRecord that was saved in the arrayList
	 */
	@Override
	public MedicalRecord save(int index, MedicalRecord medicalRecord) {
		if (index < 0) {
			listMedicalRecords.add(medicalRecord);
		} else {
			listMedicalRecords.set(index, medicalRecord);
		}
		log.debug("DAO - MedicalRecord saved for person: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return medicalRecord;
	}
	
	/**
	 * Method that delete a medicalRecord from the ArrayList
	 * 
	 * @param medicalRecord - The medicalRecord we want deleted
	 * @return A String to confirm the deletion "SUCCESS"
	 */
	@Override
	public String delete(MedicalRecord medicalRecord) {
		listMedicalRecords.remove(medicalRecord);
		log.debug("DAO - MedicalRecord deleted for person: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return "SUCCESS";
	}
}
