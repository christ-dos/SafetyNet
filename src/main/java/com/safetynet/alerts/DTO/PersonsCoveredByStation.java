package com.safetynet.alerts.DTO;

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
public class PersonsCoveredByStation {
	/**
	 * A list of {@link DisplayPartialPerson} 
	 * that contain only firstName, lastName, address and phone
	 */
	private List<DisplayPartialPerson> listPersonDTO;
	
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
	public List<DisplayPartialPerson> getListPersonDTO() {
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
