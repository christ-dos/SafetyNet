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
public class PersonResultEndPointByStationNumberDTO {
	/**
	 * A list of {@link PersonDTO} 
	 * that contain only firstName, lastName, address and phone
	 */
	private List<PersonDTO> listPersonDTO;
	
	/** 
	 *a counter that counts adults
	 */
	private Integer adultsCounter ;
	
	/** 
	 *a counter that counts childs
	 */
	private Integer childsCounter;
	
	/**
	 * method which get the listPersonDTO
	 * @return a list of PersonDTO
	 */
	public List<PersonDTO> getListPersonDTO() {
		return listPersonDTO;
	}

	/**
	 * method which get the adultsCounter
	 * @return A Integer containing the value of adultsCounter
	 */
	public Integer getAdultsCounter() {
		return adultsCounter;
	}
	
	/**
	 * method which get the childsCounter
	 * 
	 * @return A Integer containing the value of childsCounter
	 */
	public Integer getChildsCounter() {
		return childsCounter;
	}
}
