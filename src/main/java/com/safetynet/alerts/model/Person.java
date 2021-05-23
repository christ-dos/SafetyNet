package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A class that models a Person Object
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
	private String firstName;

	/**
	 * 
	 * A String that contain the lastName of the person
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("lastName")
	private String lastName;

	/**
	 * 
	 * A String that contain the address of the person
	 */
	@NotBlank
	@JsonProperty("address")
	private String address;

	/**
	 * 
	 * A String that contain the city of the person
	 */
	@NotBlank
	@JsonProperty("city")
	private String city;

	/**
	 * 
	 * A String that contain the zip of the person
	 */
	@NotBlank
	@JsonProperty("zip")
	private String zip;

	/**
	 * 
	 * A String that contain the phone of the person
	 */
	@JsonProperty("phone")
	private String phone;

	/**
	 * 
	 * A String that contain the email of the person
	 */
	@JsonProperty("email")
	private String email;

	/**
	 * 
	 * An instance of MedicalRecord that contain the medicalRecord of the person
	 */
	//private MedicalRecord medicalRecord;

	
	/**
	 * A constructor of the class Person without parameter of the class
	 */
	public Person() {
		super();
	}
	
	/**
	 * A constructor of the class Person with all parameters of the class
	 */
	public Person(@NotBlank @Size(max = 20) String firstName, @NotBlank @Size(max = 20) String lastName,
			@NotBlank String address, @NotBlank String city, @NotBlank String zip, String phone, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
}
