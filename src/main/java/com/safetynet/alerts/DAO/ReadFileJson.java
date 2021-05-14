package com.safetynet.alerts.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.boot.jackson.JsonComponent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@JsonComponent
@Slf4j
@Data
public class ReadFileJson implements IReadFileJson {
	
	private String filePathJson = "src/main/resources/data.json";
	

	/**
	 * Method that read the file data.json 
	 * and add data in a arrayList persons
	 * @param  
	 * @return 
	 */
	@Override
	public JsonObject readJsonFile() {
		JsonObject jsonObject = null;
		if(filePathJson!= null) {
			try(FileInputStream fileDataJson = new FileInputStream(filePathJson)) {
				JsonReader jsonReader = Json.createReader(fileDataJson);
				jsonObject = jsonReader.readObject();
				jsonReader.close();
				log.info("readjsonFile - Method was called");
			} catch (FileNotFoundException e1) {
				log.error("File not found", e1);
			} catch (IOException e) {
				log.error("The path of file does not be null", e);
			}
		}
		return jsonObject;
	}

}
	
