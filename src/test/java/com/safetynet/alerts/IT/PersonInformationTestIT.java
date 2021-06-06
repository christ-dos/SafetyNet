package com.safetynet.alerts.IT;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;

/**
 * Class integration tests of PersonInfoDTO which verify that all classes works
 * correctly together to test the class {@link PersonInformation}
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonInformationTestIT {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvc;
	
	
	/**
	 * Method that test request getListPersonsCoveredByStation when station  number exist the status of the request is OK
	 * and the listPersonDTO in index 0 contain a personDTO firstName and lastName is Peter Duncan, address is "644 Gershwin Cir"
	 * and a adultsCounter with 5 adults and  a childsCounter with 1 child
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenFireStationNumberExistIsOne_thenReturnAListPersonCoveredByFireStationWithPeterDuncan() throws Exception{
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/firestation?station=4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPartialPersons[0].firstName", is("Tony"))).andExpect(jsonPath("$.listPartialPersons[0].lastName", is("Cooper")))
				.andExpect(jsonPath("$.listPartialPersons[0].address", is("112 Steppes Pl"))).andExpect(jsonPath("$.listPartialPersons[0].phone", is("841-874-6874")))
				.andExpect(jsonPath("$.adultsCounter", is(4)))
				.andExpect(jsonPath("$.childsCounter", is(0)))
				.andDo(print());
	}
	
	/**
	 * Method that test getListPersonsCoveredByStation when the Station Number is five and not exist then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenStationNumberIsFive_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/firestation?station=9")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
	
	/**
	 * Method that test request GetPhoneAlertResidentsCoveredByStation when station number exist the status of the request is OK
	 * and the list of phone in index 0 contain "841-874-6512", and in index 10 contain "841-874-6741"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonCoveredByFireStation() throws Exception{
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/phoneAlert?station=4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("841-874-6874")))
				.andExpect(jsonPath("$.[3]", is("841-874-9888")))
				.andDo(print());
	}
	
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when the station number is five then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenStationNumberIsFive_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/phoneAlert?station=0")).andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
		.andExpect(result -> assertEquals("The FireStation number not found",
				result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	/**
	 * Method that test request to GetPersonInfo when person exist fistName John and
	 * lastName Boyd then return informations of person: "John", "Boyd", "1509
	 * Culver St", 37,"jaboyd@email.com" and medical history of person
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonIsJohnBoydAndExist_thenReturnPersonInformationJohnBoyd() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/personinfo?firstName=Kendrik&lastName=Stelzer"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("Kendrik")))
				.andExpect(jsonPath("$.lastName", is("Stelzer"))).andExpect(jsonPath("$.address", is("947 E. Rose Dr")))
				.andExpect(jsonPath("$.medication[0]", is("noxidian:100mg")))
				.andExpect(jsonPath("$.allergies").isEmpty()).andExpect(jsonPath("$.age", is(7))).andDo(print());
	}

	/**
	 * Method that test request to GetPersonInfo when person not exist fistName Lily
	 * and lastName Sacha then throw a PersonNotFoundException
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonNotExist_thenThrowPersonNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/personinfo?firstName=Lily&lastName=Sacha"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
