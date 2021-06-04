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

import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.PersonInfoDTO;

/**
 * Class integration tests of PersonInfoDTO which verify that all classes works
 * correctly together to test the class {@link PersonInfoDTO}
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonInfoDTOTestIT {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcByStationNumberDTO;

	/**
	 * Method that test request to GetPersonInfo when person exist fistName John and
	 * lastName Boyd then return informations of person: "John", "Boyd", "1509
	 * Culver St", 37,"jaboyd@email.com" and medical history of person
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonIsJohnBoydAndExist_thenReturnPersonInfoDTOJohnBoyd() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/personinfo?firstName=Kendrik&lastName=Stelzer"))
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
		mockMvcByStationNumberDTO.perform(get("/personinfo?firstName=Lily&lastName=Sacha"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());

	}
}
