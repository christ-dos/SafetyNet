package com.safetynet.alerts.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class PersonDataJsonDAO implements IPersonDAO {
	
	
	private  List<Person> persons = new ArrayList<>();
	/**
	 * Method that read the file data.json 
	 * and get the data of the array persons
	 * 
	 * @return persons - a list with firstName, LastName,
	 * address, city, phone, zip  and email
	 */
	
	public List<Person> readJsonFilePersons() {
		
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
				
				log.error("File not found");
				e1.printStackTrace();
			} catch (IOException e) {
				
				log.error("The path of file does not be null");
				e.printStackTrace();
			}
		}
	
		return persons;
	}
	
	public List<Person> getListPersons() {
		persons = readJsonFilePersons();
		//setPersons(persons);
		return getPersons();
	}
	
	public List<Person> save(Person person) {
		persons = getListPersons();
		if(person != null) {
			persons.add(person);
			setPersons(persons);
		}
		return persons;
	}
	
	public void deletePerson(Person person) {
		
		persons.remove(person);
	}


}
