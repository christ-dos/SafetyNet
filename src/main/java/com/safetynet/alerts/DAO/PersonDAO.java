package com.safetynet.alerts.DAO;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
	

	@Autowired
	private IReadFileJson reader;
	
	private List<Person> persons; 
	
	
	
	@PostConstruct
	public void runnerData() {
		persons = reader.readJsonFile("persons");
		log.info("readPersonn " + persons );
	}
	
	public List<Person> getPersons() {
		 return persons;
	}
	
	public Person getPerson(String firstName, String lastName) {
		for(Person person : persons) {
			if(person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
				log.info("DAO - Person found : " + firstName + " " + lastName );
				return person;
			}
		}
		return null;
	}
	
	public Person save(int index, Person person) {
		persons.add(index, person);
		log.info("DAO - Person saved: " + person.getFirstName() + " " + person.getLastName() );
		return person;
	}
	
	public String delete(Person person) {
		persons.remove(person);
		log.info("DAO - Person deleted : " + person.getFirstName() + " " + person.getLastName());
		return "SUCESS";
	}
	

}
