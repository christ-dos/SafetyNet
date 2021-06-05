package com.safetynet.alerts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;
import com.safetynet.alerts.service.IPersonInformationService;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests for {@link PersonInfoDisplaying}
 * 
 * @author Christine Duarte
 *
 */
@RestController
@Slf4j
public class PersonInformationController {
	/**
	 * An instance of the IPersonService
	 * 
	 * @see IPersonService
	 */ 
	@Autowired
	private IPersonInformationService personInformationService;
	
	/**
	 * Request get to obtain informations of person and medical history for a firstName and lastName input
	 * 
	 * @param firstName - A String containing the firstName of person
	 * @param lastName - A String containing the lastName of person
	 * @return A result containing firstName, lastName, address, age, email, and a list of medication and allergies
	 */
	@GetMapping(value= "/personinfo")
	public PersonInfoDisplaying testGetPersonInfo(@Valid @RequestParam String firstName, @Valid @RequestParam  String lastName) {
		log.debug("PersonInformationController - Request to obtain person information and medical history for: " + firstName + " " + lastName);
		return personInformationService.getPersonInformation(firstName, lastName);
	}
	
	/**
	 * Request get to obtain a list of person DTO covered by a fireStation station number
	 * and a counter of adults and childs
	 * 
	 * @param station - A String with a number of the station
	 * @return A list of PersonDTO and a counter of adults and childs
	 */
	@GetMapping(value = "/firestation")
	public PersonsCoveredByStation getListPersonsCoveredByStation(@Valid @RequestParam  String station) {
		log.debug("PersonInformationController - Request list person covered by station: " + station);
		return personInformationService.getPersonCoveredByFireStation(station);
	}
	
	/**
	 * Request get to obtain a list of phone of person covered by a fireStation station number
	 * 
	 * @param station - A String with a number of the station
	 * @return A list of phone of person covered by station
	 */
	@GetMapping(value = "/phoneAlert")
	public List<String> getPhoneAlertResidentsCoveredByStation(@Valid @RequestParam  String station) {
		log.debug("PersonInformationController  - Request list of phones of persons covered by station: " + station);
		return personInformationService.getPhoneAlertResidentsCoveredByStation(station);
	}
}
