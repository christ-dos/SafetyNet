package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;
/**
 * A class that call the methods CRUD 
 * 
 * @author Christine Duarte
 * 
 * @see PersonDAO
 *
 */
@Service
@Slf4j
public class PersonService {
	
	@Autowired
	private PersonDAO personDao;
	
	
	public List<Person> getListPersons() {

		return personDao.getListPersons();
	}
	
	public Person getPerson(String lastName, String firstName) {
		if(lastName == null || firstName == null) {
			// Throws exception
			log.info("This person not exist in file");
		}
		
		Person person = personDao.getPerson(lastName, firstName);
		if (person != null) {
			return person;
		}
		throw new RuntimeException(); // Throws PersonNotFoundException
	}
	
	public Person addPerson(Person person) {
		return personDao.save(person);
		
	}
	public void deletePerson(String firstName, String lastName) {
		Person person = personDao.getPerson(lastName, firstName);
		
		if(person != null) {
			personDao.delete(person);
			log.info("Service Person deleted : " + firstName + lastName);
		}
		log.info("je suis null");
	}
	
	public Person updatePerson(Person person) {
		return personDao.update(person);
		
	}

}
