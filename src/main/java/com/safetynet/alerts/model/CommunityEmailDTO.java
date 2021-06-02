package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * A class which models the results of the endpoint 
 * communityEmail?city=<city>
 * 
 * @author Christine Duarte 
 *
 */
@AllArgsConstructor
public class CommunityEmailDTO {
	
	/**
	 * A list of  string which contain email of person
	 */
	private List<String> listEmail;
	
	/**
	 * getter for the  listEmail
	 * @return A list of email
	 */
	public List<String> getListEmail() {
		return listEmail;
	}
}
