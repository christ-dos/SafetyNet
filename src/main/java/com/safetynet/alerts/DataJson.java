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
	 * An arrayList that contain the list of Persons
	 */
	private List<Person> persons;

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
	public DataJson(ReadFileJson reader, ObjectMapper mapper, List<Person> persons, JsonObject jsonObject) {
		super();
		this.reader = reader;
		this.mapper = mapper;
		this.persons = persons;
		this.jsonObject = jsonObject;
	}

	/**
	 * Method that get the JsonObject that is read by the method readFileJson
	 * 
	 * @return A JsonObject of the file data.json
	 */
	private JsonObject getObjectJson() {
		return reader.readJsonFile();
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
}
