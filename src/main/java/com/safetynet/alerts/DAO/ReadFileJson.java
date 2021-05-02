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


@Repository
@Slf4j
@Data
public class ReadFileJson implements IReadFileJson {
	
private  List<Person> persons = new ArrayList<>();

	/**
	 * Method that read the file data.json 
	 * and add data in a arrayList persons
	 */
	@Override
	@PostConstruct
	public  void readJsonFilePersons() {
		String filePathJson = "src/main/resources/data.json";
		String tabNameInFileJson = "persons";
		if(filePathJson !=null) {
			try(FileInputStream fileDataJson = new FileInputStream(filePathJson)) {
				JsonReader jsonReader = Json.createReader(fileDataJson);
				JsonObject jsonObject = jsonReader.readObject();
				
				ObjectMapper mapper = new ObjectMapper();
				JsonArray jsonPersonsArray= jsonObject.getJsonArray(tabNameInFileJson);
				persons = mapper.readValue(jsonPersonsArray.toString(),new TypeReference<List<Person>>(){});
				jsonReader.close();
				log.info("la methode read a ete appelleé" + persons);
			} catch (FileNotFoundException e1) {
				log.error("File not found", e1);
			} catch (IOException e) {
				log.error("The path of file does not be null", e);
			}
		}
	}
	
}
