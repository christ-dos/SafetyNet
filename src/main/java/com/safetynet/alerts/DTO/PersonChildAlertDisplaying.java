package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Class that models the result for 
 * the Endpoint /childAlert?address=<address>
 * 
 * @author Christine Duarte
 *
 */
@AllArgsConstructor
public class PersonChildAlertDisplaying {
	/**
	 * An list of {@link PersonChildAlert} containing childs living in address
	 */
	private List<PersonChildAlert> listChild;
	
	/**
	 * An list of {@link PersonChildAlert} containing other persons living in address
	 */
	private List<PersonChildAlert> listOtherPersonInHouse;
	
	/**
	 * getter for listChild
	 * @return A list containing childs living at an address
	 */
	public List<PersonChildAlert> getListChild() {
		return listChild;
	}
	
	/**
	 * getter for listOtherPersonInHouse
	 * @return A list containing other person living in the same address of childs
	 */
	public List<PersonChildAlert> getListOtherPersonInHouse() {
		return listOtherPersonInHouse;
	}
}
