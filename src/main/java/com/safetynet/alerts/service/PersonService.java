package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
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
public class PersonService implements IPersonService {
	
	@Autowired
	private IPersonDAO iPersonDAO;
	
	
	@Override
	public List<Person> getListPersons() {
		return iPersonDAO.getPersons();
	}
	
	@Override
	public Person getPerson(String firstName, String lastName) {
		
		if(firstName.isEmpty() || lastName.isEmpty()) {
			// Throws exception
			log.error("Service - The fields firstName and lastName can not be empty ");
			throw new NullPointerException("The fields firstName and lastName can not be empty ");
		}
		Person person = iPersonDAO.getPerson(firstName, lastName);
		if(person != null) {
			log.info("Service - Person found : " + person.getFirstName() + " " + person.getLastName());
			return person;
		}
		log.info("Service - Person not found");
		return null;
		//throw new PersonNotFoundException("Service - Person not found exception");//TODO a revoir
	}
	
	@Override
	public Person addPerson(Person person) {
			Person personExist = getPerson(person.getFirstName(), person.getLastName());
			
			if(personExist != null && personExist.getFirstName().equalsIgnoreCase(person.getFirstName()) && person.getLastName().equalsIgnoreCase(personExist.getLastName())) {
				log.info("Service - Person: " + person.getFirstName() + " " + person.getLastName() + " that we try to saved already exist");
				return updatePerson(person);
			}
			if(personExist == null) {
			log.info("Service - Person is saved : " + person.getFirstName() + " " + person.getLastName());
			return iPersonDAO.save(person);
			}
			return null;
	}
	
	@Override
	public void deletePerson(String firstName, String lastName) {
			Person person = iPersonDAO.getPerson(firstName, lastName);
			
			if(person != null) {
				iPersonDAO.delete(person);
				log.info("Service - Person deleted : " + firstName +" "+ lastName );
			}
	}
	
	@Override
	public Person updatePerson(Person person) {
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		Person personToUpdate = getPerson(firstName, lastName);
		
		if(personToUpdate != null && personToUpdate.getFirstName().equalsIgnoreCase(person.getFirstName()) && person.getLastName().equalsIgnoreCase(personToUpdate.getLastName())) {
			int  indexPosition = getListPersons().indexOf(personToUpdate);
			
			if (!person.getAddress().equalsIgnoreCase(personToUpdate.getAddress())) {
				personToUpdate.setAddress(person.getAddress());
			}
			if (person.getCity().equalsIgnoreCase(personToUpdate.getCity())) {
				personToUpdate.setCity(person.getCity());
			}
			if (!person.getZip().equalsIgnoreCase(personToUpdate.getZip())) {
				personToUpdate.setZip(person.getZip());
			}
			if (!person.getPhone().equalsIgnoreCase(personToUpdate.getPhone())) {
				personToUpdate.setPhone(person.getPhone());
			}
			if (!person.getEmail().equalsIgnoreCase(personToUpdate.getEmail())) {
				personToUpdate.setEmail(person.getEmail());
			}
			log.info("Service - person updated: " + personToUpdate.getFirstName() + " " + personToUpdate.getLastName());
			
			return iPersonDAO.update(indexPosition, personToUpdate);
			
		}
		throw new PersonNotFoundException("Service - Person not found, and can not be updated");
	}

}
