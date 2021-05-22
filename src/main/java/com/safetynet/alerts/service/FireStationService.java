package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
public class FireStationService implements IFireStationService {

	@Autowired
	private IFireStationDAO fireStationDAO;

	public FireStationService() {
		super();
	}

	public FireStationService(IFireStationDAO fireStationDAO) {
		super();
		this.fireStationDAO = fireStationDAO;
	}

	private List<FireStation> getListFireStations() {
		return fireStationDAO.getListFireStations();

	}

	@Override
	public FireStation getFireStation(String address) throws EmptyFieldsException {
		//String address = fireStation.getAddress();
		if (address.isEmpty()) {
			log.error("Service - field can not be empty");
			throw new EmptyFieldsException("Field can not be empty");
		}
		FireStation fireStationgetted = fireStationDAO.get(address);
		if (fireStationgetted == null) {
			log.error("Service - FireStation not found : address: " + address );
			throw new FireStationNotFoundException("The FireStation not found");
		}
		log.debug("Service - FireStation found :address: " + fireStationgetted.getAddress() + ", Station: "
				+ fireStationgetted.getStation());
		return fireStationgetted;
	}

	@Override
	public FireStation addFireStation(FireStation fireStation) {
		List<FireStation> listFireStations = getListFireStations();
		int indexPosition = listFireStations.indexOf(fireStation);
		if (indexPosition >= 0) {
			log.error("Service - FireStation cannot be saved :address: " + fireStation.getAddress() + ", Station: "
					+ fireStation.getStation() + " already exist");
			throw new FireStationAlreadyExistException("The FireStation that we try to save already Exist");
		}
		log.debug("Service - FireStation saved :address: " + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStationDAO.save(fireStation, indexPosition);
	}

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
