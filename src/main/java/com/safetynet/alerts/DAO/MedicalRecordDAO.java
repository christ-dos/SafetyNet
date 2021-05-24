package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Builder
public class MedicalRecordDAO implements IMedicalRecordDAO {
	
	/**
	 * attribute that contain the list of medicalRecords that provide from data.json
	 */
	@Autowired
	private List<MedicalRecord> listMedicalRecords;
	
	
	public MedicalRecordDAO(List<MedicalRecord> listMedicalRecords) {
		super();
		this.listMedicalRecords = listMedicalRecords;
	}
	

	@Override
	public List<MedicalRecord> getMedicalRecords() {
		return listMedicalRecords;
	}


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
	 * @param medicalRecord - an instance of MedicalRecord
	 * @return - The MedicalRecord that was saved in the arrayList
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

	@Override
	public String delete(MedicalRecord medicalRecord) {
		listMedicalRecords.remove(medicalRecord);
		log.debug("DAO - MedicalRecord deleted for person: " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return "SUCCESS";
	}

	

	

	

	
}
