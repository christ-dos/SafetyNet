package com.safetynet.alerts.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A class that models a MedicalRecord Object
 * 
 * @author Christine Duarte
 *
 */
@Data
public class MedicalRecord {
	/**
	 * A String that contain the firstName contained in the MedicalRecord
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("firstName")
	private String firstName;

	/**
	 * 
	 * A String that contain the lastName contained in the MedicalRecord
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("lastName")
	private String lastName;

	/**
	 * 
	 * A String that contain the birthDate contained in the MedicalRecord
	 */
	@NotBlank
	@JsonProperty("birthdate")
	private String birthDate;

	/**
	 * 
	 * An ArrayList of String of medications contained in the MedicalRecord
	 */
	@JsonProperty("medications")
	private List<String> medications;

	/**
	 * 
	 * An ArrayList of String of allergies contained in the MedicalRecord
	 */
	@JsonProperty("allergies")
	private List<String> allergies;

	/**
	 * A constructor of the class MedicalRecord with all parameters of the class
	 */
	public MedicalRecord(@NotBlank @Size(max = 20) String firstName, @NotBlank @Size(max = 20) String lastName,
			@NotBlank String birthDate, List<String> medications, List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.medications = medications;
		this.allergies = allergies;
	}

	/**
	 * A constructor of the class Person without parameter of the class
	 */
	public MedicalRecord() {
		super();
	}
}
