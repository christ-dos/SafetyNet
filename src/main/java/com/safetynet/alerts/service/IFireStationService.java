package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.FireStation;

/**
 * An interface that manage methods CRUD of the service
 * 
 * @author Christine Duarte
 *
 */
public interface IFireStationService {

	public FireStation getFireStation(String address) throws EmptyFieldsException;
	
	public FireStation addFireStation(FireStation fireStation);
	
	public String deleteFireStation(String address);
	
	public FireStation updateFireStation(FireStation fireStation);

	

	

	

	

	

	


	

	
	
	
}
