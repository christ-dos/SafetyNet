package com.safetynet.alerts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.service.IFireStationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Christine Duarte
 *
 */
@RestController()
@Slf4j
public class FireStationController {

	@Autowired
	IFireStationService fireStationService = new FireStationService();

	@GetMapping(value = "/firestation")
	public FireStation getFireStation(@Valid @RequestParam String address) throws EmptyFieldsException {
		log.info("Controller - fireStation found: " + address);
		return fireStationService.getFireStation(address);
	}

	@PostMapping(value = "/firestation")
	public FireStation saveFireStation(@Valid @RequestBody FireStation fireStation) {
		log.info("Controller - fireStation saved: " + fireStation.getAddress() + " " + fireStation.getStation());
		return fireStationService.addFireStation(fireStation);
	}

	@PutMapping(value = "/firestation")
	public FireStation updateNumberFireStationOfOneAddress(@Valid @RequestBody FireStation fireStation) {
		log.info("Controller - Number of Station was updated: Station: " + fireStation.getStation() + " Address: "
				+ fireStation.getAddress());
		return fireStationService.updateFireStation(fireStation);
	}

}
