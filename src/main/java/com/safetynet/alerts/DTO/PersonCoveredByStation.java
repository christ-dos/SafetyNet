package com.safetynet.alerts.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Class that models {@link PersonCoveredByStation}, an instance of PersonPartial contain
 * only firstName, lastName, address, phone of the person
 * 
 * @author Christine Duarte
 *
 */
@Data
@AllArgsConstructor
public class PersonCoveredByStation {
	
	/**
	 * A String that contain the firstName of the person
	 */
	private String firstName;

	/**
	 * A String that contain the lastName of the person
	 */
	private String lastName;

	/**
	 * A String that contain the address of the person
	 */
	private String address;

	/**
	 * A String that contain the phone of the person
	 */
	private String phone;
}
