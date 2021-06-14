package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.DTO.PersonChildAlertDisplaying;
import com.safetynet.alerts.DTO.PersonCoveredByStationDisplaying;
import com.safetynet.alerts.DTO.PersonFireDisplaying;
import com.safetynet.alerts.DTO.PersonFloodDisplaying;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;

/**
 * An interface which get person informations and medical history with the
 * firstName and lastName
 * 
 * @author Christine Duarte
 *
 */
public interface IPersonInformationService {

	/**
	 * Method that filters the list of persons to get phone number covered by a
	 * station number
	 * 
	 * @param station - The station for which we want obtained the phones
	 * @return the list filtered containing the phones
	 */
	public List<String> getPhoneAlertResidentsCoveredByStation(String station);

	/**
	 * Method that get the list of persons covered by station number
	 * 
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the number of child and
	 *         adult
	 */
	public PersonCoveredByStationDisplaying getPersonCoveredByFireStation(String station);

	/**
	 * Method that get person informations and medicalRecord with the firstName and
	 * lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName  - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and
	 *         the medical history of person
	 */
	public PersonInfoDisplaying getPersonInformation(String firstName, String lastName);

	/**
	 * Method which get the list of childs and adults that living same address
	 * 
	 * @param address - A String containing the address of person
	 * @return An arrayList with childs and other arrayList containing adults living
	 *         in same address
	 */
	public PersonChildAlertDisplaying getChildAlertList(String address);

	/**
	 * Method that get a list of persons covered by a list of station number the
	 * list of person is grouping by address of households should return firstName,
	 * lastName, phone, age and medical history
	 * 
	 * @param stations - a list containing station number
	 * @return A map with the list of persons covered by the list of station number
	 *         and persons are grouping by address
	 */
	public List<PersonFloodDisplaying> getHouseHoldsCoveredByFireStation(List<String> stations);

	/**
	 * Method that get a list of persons living in same address and given firstName,
	 * lastName, phone, age and medical history
	 * 
	 * @param address - A String containing address of person
	 * @return A list of persons and the station number that covers the households
	 */
	public PersonFireDisplaying getPersonsFireByAddress(String address);
	
}