package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
/**
 * A class which models the results of the endpoint 
 * fireStation?stationNumber=<station_number>
 * 
 * @author Christine Duarte 
 *
 */

@AllArgsConstructor
public class ListPersonByStationNumberDTO {
	/**
	 * A list of {@link PersonDTO} 
	 * that contain only firstName, lastName, address and phone
	 */
	private List<PersonDTO> listPersonDTO;
	
	/** 
	 *A counter that counts adults
	 */
	private Integer adultsCounter ;
	
	/** 
	 *A counter that counts childs
	 */
	private Integer childsCounter;
	
	/**
	 * getter for listPersonDTO
	 * @return a list with field firstName, 
	 * lastName, address and phone of person
	 */
	public List<PersonDTO> getListPersonDTO() {
		return listPersonDTO;
	}
	
	/**
	 * getter for adultsCounter
	 * @return an Integer which counts the number of adults
	 */
	public Integer getAdultsCounter() {
		return adultsCounter;
	}

	/**
	 * getter for childsCounter
	 * @return an Integer which counts the number of childs
	 */
	public Integer getChildsCounter() {
		return childsCounter;
	}
}
