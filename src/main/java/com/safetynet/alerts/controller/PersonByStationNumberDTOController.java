package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.PersonResultEndPointByStationNumberDTO;
import com.safetynet.alerts.service.IPersonDTOByStationService;

import lombok.extern.slf4j.Slf4j;
/**
 * Class that manage the requests for PersonByStationNumberDTOController 
 * 
 * @author Christine Duarte
 *
 */
@RestController()
@Slf4j
public class PersonByStationNumberDTOController {
	/**
	 * An instance of {@link PersonDTOByStationService}
	 */
	@Autowired
	IPersonDTOByStationService personDTOByStationService;
	
	/**
	 * Request get to obtain a list of person DTO covered by a fireStation station number
	 * and a counter of adults and childs
	 * 
	 * @param station - A String with a number of the station
	 * @return A list of PersonDTO and a counter of adults and childs
	 */
	@GetMapping(value = "/firestation")
	public PersonResultEndPointByStationNumberDTO getListPersonsCoveredByOneStation(@Valid @RequestParam  String station) {
		log.debug("Controller - Request list person covered by station: " + station);
		return personDTOByStationService.getAddressCoveredByFireStation(station);
	}
}
