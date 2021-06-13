package com.safetynet.alerts.DTO;

import java.util.List;

import lombok.Builder;

/**
 * Class that models a {@link PersonFlood} displaying when a flood occurs
 * 
 * 
 * @author Christine Duarte
 *
 */

@Builder
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
	 * A String that contain the phone of the person
	 */
	private String phone;

	/**
	 * A Integer that contain the age of the person
	 */
	private Integer age;

	/**
	 * getter for firstName
	 * 
	 * @return A String containing firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getter for lastName
	 * 
	 * @return A String containing lastName
	 */
	public String getLastName() {
		return lastName;
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
	 * getter for allergies
	 * 
	 * @return A list of String containing allergies
	 */
	public List<String> getAllergies() {
		return allergies;
	}

	/**
	 * getter for phone
	 * 
	 * @return A String containing phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * getter for age
	 * 
	 * @return An Integer containing age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * method equals
	 * 
	 * @return true if equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonFlood other = (PersonFlood) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (allergies == null) {
			if (other.allergies != null)
				return false;
		} else if (!allergies.equals(other.allergies))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (medication == null) {
			if (other.medication != null)
				return false;
		} else if (!medication.equals(other.medication))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
}
