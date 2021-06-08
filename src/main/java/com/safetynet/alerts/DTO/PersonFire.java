package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class that models a {@link PersonFire} displaying when a fire occurs
 * 
 * @author Christine Duarte
 *
 */
@Data
@AllArgsConstructor
public class PersonFire {
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
	 * A String that contain the phone of the person
	 */
	private String phone;

	/**
	 * A Integer that contain the age of the person
	 */
	private Integer age;

	/**
	 * An ArrayList of String with medication of person
	 */
	private List<String> medication;

	/**
	 * An ArrayList of String with allergies of person
	 */
	private List<String> allergies;
}
