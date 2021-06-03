package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.CityNotFoundException;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.model.CommunityEmailDTO;
import com.safetynet.alerts.model.Person;

/**
 * An interface that manage methods CRUD of the service of the entity Person
 * 
 * @author Christine Duarte
 *
 */
public interface IPersonService {

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of Person getted
	 * @throws EmptyFieldsException when the field firstName or lastName is empty
	 */
	public Person getPerson(String firstName, String lastName) throws EmptyFieldsException;

	/**
	 * Method that add a person
	 * 
	 * @param person - an instance of Person
	 * @return the person added
	 * @throws PersonAlreadyExistException when the person that we want added
	 *                                     already exist
	 */
	public Person addPerson(Person person) throws PersonAlreadyExistException ;

	/**
	 * Method that delete a person
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return a String to confirm or deny the deletion
	 */
	public String deletePerson(String firstName, String lastName);

	/**
	 * Method that update a person
	 * 
	 * @param person - an instance of Person
	 * @return the person updated
	 */
	public Person updatePerson(Person person);
	
	/**
	 * Method that filters the list of persons to get emails for a city input
	 * 
	 * @param city - The city for which we want obtained the emails
	 * @return the list filtered containing the emails
	 * @throws CityNotFoundException
	 */
	public CommunityEmailDTO getEmailResidents(String city);
}