package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * class service that manage the methods CRUD for FireStation entity
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
public class FireStationService implements IFireStationService {
	/**
	 * An instance of {@link FireStationDAO}
	 * 
	 * @see FireStationDAO
	 */
	@Autowired
	private IFireStationDAO fireStationDAO;

	/**
	 * Constructor of FireStationService without parameter
	 */
	public FireStationService() {
		super();
	}

	/**
	 * Constructor of FireStationService with as parameter an instance of
	 * {@link FireStationDAO}
	 */
	public FireStationService(IFireStationDAO fireStationDAO) {
		super();
		this.fireStationDAO = fireStationDAO;
	}

	/**
	 * Method private that get a list of fireStations
	 * 
	 * @return An ArrayList of FireStation
	 */
	private List<FireStation> getListFireStations() {
		return fireStationDAO.getFireStations();
	}

	/**
	 * Method that get a fireStation by address
	 * 
	 * @param address - A String containing the address of fireStation
	 * @return an instance of fireStation getted
	 * @throws EmptyFieldsException         when the field address is empty
	 * @throws FireStationNotFoundException when fireStation is not found
	 */
	@Override
	public FireStation getFireStation(String address){
		if (address.isEmpty()) {
			log.error("Service - field can not be empty");
			throw new EmptyFieldsException("Field cannot be empty");
		}
		FireStation fireStationgetted = fireStationDAO.get(address);
		if (fireStationgetted == null) {
			log.error("Service - FireStation not found : address: " + address);
			throw new FireStationNotFoundException("The FireStation not found");
		}
		log.debug("Service - FireStation found :address: " + fireStationgetted.getAddress() + ", Station: "
				+ fireStationgetted.getStation());
		return fireStationgetted;
	}

	/**
	 * Method that add a fireStation
	 * 
	 * @param fireStation - An instance of {@link FireStation}
	 * @return the fireStation added
	 * @throws FireStationAlreadyExistException when the fireStation that we want
	 *                                          added already exist
	 */
	@Override
	public FireStation addFireStation(FireStation fireStation) {
		List<FireStation> listFireStations = getListFireStations();
		int indexPosition = listFireStations.indexOf(fireStation);
		//fireStation already exist
		if (indexPosition >= 0) {
			log.error("Service - FireStation cannot be saved :address: " + fireStation.getAddress() + ", Station: "
					+ fireStation.getStation() + " already exist");
			throw new FireStationAlreadyExistException("The FireStation that we try to save already Exist");
		}
		log.debug("Service - FireStation saved :address: " + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStationDAO.save(fireStation, indexPosition);
	}

	/**
	 * Method that delete a fireStation
	 * 
	 * @param address - A String that containing the address of fireStation
	 * @return a String to confirm or deny the deletion
	 */
	@Override
	public String deleteFireStation(String address) {
		FireStation fireStationToDelete = fireStationDAO.get(address);
		if (fireStationToDelete == null) {
			log.info("Service - FireStation cannot be deleted");
			return "FireStation cannot be deleted";
		}
		log.debug("Service - FireStation : address:" + fireStationToDelete.getAddress() + " ,Station: "
				+ fireStationToDelete.getStation() + " was deleted");
		return fireStationDAO.delete(fireStationToDelete);
	}

	/**
	 * Method that update a fireStation
	 * 
	 * @param fireStation - an instance of {@link FireStation}
	 * @return the fireStation updated
	 * @throws FireStationNotFoundException when the fireStation that we want update
	 *                                      not exist
	 */
	@Override
	public FireStation updateFireStation(FireStation fireStation) {
		List<FireStation> listFireStations = getListFireStations();
		FireStation fireStationInArray = fireStationDAO.get(fireStation.getAddress());
		if (fireStationInArray == null) {
			log.error("Service - FireStation not found: address: " + fireStation.getAddress() + ", Station: "
					+ fireStation.getStation() + " cannot be updated");
			throw new FireStationNotFoundException("FireStation not found");
		}
		int indexPosition = listFireStations.indexOf(fireStationInArray);
		fireStationInArray.setStation(fireStation.getStation());
		FireStation fireStationUpdated = fireStationDAO.save(fireStationInArray, indexPosition);
		log.debug("Service - The station number was updated: address: " + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStationUpdated;
	}
}
