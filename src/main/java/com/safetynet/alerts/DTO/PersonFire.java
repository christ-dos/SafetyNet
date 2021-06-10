package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Class that models a {@link PersonFire} displaying when a fire occurs
 * 
 * @author Christine Duarte
 *
 */
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

	/**
	 * getter for firstName
	 * 
	 * @return A String containing the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * setter for firstName
	 * 
	 * @param firstName - A String containing the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * getter for lastName
	 * 
	 * @return A String containing the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * setter for lastName
	 * 
	 * @param lastName - A String containing the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * getter for phone
	 * 
	 * @return A String containing the phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * setter for phone
	 * 
	 * @param phone - A String containing the phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * getter for age
	 * 
	 * @return An Integer containing the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * setter for age
	 * 
	 * @param age - An Integer containing the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * getter for medication
	 * 
	 * @return A list of String containing medication
	 */
	public List<String> getMedication() {
		return medication;
	}

	/**
	 * setter for medication
	 * 
	 * @param medication - A list of String containing medication to set
	 */
	public void setMedication(List<String> medication) {
		this.medication = medication;
	}

	/**
	 * getter for allergies
	 * 
	 * @return A list of String that containing allergies
	 */
	public List<String> getAllergies() {
		return allergies;
	}

	/**
	 * setter for allergies
	 * 
	 * @param allergies - A list of String containing allergies to set
	 */
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
}
