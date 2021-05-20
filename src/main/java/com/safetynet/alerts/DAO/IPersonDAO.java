package com.safetynet.alerts.DAO;

import java.util.List;

import com.safetynet.alerts.model.Person;

/**
 * Interface that manage methods CRUD of entity Person
 * 
 * @author Christine Duarte
 *
 */
public interface IPersonDAO {
	/**
	 * Method that get the list of persons
	 * 
	 * @return an ArrayList of Persons
	 */
	public List<Person> getPersons();

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of Person
	 */
	public Person getPerson(String firstName, String lastName);

	/**
	 * Method that save a Person in the ArrayList
	 * 
	 * @param index - the position where will be saved the Person
	 * @return - object that will be saved in the arrayList
	 */
	public Person save(int index, Person person);

	/**
	 * Method that delete a Person from the ArrayList
	 * 
	 * @param person we want deleted
	 * @return a String to confirm the deletion
	 */
	public String delete(Person person);
}
