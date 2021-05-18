package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.model.Person;

/**
 * An interface that manage methods CRUD of the service
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
	 * @return an instance of person
	 * @throws EmptyFieldsException when the field firstName or lastName is empty
	 */
	public Person getPerson(String firstName, String lastName) throws EmptyFieldsException;

	/**
	 * Method that add a person
	 * 
	 * @param person - an instance of person
	 * @return the person added
	 * @throws PersonAlreadyExistException when the person that we want added
	 *                                     already exist
	 */
	public Person addPerson(Person person) throws PersonAlreadyExistException;

	/**
	 * Method that delete a person
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return a String to confirm the deletion
	 */
	public String deletePerson(String firstName, String lastName);

	/**
	 * Method that update a person
	 * 
	 * @param person - an instance of person
	 * @return the person updated
	 */
	public Person updatePerson(Person person);
}