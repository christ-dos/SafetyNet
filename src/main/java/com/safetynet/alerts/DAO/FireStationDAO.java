package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.FireStation;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FireStationDAO implements IFireStationDAO {
	
	@Autowired
	private List<FireStation> listFireStations;
	
	
	public FireStationDAO() {
		super();
	}

	public FireStationDAO(List<FireStation> listFireStations) {
		super();
		this.listFireStations = listFireStations;
	}
	
	
	@Override
	public List<FireStation> getListFireStations() {
		return listFireStations;
	}
	
	@Override
	public FireStation get(String address) {
		for(FireStation fireStation : listFireStations) {
			if(fireStation.getAddress().equalsIgnoreCase(address)){
				log.info("DAO - FireStation found :address:" + fireStation.getAddress() + " ,Station :" + fireStation.getStation());
				return fireStation;
			}
		}
		log.error("DAO FireStation not found with address: " + address);
		return null;
	}

	@Override
	public FireStation save(FireStation fireStation, int index) {
		if(index < 0) {
			listFireStations.add(fireStation);
		}else {
			listFireStations.set(index, fireStation);
		}
		log.info("DAO - FireStation saved :address:" + fireStation.getAddress() +  ", Station: " + fireStation.getStation());
		return fireStation;
	}

	@Override
	public String delete(FireStation fireStation) {
		
		listFireStations.remove(fireStation);
		log.info("DAO - FireStation deleted with SUCCESS");
		return "SUCCESS";
	}

	
}
