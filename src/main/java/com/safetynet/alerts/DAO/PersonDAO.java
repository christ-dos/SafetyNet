package com.safetynet.alerts.DAO;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the CRUD methods and implement IPersonDAO interface
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDAO implements IPersonDAO {

	/**
	 * Attribute that contain the list of persons that provide from data.json
	 */
	@Autowired
	private List<Person> listPersons;

	/**
	 * Method that get the list of persons
	 * 
	 * @return An ArrayList of Persons
	 */
	@Override
	public List<Person> getPersons() {
		return listPersons;
	}
	
	/**
	 * Method that get the list of person by a list of addresses 
	 * 
	 * @return An ArrayList of Persons with address provided in parameter
	 */
	@Override
	public List<Person> getPersonsByListAdresses(List<String> listAddress){
		List<Person> listPersonGetByListAddress = listPersons.stream()
		.filter(person -> listAddress.contains(person.getAddress()))
		.map(person-> person)
		.collect(Collectors.toList());
		return listPersonGetByListAddress;
	}

	/**
	 * Method that get a person by combining keys firstName and lastName
	 * 
	 * @param firstName - The firstName
	 * @param lastName  - The lastName
	 * @return An instance of Person or null if the person not exist
	 */
	@Override
	public Person getPerson(String firstName, String lastName) {
		for (Person person : listPersons) {
			if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
				log.debug("DAO - Person found : " + firstName + " " + lastName);
				return person;
			}
		}
		log.debug("DAO - Person not found: "  + firstName  + " " + lastName);
		return null;
	}
	
	/**
	 * Method that get age by birthDate 
	 * 
	 * @param birthDate - A string containing the birthDate
	 * @return The value of age
	 */
	@Override
	public int getAge(String birthDate) {
		if (birthDate != null) {
			LocalDateTime bithDateParse = LocalDateTime.parse(birthDate, DateTimeFormat.forPattern("MM/dd/yyyy"));
			LocalDateTime currentDate = LocalDateTime.now();
			if (currentDate.isAfter(bithDateParse)) {
				Years age = Years.yearsBetween(bithDateParse, currentDate);
				log.info("DAO - Age calculate for bithDate: " + birthDate);
				return age.getYears();
			}
		}
		return -1;
	}
	
	/**
	 * Method that get person by address
	 * 
	 * @param address - A string containing address of person
	 * @return An instance of Person if exist else return null
	 */
	@Override
	public Person getPersonByAddress(String address) {
		for (Person person : listPersons) {
			if (person.getAddress().equalsIgnoreCase(address)) {
				log.debug("DAO - Person found with address : " + address);
				return person;
			}
		}
		log.debug("DAO - The address not found: "  + address);
		return null;
	}

	/**
	 * Method that save a Person in the ArrayList
	 * 
	 * @param index - An integer containing the position where will be saved the Person
	 * @param person - An instance of Person
	 * @return The Person that was saved in the arrayList
	 */
	@Override
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
	 * Method that delete a person from the ArrayList
	 * 
	 * @param person - The person we want deleted
	 * @return A String to confirm the deletion "SUCCESS"
	 */
	@Override
	public String delete(Person person) {
		listPersons.remove(person);
		log.debug("DAO - Person deleted : " + person.getFirstName() + " " + person.getLastName());
		return "SUCCESS";
	}
}
