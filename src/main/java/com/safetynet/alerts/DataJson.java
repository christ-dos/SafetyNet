package com.safetynet.alerts;

import java.util.ArrayList;
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

	

	

	@Autowired
	private ReadFileJson reader;

	@Autowired
	private ObjectMapper mapper;

	private List<Person> persons = new ArrayList<>();

	private JsonObject jsonObject;
	
	
	public DataJson() {
		super();
	}

	public DataJson(ReadFileJson reader, ObjectMapper mapper, List<Person> persons, JsonObject jsonObject) {
		super();
		this.reader = reader;
		this.mapper = mapper;
		this.persons = persons;
		this.jsonObject = jsonObject;
	}

	private JsonObject getObjectJson() {
		return reader.readJsonFile();
	}

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
