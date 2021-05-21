package com.safetynet.alerts.DAO;

import java.util.List;

import com.safetynet.alerts.model.FireStation;
/**
 * Interface that manage methods CRUD of entity FireStation
 * 
 * @author Christine Duarte
 *
 */
public interface IFireStationDAO {
	
	public List<FireStation> getListFireStations();
	
	public FireStation get(String address);
	
	public FireStation save(FireStation fireStation, int index);
	
	public String delete(FireStation fireStation);

	

	

}
