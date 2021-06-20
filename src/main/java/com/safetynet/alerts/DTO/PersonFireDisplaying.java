package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * A class which models the results of the endpoint /fire?address=<address>
 * 
 * @author Christine Duarte
 *
 */
@AllArgsConstructor
public class PersonFireDisplaying {
	/**
	 * A list of persons that displaying firstName, lastName, phone, age and medical
	 * history
	 */
	private List<PersonFlood> listPersonFire;

	/**
	 * A String that contain the station number that covers the address of the fire
	 */
	private String stationNumber;

	/**
	 * getter for listPersonFire
	 * 
	 * @return An ArrayList containing a list of persons
	 */
	public List<PersonFlood> getListPersonFire() {
		return listPersonFire;
	}

	/**
	 * getter for stationNumber
	 * 
	 * @return A String containing the station number
	 */
	public String getStationNumber() {
		return stationNumber;
	}
}
