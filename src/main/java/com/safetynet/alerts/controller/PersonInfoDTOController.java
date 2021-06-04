package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.PersonInfoDTO;
import com.safetynet.alerts.service.IPersonInfoDTOService;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests for {@link PersonInfoDTO}
 * 
 * @author Christine Duarte
 *
 */
@RestController
@Slf4j
public class PersonInfoDTOController {
	/**
	 * An instance of the IPersonService
	 * 
	 * @see IPersonService
	 */ 
	@Autowired
	private IPersonInfoDTOService personInfoDTOService;
	
	/**
	 * Request get to obtain informations of person and medical history for a firstName and lastName input
	 * 
	 * @param firstName - A String containing the firstName of person
	 * @param lastName - A String containing the lastName of person
	 * @return A result containing firstName, lastName, address, age, email, and a list of medication and allergies
	 */
	@GetMapping(value= "/personinfo")
	public PersonInfoDTO testGetPersonInfo(@Valid @RequestParam String firstName, @Valid @RequestParam  String lastName) {
		log.debug("PersonInfoController - Request to obtain person information and medical history for: " + firstName + " " + lastName);
		return personInfoDTOService.getPersonInformationDTO(firstName, lastName);
	}

}
