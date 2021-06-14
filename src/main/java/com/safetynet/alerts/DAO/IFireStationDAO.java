package com.safetynet.alerts.DAO;

import java.util.List;

import com.safetynet.alerts.model.FireStation;

/**
 * Interface that manage methods CRUD of entity FireStation
 * 
 * @author Christine Duarte
 *
 */
public interface IFireStationDAO {
	/**
	 * Method that get the list of fireStations
	 * 
	 * @return an ArrayList of FireStation
	 */
	public List<FireStation> getFireStations();

	/**
	 * Method that get the list of addresses covered by fireStation with station
	 * number
	 * 
	 * @station - a number of fireStation
	 * 
	 * @return an ArrayList of String with addresses covered by fireStaiton
	 */
	public List<String> getAddressesCoveredByStationNumber(String station);

	/**
	 * Method that get a fireStation by address
	 * 
	 * @param address - A String containing the address
	 * @return an instance of FireStation
	 */
	public FireStation get(String address);

	/**
	 * Method that save a fireStation in the ArrayList
	 * 
	 * @param index - the position where will be saved the FireStation
	 * @return - FireStation that was saved in the arrayList
	 */
	public FireStation save(FireStation fireStation, int index);

	/**
	 * Method that delete a FireStation from the ArrayList
	 * 
	 * @param fireStation - fireStation we want deleted
	 * @return a String to confirm the deletion
	 */
	public String delete(FireStation fireStation);
}
