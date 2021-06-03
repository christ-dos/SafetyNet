package com.safetynet.alerts.service;

import com.safetynet.alerts.model.PersonResultEndPointByStationNumberDTO;

public interface IPersonDTOByStationService {

	/**
	 * Method that get the list of persons covered by station number
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the number of child and adult
	 */
	public PersonResultEndPointByStationNumberDTO getAddressCoveredByFireStation(String station);

}