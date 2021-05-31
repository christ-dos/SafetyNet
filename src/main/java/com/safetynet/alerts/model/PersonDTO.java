package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
	
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
	@JsonInclude(Include.NON_NULL)
	private String address;

	/**
	 * 
	 * A String that contain the phone of the person
	 */
	@JsonProperty("phone")
	@JsonInclude(Include.NON_NULL)
	private String phone;


}
