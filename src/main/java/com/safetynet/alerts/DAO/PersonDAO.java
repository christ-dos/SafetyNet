package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the CRUD methods and implement IPersonDAO interface
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
public class PersonDAO implements IPersonDAO {

	/**
	 * attribute that contain the list of persons that provide from data.json
	 */
	@Autowired
	private List<Person> listPersons;

	/**
	 * Constructor with the parameter listPersons
	 * 
	 * @param listPersons - an ArrayList with the list of persons
	 */
	public PersonDAO(List<Person> listPersons) {
		super();
		this.listPersons = listPersons;
	}

	/**
	 * Method that get the list of persons
	 * 
	 * @return an ArrayList of Persons
	 */
	public List<Person> getPersons() {
		return listPersons;
	}

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of Person or null if the person not exist
	 */
	public Person getPerson(String firstName, String lastName) {
		for (Person person : listPersons) {
			if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
				log.debug("DAO - Person found : " + firstName + " " + lastName);
				return person;
			}
		}
		return null;
	}

	/**
	 * Method that save a Person in the ArrayList
	 * 
	 * @param index - An integer containing the position where will be saved the Person
	 * @param person - an instance of Person
	 * @return - The Person that was saved in the arrayList
	 */
	public Person save(int index, Person person) {
		if (index < 0) {
			listPersons.add(person);
		} else {
			listPersons.set(index, person);
		}
		log.debug("DAO - Person saved: " + person.getFirstName() + " " + person.getLastName());
		return person;
	}

	/**
	 * Method that delete a Person from the ArrayList
	 * 
	 * @param person - The person we want deleted
	 * @return a String to confirm the deletion "SUCCESS"
	 */
	public String delete(Person person) {
		listPersons.remove(person);
		log.debug("DAO - Person deleted : " + person.getFirstName() + " " + person.getLastName());
		return "SUCCESS";
	}

}
