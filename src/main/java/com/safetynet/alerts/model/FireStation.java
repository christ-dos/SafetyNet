package com.safetynet.alerts.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * A class that models a firestation Object
 * 
 * @author Christine Duarte
 *
 */
@Data
public class FireStation {

	@NotBlank
	@JsonProperty("address")
	private String address;
	
	@NotBlank
	@JsonProperty("station")
	private String station;

	/**
	 * A constructor of the class FireStation without parameter
	 */
	public FireStation() {
		super();
	}

	/**
	 * A constructor of the class FireStation with all parameters
	 */
	public FireStation(@NotBlank String station, @NotBlank String address) {
		super();
		this.station = station;
		this.address = address;
	}

}
