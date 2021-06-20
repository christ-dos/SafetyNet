package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.FireStation;

/**
 * An interface that manage methods CRUD of the service of the entity
 * FireStation
 * 
 * @author Christine Duarte
 *
 */
public interface IFireStationService {
	
	/**
	 * Method that get a list of fireStations
	 * 
	 * @return An ArrayList of FireStation
	 */
	public List<FireStation> getListFireStations();
	
	/**
	 * Method that get a fireStation by address
	 * 
	 * @param address - A String that contain the address
	 * @return an instance of FireStation getted
	 * @throws EmptyFieldsException when the field address is empty
	 */
	public FireStation getFireStation(String address) throws EmptyFieldsException;

	/**
	 * Method that add a fireStation
	 * 
	 * @param fireStation - an instance of FireStation
	 * @return the fireStation added
	 */
	public FireStation addFireStation(FireStation fireStation);

	/**
	 * Method that delete a fireStation
	 * 
	 * @param fireStation - an instance of FireStation
	 * @return a String to confirm or deny the deletion
	 */
	public String deleteFireStation(String address);

	/**
	 * Method that update a fireStation
	 * 
	 * @param fireStation - an instance of FireStation
	 * @return the fireStation updated
	 */
	public FireStation updateFireStation(FireStation fireStation);

}
