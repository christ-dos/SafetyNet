package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.FireStation;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the CRUD methods and implement {@link IFireStationDAO}
 * interface
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Builder
public class FireStationDAO implements IFireStationDAO {
	/**
	 * attribute that contain the list of fireStations that provide from data.json
	 */
	@Autowired
	private List<FireStation> listFireStations;

	/**
	 * Constructor with the parameter listFireStations
	 * 
	 * @param listFireStations - an ArrayList with the list of fireStations
	 */
	public FireStationDAO(List<FireStation> listFireStations) {
		super();
		this.listFireStations = listFireStations;
	}

	/**
	 * Method that get the list of fireStations
	 * 
	 * @return an ArrayList of FireStation
	 */
	@Override
	public List<FireStation> getFireStations() {
		return listFireStations;
	}

	/**
	 * Method that get a fireStation by address
	 * 
	 * @param address - String with the address
	 * @return an instance of FireStation or null if fireStation not exist
	 */
	@Override
	public FireStation get(String address) {
		for (FireStation fireStation : listFireStations) {
			if (fireStation.getAddress().equalsIgnoreCase(address)) {
				log.debug("DAO - FireStation found: address:" + fireStation.getAddress() + " ,Station :"
						+ fireStation.getStation());
				return fireStation;
			}
		}
		log.error("DAO FireStation not found with address: " + address);
		return null;
	}

	/**
	 * Method that save a FireStation in the ArrayList
	 * 
	 * @param index - An integer containing the position where will be saved the
	 *              fireStation
	 * @return - The FireStation that was saved in the arrayList
	 */
	@Override
	public FireStation save(FireStation fireStation, int index) {
		if (index < 0) {
			listFireStations.add(fireStation);
		} else {
			listFireStations.set(index, fireStation);
		}
		log.debug("DAO - FireStation saved: address:" + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStation;
	}

	/**
	 * Method that delete a FireStation from the ArrayList
	 * 
	 * @param fireStation - The fireStation which will be deleted
	 * @return A String containing "SUCCESS" to confirm the deletion
	 */
	@Override
	public String delete(FireStation fireStation) {
		listFireStations.remove(fireStation);
		log.info("DAO - FireStation deleted with SUCCESS");
		return "SUCCESS";
	}
}
