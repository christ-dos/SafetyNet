package com.safetynet.alerts.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the file Json with data sources
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Data
public class PersonDAO implements IPersonDAO {
	
	private List<Person> persons = new ArrayList<>();
	
	
	public PersonDAO(ReadFileJson readFileJson) {
		this.persons = readFileJson.getPersons();
		
	}

	public List<Person> getPersons() {
		 return persons;
	}
	
	public Person getPerson(String firstName, String lastName) {
		for(Person person : persons) {
			if(person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
				log.info("DAO - Person not found : " + firstName + " " + lastName );
				return person;
			}
		}
		return null;
	}
	
	public Person save(Person person) {
		persons.add(person);
		log.info("DAO - Person saved: " + person.getFirstName() + " " + person.getLastName() );
		return person;
	}
	
	public void delete(Person person) {
		persons.remove(person);
		log.info("DAO - Person deleted : " + person.getFirstName() + " " + person.getLastName());
	}
	
	public Person update(int indexPosition, Person person) {
		log.info("DAO - Person updated : " + person.getFirstName() + " " + person.getLastName());
		persons.set(indexPosition, person);
		return person;
	}
}
