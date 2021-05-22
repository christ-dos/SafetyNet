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

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests
 * 
 * @author Christine Duarte
 *
 */
@RestController
@Slf4j
public class PersonController {
		
	/**
	 * An instance of the IPersonService
	 * 
	 * @see IPersonService
	 */
	@Autowired
	private IPersonService personService;

	/**
	 * Request get to obtain a person
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 * @return a person object
	 * @throws EmptyFieldsException - when the field firstName or lastName is empty
	 *                              in the request
	 */
	@GetMapping(value = "/person")
	public Person getPerson(@Valid @RequestParam String firstName, @RequestParam String lastName)
			throws EmptyFieldsException {
		log.info("Controller - person found: " + firstName + " " + lastName);
		return personService.getPerson(firstName, lastName);
	}

	/**
	 * Request post to add a person at the ArrayList
	 * 
	 * @param person - a person obtained by the body of the request
	 * @return the person added in the ArrayList
	 * @throws PersonAlreadyExistException - when the person we want saved exist in
	 *                                     ArrayList
	 */
	@PostMapping(value = "/person")
	public Person savePerson(@Valid @RequestBody Person person) throws PersonAlreadyExistException {
		log.info("Controller - person saved: " + person.getFirstName() + " " + person.getLastName());
		return personService.addPerson(person);
	}

	/**
	 * Request Delete to delete a person from the ArrayList
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 */
	@DeleteMapping(value = "/person")
	public String deletePersonByFirstNameAndLastName(@Valid @RequestParam String firstName, @RequestParam String lastName) {
		log.info("Controller - person deleted : " + firstName + " " + lastName);
		return personService.deletePerson(firstName, lastName);
	}

	/**
	 * Request put to update a person from the ArrayList
	 * 
	 * @param person - a person obtained by the body of the request
	 * @return - the person updated in the ArrayList
	 */
	@PutMapping(value = "/person")
	public Person updatePersonByFirstNameAndLastName(@Valid @RequestBody Person person) {
		log.info("Controller - person updated : " + person.getFirstName() + " " + person.getLastName());
		return personService.updatePerson(person);
	}

}
