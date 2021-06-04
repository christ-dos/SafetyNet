package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Class that models the result for 
 * the endpoint /childAlert?address=<address>
 * @author Christine Duarte
 *
 */
@AllArgsConstructor
public class ChildAlertDTO {
	/**
	 * An list of {@link PersonChildAlertDTO} containing childs living in address
	 */
	private List<PersonChildAlertDTO> listChild;
	
	/**
	 * An list of {@link PersonChildAlertDTO} containing other persons living in address
	 */
	private List<PersonChildAlertDTO> listOtherPersonInHouse;
	
	/**
	 * getter for listChild
	 * @return A list containing childs living at an address
	 */
	public List<PersonChildAlertDTO> getListChild() {
		return listChild;
	}
	
	/**
	 * getter for listOtherPersonInHouse
	 * @return A list containing other person living in the same address of childs
	 */
	public List<PersonChildAlertDTO> getListOtherPersonInHouse() {
		return listOtherPersonInHouse;
	}
}
