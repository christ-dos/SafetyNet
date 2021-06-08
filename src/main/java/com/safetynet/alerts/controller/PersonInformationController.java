package com.safetynet.alerts.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.PersonFlood;
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
		log.debug("Controller - Request to obtain person information and medical history for: " + firstName + " " + lastName);
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
		log.debug("Controller - Request list person covered by station: " + station);
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
		log.debug("Controller  - Request list of phones of persons covered by station: " + station);
		return personInformationService.getPhoneAlertResidentsCoveredByStation(station);
	}
	
	/**
	 * Request get to obtain 2 lists: a list of childs and adults living in same address
	 * 
	 * @param address - A String with address
	 * @return A result containing a list with childs and adults living in address
	 */
	@GetMapping(value = "/childAlert")
	public ChildAlertDisplaying getChildAlertList(@Valid @RequestParam  String address) {
		log.debug("Controller  - Request list of childs living in address " + address);
		return personInformationService.getChildAlertList(address);
	}
	
	/**
	 * Request get to obtain a list of persons covered by a list of fireStation number
	 * 
	 * @param stations - A list of String containing stations numbers
	 * @return A map containing persons grouping by address
	 */
	@GetMapping(value = "/flood/stations")
	public Map<String, List<PersonFlood>> getFloodPersonsCoveredByStationList(@Valid @RequestParam List<String> stations) {
		log.debug("Controller  - Request list of childs living in address " + stations);
		return personInformationService.getHouseHoldsCoveredByFireStation(stations);
	}
}
