package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * A class which models the results of the endpoint 
 * phoneAlert?firestation=<firestation_number>
 * 
 * @author Christine Duarte 
 *
 */
@AllArgsConstructor
public class PhoneAlertDTO {
	
	/**
	 * A list of String  which contain the phones of persons covered by a station number
	 */
	private List<String> listPhoneAlert;
	
	/**
	 * getter to obtain the list of phone
	 * @return
	 */
	public List<String> getListPhoneAlert() {
		return listPhoneAlert;
	}
}
