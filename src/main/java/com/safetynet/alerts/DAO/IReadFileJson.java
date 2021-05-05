package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public interface IReadFileJson {

	/**
	 * Method that read the file data.json 
	 * and add data in a arrayList persons
	 * @param string2 
	 * @param string 
	 * @return 
	 */
	public List<Person> readJsonFile(String arrayName);

}