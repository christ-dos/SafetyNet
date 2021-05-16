package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
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
	private IPersonDAO personDAO;

	private List<Person> getListPersons() {
		return personDAO.getPersons();
	}

	@Override
	public Person getPerson(String firstName, String lastName) throws EmptyFieldsException {
		if (firstName.isEmpty() || lastName.isEmpty()) {
			log.error("Service - The fields firstName and lastName can not be empty ");
			throw new EmptyFieldsException("The fields firstName and lastName can not be empty");
		}
		Person person = personDAO.getPerson(firstName, lastName);
		if (person != null) {
			log.info("Service - Person found : " + person.getFirstName() + " " + person.getLastName());
			return person;
		}
		log.info("Service - Person not found");
		throw new PersonNotFoundException("Service - Person not found exception");
	}

	@Override
	public Person addPerson(Person person) throws PersonAlreadyExistException {
		List<Person> myList = getListPersons();
		int index = myList.indexOf(person);
		if (index >= 0) {
			log.info("Service - Person can not be saved because  : " + person.getFirstName() + " "
					+ person.getLastName() + " already exist");
			throw new PersonAlreadyExistException(" Service - Person Already exist ");
		}
		log.info("Service - Person is saved : " + person.getFirstName() + " " + person.getLastName());
		return personDAO.save(index, person);
	}

	@Override
	public String deletePerson(String firstName, String lastName) {
		Person person = personDAO.getPerson(firstName, lastName);
		if (person != null) {
			log.info("Service - Person deleted : " + firstName + " " + lastName);
			return personDAO.delete(person);
		}
		return "Person Not Deleted";
	}

	@Override
	public Person updatePerson(Person person) {
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		Person resultPersonFinded = personDAO.getPerson(firstName, lastName);

		if (resultPersonFinded != null) {
			List<Person> myList = getListPersons();
			int indexPosition = myList.indexOf(resultPersonFinded);
			log.info("Service - person updated: " + resultPersonFinded.getFirstName() + " "
					+ resultPersonFinded.getLastName());

			return personDAO.save(indexPosition, person);
		}
		throw new PersonNotFoundException(
				"The person that we want update not exist : " + person.getFirstName() + " " + person.getLastName());
	}

}
