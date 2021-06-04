package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDTO {
	
	/**
	 * A String that contain the firstName of the person
	 */
	private String firstName;

	/**
	 * 
	 * A String that contain the lastName of the person
	 */
	private String lastName;

	/**
	 * 
	 * A String that contain the address of the person
	 */
	private String address;

	/**
	 * 
	 * A String that contain the phone of the person
	 */
	private String phone;
}
