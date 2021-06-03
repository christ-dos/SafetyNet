package com.safetynet.alerts.service;

import com.safetynet.alerts.model.ListPersonByStationNumberDTO;
import com.safetynet.alerts.model.PhoneAlertDTO;

public interface IByStationNumberDTOService {

	/**
	 * Method that get the list of persons covered by station number
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the number of child and adult
	 */
	public ListPersonByStationNumberDTO getAddressCoveredByFireStation(String station);
	
	/**
	 * Method that filters the list of persons to get phone number covered by a station number
	 * 
	 * @param station - The station for which we want obtained the phones
	 * @return the list filtered containing the phones
	 */
	public PhoneAlertDTO getPhoneAlertResidentsCoveredByStation(String station);

}