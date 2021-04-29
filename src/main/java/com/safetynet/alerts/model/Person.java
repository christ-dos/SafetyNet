package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * A class model 
 * @author Christine Duarte
 *
 */
@Data
public class Person {
	
	@JsonProperty("firstName")
	String firstName;
	
	@JsonProperty("lastName")
	String lastName;
	
	@JsonProperty("address")
	String address;
	
	@JsonProperty("city")
	String city;
	
	@JsonProperty("zip")
	String zip;
	
	@JsonProperty("phone")
	String phone;
	
	@JsonProperty("email")
	String email;
	

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
	
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", LastName=" + lastName + ", adress=" + address + ", city=" + city
				+ ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
	}

	

	



	

	
}
