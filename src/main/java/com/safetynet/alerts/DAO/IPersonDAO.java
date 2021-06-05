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
	 * Method that get the list of person by a list of addresses 
	 * 
	 * @return An ArrayList of Persons with address provided in parameter
	 */
	public List<Person> getPersonsByListAdresses(List<String> listAddress);

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - the firstName
	 * @param lastName  - the lastName
	 * @return an instance of Person
	 */
	public Person getPerson(String firstName, String lastName);
	
	/**
	 * Method that get a person by address
	 * 
	 * @param address - A string containing address of person
	 * @return A Person
	 */
	public Person getPersonByAddress(String address);
	
	/**
	 * Method that get age by birthDate 
	 * 
	 * @param birthDate - A string containing the birthDate
	 * @return The value of age
	 */
	//public int getAge(String birthDate);

	/**
	 * Method that save a Person in the ArrayList
	 * 
	 * @param index - the position where will be saved the Person
	 * @return - Person that was saved in the arrayList
	 */
	public Person save(int index, Person person);

	/**
	 * Method that delete a Person from the ArrayList
	 * 
	 * @param person - person we want deleted
	 * @return a String to confirm the deletion
	 */
	public String delete(Person person);
}
