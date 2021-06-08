package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class that models a {@link PersonFlood} displaying when a flood occurs
 * 
 * 
 * @author Christine Duarte
 *
 */
@Data
@AllArgsConstructor
public class PersonFlood {
	

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
	 * An ArrayList of String with medication of person
	 */
	private List<String> medication;

	/**
	 * An ArrayList of String with allergies of person
	 */
	private List<String> allergies;
	
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
	
	/**
	 * A Integer that contain the age of the person
	 */
	private Integer age;
}
