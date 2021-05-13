package com.safetynet.alerts.DAO;

import javax.json.JsonObject;

import org.springframework.stereotype.Repository;

@Repository
public interface IReadFileJson {

	/**
	 * Method that read the file data.json 
	 * and add data in a arrayList persons
	 * @param string2 
	 * @param string 
	 * @return 
	 */
	public JsonObject readJsonFile();

}