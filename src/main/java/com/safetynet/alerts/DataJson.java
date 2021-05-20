package com.safetynet.alerts;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DAO.ReadFileJson;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage data of the Json file
 * 
 * @author Christine Duarte
 *
 */
@Component
@Slf4j
public class DataJson {
	/**
	 * An instance of ReadFileJson class that read the Json file
	 * 
	 * @see ReadFileJson
	 */
	@Autowired
	private ReadFileJson reader;
	/**
	 * An instance of ObjectMapper that provides functionality for reading and
	 * writing JSON
	 * 
	 * @see ObjectMapper
	 */
	@Autowired
	private ObjectMapper mapper;

	/**
	 * An arrayList that contain the list of Person
	 */
	private List<Person> persons;
	
	/**
	 * An arrayList that contain the list of FireStation
	 */
	private List<FireStation> fireStation;

	/**
	 * An instance of JsonObject that can be created from an input source using
	 * JsonReader.readObject().
	 * 
	 * @see JsonObject
	 */
	private JsonObject jsonObject;

	/**
	 * Constructor without parameter
	 */
	public DataJson() {
		super();
	}

	/**
	 * Constructor with all parameters
	 */
	

	/**
	 * Method that get the JsonObject that is read by the method readFileJson
	 * 
	 * @return A JsonObject of the file data.json
	 */
	private JsonObject getObjectJson() {
		return reader.readJsonFile();
	}

	public DataJson(ReadFileJson reader, ObjectMapper mapper, List<Person> persons, List<FireStation> fireStation,
			JsonObject jsonObject) {
		super();
		this.reader = reader;
		this.mapper = mapper;
		this.persons = persons;
		this.fireStation = fireStation;
		this.jsonObject = jsonObject;
	}

	/**
	 * Method that extract the JsonArray "persons" of the JsonObject and map in an
	 * arrayList of Persons
	 * 
	 * @return A list of persons that was contained in the file Json
	 */
	@Bean
	public List<Person> listPersons() {
		String ArrayName = "persons";
		jsonObject = getObjectJson();
		try {
			JsonArray jsonPersonsArray = jsonObject.getJsonArray(ArrayName);
			persons = mapper.readValue(jsonPersonsArray.toString(), new TypeReference<List<Person>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("DataJson - Error occured during deserialization of the JsonArray persons");
			e.printStackTrace();
		}
		return persons;
	}
	
	/**
	 * Method that extract the JsonArray "firestations" of the JsonObject and map in an
	 * arrayList of FireStations
	 * 
	 * @return A list of fireStation  that was contained in the file Json
	 */
	@Bean
	public List<FireStation> listFireStations() {
		String ArrayName = "firestations";
		jsonObject = getObjectJson();
		try {
			JsonArray jsonPersonsArray = jsonObject.getJsonArray(ArrayName);
			fireStation = mapper.readValue(jsonPersonsArray.toString(), new TypeReference<List<FireStation>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("DataJson - Error occured during deserialization of the JsonArray persons");
			e.printStackTrace();
		}
		log.info("fireStation: " + fireStation);
		return fireStation;
	}
}
