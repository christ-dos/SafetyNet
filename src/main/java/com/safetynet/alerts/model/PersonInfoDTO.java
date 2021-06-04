package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * A class which models the results of the endpoint 
 * personInfo?firstName=<firstName>&lastName=<lastName>
 * 
 * @author Christine Duarte 
 *
 */
@AllArgsConstructor
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
	
	/**
	 * getter for firstName
	 * @return A String with the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * getter for lastName
	 * @return A String with the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * getter for address
	 * @return A String with the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * getter for age
	 * @return An Integer with the age 
	 */
	public Integer getAge() {
		return age;
	}
	
	/**
	 * getter for email
	 * @return A String with the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * getter for medication
	 * @return An arrayList containing the medication
	 */
	public List<String> getMedication() {
		return medication;
	}
	
	/**
	 * getter for allergies
	 * @return An arrayList containing the allergies
	 */
	public List<String> getAllergies() {
		return allergies;
	}
}
