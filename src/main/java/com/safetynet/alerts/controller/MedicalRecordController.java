package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests for MedicalRecord entity
 * 
 * @author Christine Duarte
 *
 */
@RestController
@Slf4j
public class MedicalRecordController {

	/**
	 * An instance of the MedicalRecordService
	 * 
	 * @see IMedicalRecordService
	 */
	@Autowired
	private IMedicalRecordService medicalRecordService;

	/**
	 * Request Get to obtain a medicalRecord
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 * @return a MedicalRecord object
	 */
	@GetMapping(value = "/medicalRecord")
	public MedicalRecord getMedicalRecord(@Valid @RequestParam String firstName, @RequestParam String lastName) {
		log.debug("Controller - Request to find medicalRecord for person: " + firstName + " " + lastName);
		return medicalRecordService.getMedicalRecord(firstName, lastName);
	}

	/**
	 * Request Post to add a medicalRecord at the ArrayList
	 * 
	 * @param medicalRecord - a medicalRecord obtained by the body of the request
	 * @return the medicalRecord added in the ArrayList
	 */
	@PostMapping(value = "/medicalRecord")
	public MedicalRecord saveMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		log.debug("Controller - Request to save medicalRecord for person: " + medicalRecord.getFirstName() + " "
				+ medicalRecord.getLastName());
		return medicalRecordService.addMedicalRecord(medicalRecord);
	}

	/**
	 * Request Delete to delete a medicalRecord from the ArrayList
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 * @return a String with the message "SUCCESS" or "medicalRecord cannot be
	 *         deleted"
	 */
	@DeleteMapping(value = "/medicalRecord")
	public String deleteMedicalRecordByFirstNameAndLastName(@Valid @RequestParam String firstName,
			@RequestParam String lastName) {
		log.debug("Controller - Request to delete medicalRecord for person: " + firstName + " " + lastName);
		return medicalRecordService.deleteMedicalRecord(firstName, lastName);
	}

	/**
	 * Request Put to update a medicalRecord from the ArrayList
	 * 
	 * @param medicalRecord - a medicalRecord obtained by the body of the request
	 * @return the medicalRecord updated in the ArrayList
	 */
	@PutMapping(value = "/medicalRecord")
	public MedicalRecord updateMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		log.debug("Controller - Request to update person: " + medicalRecord.getFirstName() + " "
				+ medicalRecord.getLastName());
		return medicalRecordService.updateMedicalRecord(medicalRecord);
	}
}
