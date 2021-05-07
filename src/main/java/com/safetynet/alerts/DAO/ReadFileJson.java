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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@JsonComponent
@Slf4j
@Data
public class ReadFileJson implements IReadFileJson {
	
	private String filePathJson = "src/main/resources/data.json";
	
	
	@Autowired
	ObjectMapper mapper;

	/**
	 * Method that read the file data.json 
	 * and add data in a arrayList persons
	 * @return 
	 */
	@Override
	public List<Person> readJsonFile(String ArrayName) {
		List<Person> persons = new ArrayList<>();
		if(filePathJson!= null) {
			try(FileInputStream fileDataJson = new FileInputStream(filePathJson)) {
				JsonReader jsonReader = Json.createReader(fileDataJson);
				JsonObject jsonObject = jsonReader.readObject();
				JsonArray jsonPersonsArray= jsonObject.getJsonArray(ArrayName);
				persons = mapper.readValue(jsonPersonsArray.toString(),new TypeReference<List<Person>>(){});
				jsonReader.close();
				log.info("readjsonFile - Method was called");
			} catch (FileNotFoundException e1) {
				log.error("File not found", e1);
			} catch (IOException e) {
				log.error("The path of file does not be null", e);
			}
		}
		return persons;
	}

}
	
