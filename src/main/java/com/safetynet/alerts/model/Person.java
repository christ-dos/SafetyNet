package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * A class model 
 * @author Christine Duarte
 *
 */
@Data
public class Person {
	
	@NotBlank
	@Size(max= 20)
	@JsonProperty("firstName")
	String firstName;
	
	@NotBlank
	@Size(max= 20)
	@JsonProperty("lastName")
	String lastName;
	
	@NotBlank
	@JsonProperty("address")
	String address;
	
	@NotBlank
	@JsonProperty("city")
	String city;
	
	@NotBlank
	@JsonProperty("zip")
	String zip;
	
	
	@JsonProperty("phone")
	String phone;
	
	@JsonProperty("email")
	String email;
	
	// MedicalRecors medicalRecords;
	
	
	public Person() {
		
	}

	public Person(String firstName, String lastName, String adress, String city, String zip, String phone,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = adress;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", LastName=" + lastName + ", adress=" + address + ", city=" + city
				+ ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}
	
}
