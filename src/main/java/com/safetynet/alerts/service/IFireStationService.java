package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;

/**
 * An interface that manage methods CRUD of the service
 * 
 * @author Christine Duarte
 *
 */
public interface IFireStationService {

	public FireStation getFireStation();
	
	public FireStation addFireStation();
	
	public FireStation deleteFireStation();
	
	public FireStation updateFireStation();
	
	
}
