package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Class service that manage the methods CRUD for MedicalRecord entity
 * 
 * @author Christine Duarte
 * 
 * @see MedicalRecord
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
public class MedicalRecordService implements IMedicalRecordService {

	/**
	 * An instance of {@link MedicalRecordDAO}
	 */
	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;

	/**
	 * Method private that get a list of medicalRecords
	 * 
	 * @return An ArrayList of medicalRecords
	 */
	private List<MedicalRecord> getListMedicalRecords() {
		return medicalRecordDAO.getMedicalRecords();
	}

	/**
	 * Method that get a medicalRecord by combining keys firstName and lastName
	 * recorded in the medicalRecord
	 * 
	 * @param firstName - the firstName recorded in the medicalRecord
	 * @param lastName  - the lastName recorded in the medicalRecord
	 * @return an instance of MedicalRecord getted
	 * @throws EmptyFieldsException           when the field firstName or lastName
	 *                                        is empty
	 * @throws MedicalRecordNotFoundException when medicalRecord is not found
	 */
	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) {
		if (firstName.isEmpty() || lastName.isEmpty()) {
			log.error("Service - The fields firstName and lastName cannot be empty ");
			throw new EmptyFieldsException("Field cannot be empty");
		}
		MedicalRecord medicalRecord = medicalRecordDAO.get(firstName, lastName);
		if (medicalRecord != null) {
			log.debug("Service - MedicalRecord found for person: " + medicalRecord.getFirstName() + " "
					+ medicalRecord.getLastName());
			return medicalRecord;
		}
		log.error("Service - MedicalRecord not found for person: " + firstName + " " + lastName);
		throw new MedicalRecordNotFoundException("MedicalRecord not found, please try again!");
	}

	/**
	 * Method that add a {@link MedicalRecord}
	 * 
	 * @param medicalRecord - An instance of MedicalRecord
	 * @return The medicalRecord added
	 * @throws MedicalRecordAlreadyExistException when the medicalRecord that we
	 *                                            want added already exist
	 */
	@Override
	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		List<MedicalRecord> medicalRecordsList = getListMedicalRecords();
		int index = medicalRecordsList.indexOf(medicalRecord);
		// medicalRecord already exist
		if (index >= 0) {
			log.error("Service - medicalRecord cannot be saved because person: " + medicalRecord.getFirstName() + " "
					+ medicalRecord.getLastName() + " already have a medicalRecord");
			throw new MedicalRecordAlreadyExistException("MedicalRecord already exist");
		}
		log.debug("Service - MedicalRecord is saved for the person: " + medicalRecord.getFirstName() + " "
				+ medicalRecord.getLastName());
		return medicalRecordDAO.save(index, medicalRecord);
	}

	/**
	 * Method that delete a medicalRecord
	 * 
	 * @param firstName - the firstName recorded in the medicalRecord
	 * @param lastName  - the lastName recorded in the medicalRecord
	 * @return a String to confirm or deny the deletion
	 */
	@Override
	public String deleteMedicalRecord(String firstName, String lastName) {
		MedicalRecord medicalRecordToDelete = medicalRecordDAO.get(firstName, lastName);
		if (medicalRecordToDelete != null) {
			log.debug("Service - MedicalRecord deleted for person: " + firstName + " " + lastName);
			return medicalRecordDAO.delete(medicalRecordToDelete);
		}
		log.error("Service - MedicalRecord cannot be deleted for person: " + firstName + " " + lastName
				+ " because not exist");
		return "MedicalRecord cannot be Deleted";
	}

	/**
	 * Method that update a medicalRecord
	 * 
	 * @param medicalRecord - an instance of MedicalRecord
	 * @return the medicalRecord updated
	 * @throws MedicalRecordNotFoundException when the medicalRecord that we want
	 *                                        update not exist
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		String firstName = medicalRecord.getFirstName();
		String lastName = medicalRecord.getLastName();
		MedicalRecord resultMedicalRecordFinded = medicalRecordDAO.get(firstName, lastName);

		if (resultMedicalRecordFinded == null) {
			log.error("Service - The medicalRecord that we want update not exist for the person: " + firstName + " "
					+ lastName);
			throw new MedicalRecordNotFoundException(
					"The medicalRecord that we want update not exist for the person: " + firstName + " " + lastName);
		}
		List<MedicalRecord> medicalRecordsList = getListMedicalRecords();
		int indexPosition = medicalRecordsList.indexOf(resultMedicalRecordFinded);
		resultMedicalRecordFinded.setMedications(medicalRecord.getMedications());
		resultMedicalRecordFinded.setAllergies(medicalRecord.getAllergies());
		log.debug("Service - MedicalRecord updated for the person: " + resultMedicalRecordFinded.getFirstName() + " "
				+ resultMedicalRecordFinded.getLastName());

		return medicalRecordDAO.save(indexPosition, resultMedicalRecordFinded);
	}
}
