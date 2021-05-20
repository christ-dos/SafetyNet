package com.safetynet.alerts.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that read the file Json
 * 
 * @author Chrsitine Duarte
 *
 */
@Component
@Slf4j
public class ReadFileJson implements IReadFileJson {

	/**
	 * Method that read the file data.json and add data in a arrayList persons
	 * 
	 * @param filePathJson - a String that contain the path of the file
	 * 
	 * @return an jsonObject that contain jsonArrays of the file
	 */
	@Override
	public JsonObject readJsonFile() {
		String filePathJson = "src/main/resources/data.json";
		JsonObject jsonObject = null;
		if (filePathJson != null) {
			try (FileInputStream fileDataJson = new FileInputStream(filePathJson)) {
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
