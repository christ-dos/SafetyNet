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
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.slf4j.Slf4j;


/**
 * Class that manage the requests of browser
 * 
 * @author Christine Duarte
 *
 */
@RestController
@Slf4j
public class PersonController {
	
	@Autowired
	private IPersonService ipersonService;
	
	
	 /**@GetMapping(value= "/person")
	public List <Person> getPersonDataJson()  {
		log.info("Controller get list of persons");
		return ipersonService.getListPersons();
	}*/
	
	@GetMapping(value= "/person")
	public Person getPersonDataJson(@Valid @RequestParam String firstName, @RequestParam String lastName) throws EmptyFieldsException  {
		//Person person = new Person("John", "Boyd","1509 Culver St","Culver", "97451", "841-874-6512","jaboyd@email.com");
		log.info("Controller - person found: " + firstName + " " + lastName );
		return ipersonService.getPerson(firstName, lastName);
	}
	
	@PostMapping(value= "/person")
	public Person savePersonInFile(@Valid @RequestBody Person person) throws EmptyFieldsException {
		log.info("Controller - person saved: " + person.getFirstName() + " " + person.getLastName());
		return ipersonService.addPerson(person);
	}
	
	@DeleteMapping(value= "/person")
	public void deletePersonByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
		ipersonService.deletePerson(firstName, lastName);
		log.info("Controller - person deleted : " + firstName + " "+ lastName);
		
	}
	
	@PutMapping(value= "/person")
	public Person updatePersonByFirstNameAndLastName(@Valid @RequestBody Person person) throws EmptyFieldsException {
		log.info("Controller - person updated : " + person.getFirstName() + " " + person.getLastName());
		return ipersonService.updatePerson(person);
	}
	
}
