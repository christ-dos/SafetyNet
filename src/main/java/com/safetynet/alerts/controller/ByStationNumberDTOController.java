package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.ListPersonByStationNumberDTO;
import com.safetynet.alerts.model.PhoneAlertDTO;
import com.safetynet.alerts.service.IByStationNumberDTOService;

import lombok.extern.slf4j.Slf4j;
/**
 * Class that manage the requests for PersonByStationNumberDTOController 
 * 
 * @author Christine Duarte
 *
 */
@RestController()
@Slf4j
public class ByStationNumberDTOController {
	/**
	 * An instance of {@link PersonDTOByStationService}
	 */
	@Autowired
	IByStationNumberDTOService byStationNumberDTOService;
	
	/**
	 * Request get to obtain a list of person DTO covered by a fireStation station number
	 * and a counter of adults and childs
	 * 
	 * @param station - A String with a number of the station
	 * @return A list of PersonDTO and a counter of adults and childs
	 */
	@GetMapping(value = "/firestation")
	public ListPersonByStationNumberDTO getListPersonsCoveredByStation(@Valid @RequestParam  String station) {
		log.debug("Controller - Request list person covered by station: " + station);
		return byStationNumberDTOService.getAddressCoveredByFireStation(station);
	}
	
	/**
	 * Request get to obtain a list of phone of person covered by a fireStation station number
	 * 
	 * @param station - A String with a number of the station
	 * @return A list of phone of person covered by station
	 */
	@GetMapping(value = "/phoneAlert")
	public PhoneAlertDTO getPhoneAlertResidentsCoveredByStation(@Valid @RequestParam  String station) {
		log.debug("Controller - Request list of phones of persons covered by station: " + station);
		return byStationNumberDTOService.getPhoneAlertResidentsCoveredByStation(station);
	}
}
