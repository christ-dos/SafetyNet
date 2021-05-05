package com.safetynet.alerts.DAO;

import java.util.List;

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
	private ReadFileJson readFileJson;
	
	private List<Person> persons; 
	
	//private String filePathJson = "src/main/resources/data.json";
	
	//private String tabNameInFileJson = "persons";
	

	@Autowired
	public PersonDAO( ReadFileJson readFileJson) {
		this.persons = readFileJson.getPersons();
	}
	
	public PersonDAO() {
	}

	public List<Person> getPersons() {
		log.info("ma liste persons dans personsdao " );
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
		persons.set(indexPosition, person);
		log.info("DAO - Person updated : " + person.getFirstName() + " " + person.getLastName());
		return person;
	}

}
