package com.safetynet.alerts.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class that models a MedicalRecord Object
 * 
 * @author Christine Duarte
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
	/**
	 * A String that contain the firstName contained in the MedicalRecord
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("firstName")
	private String firstName;

	/**
	 * A String that contain the lastName contained in the MedicalRecord
	 */
	@NotBlank
	@Size(max = 20)
	@JsonProperty("lastName")
	private String lastName;

	/**
	 * A String that contain the birthDate contained in the MedicalRecord
	 */
	@NotBlank
	@JsonProperty("birthdate")
	private String birthDate;

	/**
	 * An ArrayList of String of medications contained in the MedicalRecord
	 */
	@JsonProperty("medications")
	private List<String> medications;

	/**
	 * An ArrayList of String of allergies contained in the MedicalRecord
	 */
	@JsonProperty("allergies")
	private List<String> allergies;
}
