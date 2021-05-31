package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.PersonResultEndPointByStationNumberDTO;
import com.safetynet.alerts.service.IPersonDTOByStationService;

import lombok.extern.slf4j.Slf4j;

@RestController()
@Slf4j
public class PersonByStationNumberDTOController {
	
	@Autowired
	IPersonDTOByStationService personDTOByStationService;
	
	@GetMapping(value = "/firestation")
	public PersonResultEndPointByStationNumberDTO getListPersonsCoveredByOneStation(@Valid @RequestParam  String station) {
		log.debug("Controller - Request list person covered by station: " + station);
		return personDTOByStationService.getAddressCoveredByFireStation(station);
	}

}
