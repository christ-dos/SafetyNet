package com.safetynet.alerts.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the url /personinfo?firstName=<firstName>&lastName=<lastName>
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDTOService implements IPersonInfoDTOService {
	
	/**
	 * An instance of  {@link personDAO}
	 */
	@Autowired
	private IPersonDAO personDAO;
	
	/**
	 * An instance of  {@link MedicalRecordDAO}
	 */
	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;
	
	/**
	 * Method that get person informations and medicalRecord with the firstName and lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and the medical history of person
	 */
	@Override
	public PersonInfoDTO getPersonInformationDTO(String firstName, String lastName) {
		Person personInfo = personDAO.getPerson(firstName, lastName);
		MedicalRecord medicalRecordPerson = medicalRecordDAO.get(firstName, lastName);
		Integer agePerson = personDAO.getAge(medicalRecordPerson.getBirthDate());
		
		PersonInfoDTO personInfoDTO = 
				new PersonInfoDTO(personInfo.getFirstName(), personInfo.getLastName(), personInfo.getAddress(), agePerson, personInfo.getEmail(), new ArrayList<>(medicalRecordPerson.getMedications()), new ArrayList<>(medicalRecordPerson.getAllergies()));
		log.info("PersonInfoDTOService - displaying person informations for: " + firstName + " " + lastName);
		return personInfoDTO;
	}
}
