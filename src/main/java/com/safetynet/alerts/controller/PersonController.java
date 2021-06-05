package com.safetynet.alerts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests for Person entity
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
	 * Request Get to obtain a person
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 * @return a person object
	 */
	@GetMapping(value = "/person")
	public Person getPerson(@Valid @RequestParam String firstName, @Valid @RequestParam String lastName) {
		log.debug("Controller - Request to find person: " + firstName + " " + lastName);
		return personService.getPerson(firstName, lastName);
	}
	
	/**
	 * Request Get to obtain the list of email of residents of the city input
	 * 
	 * @param city - City for which we want obtained the list of email
	 * @return A list of email for residents living in city input
	 */
	@GetMapping(value = "/communityEmail")
	public List<String> getEmailResident(@Valid @RequestParam String city) {
		log.debug("Controller - Request to get Email residents in city : "  + city );
		return personService.getEmailResidents(city);
	}

	/**
	 * Request Post to add a person at the ArrayList
	 * 
	 * @param person - a person obtained by the body of the request
	 * @return the person added in the ArrayList
	*/
	@PostMapping(value = "/person")
	public Person savePerson(@Valid @RequestBody Person person){
		log.debug("Controller - Request to save person: " + person.getFirstName() + " " + person.getLastName());
		return personService.addPerson(person);
	}

	/**
	 * Request Delete to delete a person from the ArrayList
	 * 
	 * @param firstName - a String obtained from url request
	 * @param lastName  - a String obtained from url request
	 * @return a String with the message "SUCCESS" or "Person not deleted"
	 */
	@DeleteMapping(value = "/person")
	public String deletePersonByFirstNameAndLastName(@Valid @RequestParam String firstName, @RequestParam String lastName) {
		log.debug("Controller - Request to delete person: " + firstName + " " + lastName);
		return personService.deletePerson(firstName, lastName);
	}

	/**
	 * Request Put to update a person from the ArrayList
	 * 
	 * @param person - a person obtained by the body of the request
	 * @return the person updated in the ArrayList
	 */
	@PutMapping(value = "/person")
	public Person updatePerson(@Valid @RequestBody Person person) {
		log.debug("Controller - Request to update person: " + person.getFirstName() + " " + person.getLastName());
		return personService.updatePerson(person);
	}
}
