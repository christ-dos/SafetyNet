package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A class that models a person Object
 * 
 * @author Christine Duarte
 *
 */
@Data
public class Person {
	/**
	 * A String that contain the firstName of the person
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("firstName")
	String firstName;

	/**
	 * 
	 * A String that contain the lastName of the person
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("lastName")
	String lastName;

	/**
	 * 
	 * A String that contain the address of the person
	 */
	@NotBlank
	@JsonProperty("address")
	String address;

	/**
	 * 
	 * A String that contain the city of the person
	 */
	@NotBlank
	@JsonProperty("city")
	String city;

	/**
	 * 
	 * A String that contain the zip of the person
	 */
	@NotBlank
	@JsonProperty("zip")
	String zip;

	/**
	 * 
	 * A String that contain the phone of the person
	 */
	@JsonProperty("phone")
	String phone;

	/**
	 * 
	 * A String that contain the email of the person
	 */
	@JsonProperty("email")
	String email;

	/**
	 * 
	 * An arrayList that contain the medicalRecords of the person
	 */
	// ArrayList<MedicalRecords> medicalRecords;


	/**
	 * A constructor of the class Person with all parameters of the class
	 */
	public Person(String firstName, String lastName, String adress, String city, String zip, String phone,
			String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = adress;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
}
