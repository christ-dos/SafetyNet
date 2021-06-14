package com.safetynet.alerts.DTO;

import lombok.AllArgsConstructor;

/**
 * A class which models a PersonChildAlertDTO
 * 
 * @author Christine Duarte
 *
 */
@AllArgsConstructor
public class PersonChildAlert {

	/**
	 * A String that contain the firstName of the person
	 */
	private String firstName;

	/**
	 * A String that contain the lastName of the person
	 */
	private String lastName;

	/**
	 * A Integer that contain the age of the person
	 */
	private Integer age;

	/**
	 * getter for firstName
	 * 
	 * @return A String with the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getter for lastName
	 * 
	 * @return A String with the firstNlastNameame
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * getter for age
	 * 
	 * @return An Integer with the age
	 */
	public Integer getAge() {
		return age;
	}
}
