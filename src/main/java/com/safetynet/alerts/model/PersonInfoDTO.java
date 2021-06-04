package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A class which models the results of the endpoint 
 * personInfo?firstName=<firstName>&lastName=<lastName>
 * 
 * @author Christine Duarte 
 *
 */
@AllArgsConstructor
@Data
public class PersonInfoDTO {
	
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
	 * A Integer that contain the age of the person
	 */
	private Integer age;
	
	/**
	 * 
	 * A String that contain the email of the person
	 */
	private String email;
	
	/**
	 * An ArrayList of String with medications of person
	 */
	private List<String> medication;

	/**
	 * An ArrayList of String with allergies of person
	 */
	private List<String> allergies;
}
