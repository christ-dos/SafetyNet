package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class that models a fireStation Object
 * 
 * @author Christine Duarte
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStation {

	/**
	 * A String that contain the station number of the fireStation
	 */
	@NotBlank
	@JsonProperty("station")
	private String station;
	
	/**
	 * A String that contain the address of the fireStation
	 */
	@NotBlank
	@JsonProperty("address")
	private String address;
}
