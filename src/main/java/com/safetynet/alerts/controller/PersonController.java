package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.DAO.PersonDataJsonDAO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

import lombok.extern.slf4j.Slf4j;


/**
 * Class that manage the requests of browser
 * 
 * @author Christine Duarte
 *
 */
@ RestController
@Slf4j
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonDataJsonDAO personDataJsonDAO;
	
	
	@GetMapping(value= "/person")
	public List <Person> getPersonDataJson()  {
		
		log.info("un test de log christine");
		return personService.getListPersons();
		
	}
	
	@PostMapping(value= "/person")
	public List<Person> SavePersonInFile(@RequestBody Person person) {
		
		log.info("methode save de controller est appellé");
		List<Person> persons = personService.addPersonToFile(person);
		return persons;
		
		
		
		
	}

}
