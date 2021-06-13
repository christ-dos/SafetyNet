package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.Builder;

/**
 * A class which models the results of the endpoint
 * /flood/stations?stations=<list of station_number>
 * 
 * @author Christine Duarte
 *
 */
@Builder
public class PersonFloodDisplaying {
	/**
	 * A String that contain the address of the person
	 */
	private String address;
	
	/**
	 * An ArrayList of String with medications of person
	 */
	private List<PersonFlood> listPersonsFlood;
	
	/**
	 * getter for address
	 * @return A String containing address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * getter for listPersonsFlood
	 * @return A list of PersonFlood
	 */
	public List<PersonFlood> getListPersonsFlood() {
		return listPersonsFlood;
	}

}
