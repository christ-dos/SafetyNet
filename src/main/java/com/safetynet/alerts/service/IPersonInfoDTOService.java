package com.safetynet.alerts.service;

import com.safetynet.alerts.model.PersonInfoDTO;

/**
 * An interface which get person informations and medical history with the firstName and lastName
 * 
 * @author Christine Duarte
 *
 */
public interface IPersonInfoDTOService {
	
	/**
	 * Method that get person informations and medicalRecord with the firstName and lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and the medical history of person
	 */
	public PersonInfoDTO getPersonInformationDTO(String firstName, String lastName);

}