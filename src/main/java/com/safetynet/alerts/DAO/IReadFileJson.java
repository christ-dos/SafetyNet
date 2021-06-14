package com.safetynet.alerts.DAO;

import javax.json.JsonObject;

/**
 * Interface that read a Json file
 * 
 * @author Christine Duarte
 *
 */
public interface IReadFileJson {

	/**
	 * Method that read the file data.json and add data in a arrayList persons
	 * 
	 * @return a Json object that contain JsonArrays
	 */
	public JsonObject readJsonFile();
}