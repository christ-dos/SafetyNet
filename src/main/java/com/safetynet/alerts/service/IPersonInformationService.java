package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;

/**
 * An interface which get person informations and medical history with the firstName and lastName
 * 
 * @author Christine Duarte
 *
 */
public interface IPersonInformationService {
	
	/**
	 * Method that filters the list of persons to get phone number covered by a station number
	 * 
	 * @param station - The station for which we want obtained the phones
	 * @return the list filtered containing the phones
	 */
	public List<String> getPhoneAlertResidentsCoveredByStation(String station);
	
	/**
	 * Method that get the list of persons covered by station number
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the number of child and adult
	 */
	public PersonsCoveredByStation getPersonCoveredByFireStation(String station);
	/**
	 * Method that get person informations and medicalRecord with the firstName and lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and the medical history of person
	 */
	public PersonInfoDisplaying getPersonInformation(String firstName, String lastName);
	
	/**
	 * Method which get the list of childs and adults that living same address
	 * 
	 * @param address - A String containing the address of person
	 * @return An arrayList with childs and other arrayList containing adults living in same address
	 */
	public ChildAlertDisplaying getChildAlertList(String address);

	

	

}