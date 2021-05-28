package com.safetynet.alerts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.IFireStationService;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the requests for FireStation entity
 * 
 * @author Christine Duarte
 *
 */
@RestController()
@Slf4j
public class FireStationController {
	/**
	 * An instance of the IFireStationService
	 * 
	 * @see IFireStationService
	 */
	@Autowired
	IFireStationService fireStationService;

	/**
	 * Request get to obtain a FireStation
	 * 
	 * @param address - a String obtained from url request
	 * @return a fireStation object
	 * @throws EmptyFieldsException - when the field address is empty in the request
	 */
	
	/**  @GetMapping(value = "/firestation")
	public FireStation getFireStation(@Valid @RequestParam String address){
		log.debug("Controller - fireStation found: " + address);
		return fireStationService.getFireStation(address);
	}*/
	
	@GetMapping(value = "/firestation")
	public List<Object> getListPersonsCoveredByOneStation(@Valid @RequestParam String stationNumber) {
		log.debug("Controller - Request list person covered by station: "+ stationNumber);
		return fireStationService.getAddressCoveredByFireStation(stationNumber);
	}
	/**
	 * Request post to add a fireStation at the ArrayList
	 * 
	 * @param fireStation - a an object fireStation obtained by the body of the
	 *                    request
	 * @return the fireStation added in the ArrayList
	 * 
	 */
	@PostMapping(value = "/firestation")
	public FireStation saveFireStation(@Valid @RequestBody FireStation fireStation) {
		log.debug("Controller - fireStation saved: " + fireStation.getAddress() + " " + fireStation.getStation());
		return fireStationService.addFireStation(fireStation);
	}

	/**
	 * Request put to update a fireStation from the ArrayList
	 * 
	 * @param fireStation - an object fireStation obtained by the body of the
	 *                    request
	 * @return The fireStation updated in the ArrayList
	 */
	@PutMapping(value = "/firestation")
	public FireStation updateNumberFireStationOfOneAddress(@Valid @RequestBody FireStation fireStation) {
		log.debug("Controller - The number of station with address: " + fireStation.getAddress()
				+ " was updated with number station:" + fireStation.getStation());
		return fireStationService.updateFireStation(fireStation);
	}

	/**
	 * Request Delete to delete a fireStation from the ArrayList
	 * 
	 * @param address - a string containing the fireStation address
	 * @return a String with the message "SUCCESS" or "FireStation cannot be
	 *         deleted"
	 */
	@DeleteMapping(value = "/firestation")
	public String deleteFireStationByAddress(@Valid @RequestParam String address) {
		log.debug("Controller - fireStation deleted: address: " + address);
		return fireStationService.deleteFireStation(address);
	}

}
