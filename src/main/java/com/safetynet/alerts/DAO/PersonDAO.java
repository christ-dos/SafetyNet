package com.safetynet.alerts.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	
	private  List<Person> persons = new ArrayList<>();
	
	/**
	 * Method that read the file data.json 
	 * and get the data of the array persons
	 * 
	 * @return persons - a list with firstName, LastName,
	 * address, city, phone, zip  and email
	 */
	@PostConstruct
	public  void readJsonFilePersons() {
		
		String FileJsonPath = "src/main/resources/data.json";
		
		if(FileJsonPath !=null) {
			
			try(FileInputStream fileDataJson = new FileInputStream(FileJsonPath)) {
				
				JsonReader jsonReader = Json.createReader(fileDataJson);
				JsonObject jsonObject = jsonReader.readObject();
				
				ObjectMapper mapper = new ObjectMapper();
				JsonArray jsonPersonsArray= jsonObject.getJsonArray("persons");
				persons = mapper.readValue(jsonPersonsArray.toString(),new TypeReference<List<Person>>(){});
				jsonReader.close();
				
			} catch (FileNotFoundException e1) {
				log.error("File not found", e1);
			} catch (IOException e) {
				log.error("The path of file does not be null", e);
			}
		}
	}
	
	public List<Person> getListPersons() {
		//persons = readJsonFilePersons();
		
		return getPersons();
	}
	
	public Person getPerson(String lastName, String firstName) {
		//persons = readJsonFilePersons();
		for(Person element : persons) {
			if(element.getLastName().equalsIgnoreCase(lastName) && element.getFirstName().equalsIgnoreCase(firstName)) {
				log.info("DAO person found : " + firstName + " " + lastName );
				return element;
			}
		}
		//log.info("Get the person : " + firstName + " " + lastName );
		return null;	
	}
	
	public Person save(Person person) {
		persons = getListPersons();
		if(person != null) {
			persons.add(person);
			setPersons(persons);
		}
		log.info("DAO person saved: " + person.getFirstName() + " " + person.getLastName() );
		return person;
	}
	
	public void delete(Person person) {
		persons.remove(person);
		log.info("DAO person deleted : " + person.getFirstName() + " " + person.getLastName());
	}
	
	public Person update(Person person) {
		//Person personToUpdate = null;
		
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		Person personToUpdate = getPerson(lastName, firstName);
		
		if(personToUpdate != null) {
			
			personToUpdate.setAddress(person.getAddress());
			personToUpdate.setCity(person.getCity());
			personToUpdate.setZip(person.getZip());
			personToUpdate.setPhone(person.getPhone());
			personToUpdate.setEmail(person.getEmail());
			
			save(personToUpdate);
			log.info("Dao person updated: " + person.getFirstName() + " " + person.getLastName());
		}
		return personToUpdate;
	}


}
