package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the file Json with data sources
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
//@Data
public class PersonDAO implements IPersonDAO {

	@Autowired
	private List<Person> listPersons;

	public PersonDAO(List<Person> listPersons) {
		super();
		this.listPersons = listPersons;
	}
	
	public PersonDAO() {
		
	}
	public List<Person> getPersons() {
		return listPersons;
	}

	public Person getPerson(String firstName, String lastName) {
		for (Person person : listPersons) {
			if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
				log.info("DAO - Person found : " + firstName + " " + lastName);
				return person;
			}
		}
		return null;
	}

	public Person save(int index, Person person) {
		if (index < 0) {
			listPersons.add(person);
		} else {
			listPersons.set(index, person);
		}
		log.info("DAO - Person saved: " + person.getFirstName() + " " + person.getLastName());
		return person;
	}

	public String delete(Person person) {
		listPersons.remove(person);
		log.info("DAO - Person deleted : " + person.getFirstName() + " " + person.getLastName());
		return "SUCESS";
	}

}
