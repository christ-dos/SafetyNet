package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * A class which models the results of the endpoint 
 * fireStation?stationNumber=<station_number>
 * 
 * @author Christine Duarte 
 *
 */
@Data
@AllArgsConstructor
public class ListPersonByStationNumberDTO {
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
	
}
