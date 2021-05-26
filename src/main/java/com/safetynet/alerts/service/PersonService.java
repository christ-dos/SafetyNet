package com.safetynet.alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.CityNotFoundException;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Class service that manage the methods CRUD for Person entity
 * 
 * @author Christine Duarte
 * 
 * @see Person
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
public class PersonService implements IPersonService {
	/**
	 * An instance of {@link PersonDAO}
	 * 
	 */
	@Autowired
	private IPersonDAO personDAO;


	/**
	 * Method private that get a list of persons
	 * 
	 * @return An ArrayList of Person
	 */
	private List<Person> getListPersons() {
		return personDAO.getPersons();
	}

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of Person getted
	 * @throws EmptyFieldsException  when the field firstName or lastName is empty
	 * @throws PersonNotFoundException when person is not found
	 */
	@Override
	public Person getPerson(String firstName, String lastName){
		if (firstName.isEmpty() || lastName.isEmpty()) {
			log.error("Service - The fields firstName and lastName cannot be empty ");
			throw new EmptyFieldsException("Field cannot be empty");
		}
		Person person = personDAO.getPerson(firstName, lastName);
		if (person != null) {
			log.debug("Service - Person found : " + person.getFirstName() + " " + person.getLastName());
			return person;
		}
		log.error("Service - Person not found: " + firstName +  " " + lastName);
		throw new PersonNotFoundException("Service - Person not found exception");
	}

	/**
	 * Method that add a {@link Person}
	 * 
	 * @param person - An instance of Person
	 * @return the person added
	 * @throws PersonAlreadyExistException when the person that we want added
	 *                                     already exist
	 */
	@Override
	public Person addPerson(Person person){
		List<Person> personList = getListPersons();
		int index = personList.indexOf(person);
		// person Already exist
		if (index >= 0) {
			log.error("Service - Person cannot be saved because: " + person.getFirstName() + " "
					+ person.getLastName() + " already exist");
			throw new PersonAlreadyExistException("Person already exist");
		}
		log.debug("Service - Person is saved : " + person.getFirstName() + " " + person.getLastName());
		return personDAO.save(index, person);
	}

	/**
	 * Method that delete a person
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return a String to confirm or deny the deletion
	 */
	@Override
	public String deletePerson(String firstName, String lastName) {
		Person person = personDAO.getPerson(firstName, lastName);
		if (person != null) {
			log.debug("Service - Person deleted : " + firstName + " " + lastName);
			return personDAO.delete(person);
		}
		log.error("Service - Person cannot be deleted : " + firstName + " " + lastName + " because not exist");
		return "Person not Deleted";
	}

	/**
	 * Method that update a person
	 * 
	 * @param person - an instance of Person
	 * @return the person updated
	 * @throws PersonNotFoundException when the person that we want update not exist
	 */
	@Override
	public Person updatePerson(Person person) {
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		Person resultPersonFinded = personDAO.getPerson(firstName, lastName);

		if (resultPersonFinded == null) {
			log.error("Service - The person that we want update not exist : " + person.getFirstName() + " "
					+ person.getLastName());
			throw new PersonNotFoundException(
					"The person that we want update not exist : " + person.getFirstName() + " " + person.getLastName());
		}
		List<Person> personList = getListPersons();
		int indexPosition = personList.indexOf(resultPersonFinded);
		resultPersonFinded.setAddress(person.getAddress());
		resultPersonFinded.setCity(person.getCity());
		resultPersonFinded.setZip(person.getZip());
		resultPersonFinded.setEmail(person.getEmail());
		resultPersonFinded.setPhone(person.getPhone());
		log.debug("Service - Person updated: " + resultPersonFinded.getFirstName() + " "
				+ resultPersonFinded.getLastName());

		return personDAO.save(indexPosition, person);
	}
	
	/**
	 * Method that filters the list of persons to get emails for a city input
	 * 
	 * @param city - The city for which we want obtained the emails
	 * @return the list filtered containing the emails
	 * @throws CityNotFoundException
	 */
	@Override
	public List<String> getEmailResidents(String city) {
		List<Person> personList = getListPersons();
		List<String> listEmailResidents = personList.stream().filter(person -> person.getCity().equalsIgnoreCase(city))
				.map(person -> person.getEmail()).collect(Collectors.toList());
		if (listEmailResidents.isEmpty()) {
			log.error("Service - The city requested not found: " + city);
			throw new CityNotFoundException("The City: " + city + " not found, please try again");
		}
		log.info("Service - The list of emails of residents of the city: " + city + " has been requested");
		return listEmailResidents;
	}
}
