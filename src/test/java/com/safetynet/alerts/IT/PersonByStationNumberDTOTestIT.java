package com.safetynet.alerts.IT;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

/**
 * Class integration tests of PersonByStationNumberDTO which verify that all classes works correctly together
 * to test the class {@link PersonByStationNumberDTO}
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonByStationNumberDTOTestIT {
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcByStationNumberDTO;
	
	/**
	 * Method that test request getListPersonsCoveredByStation when station  number exist the status of the request is OK
	 * and the listPersonDTO in index 0 contain a personDTO firstName and lastName is Peter Duncan, address is "644 Gershwin Cir"
	 * and a adultsCounter with 5 adults and  a childsCounter with 1 child
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenFireStationNumberExistIsOne_thenReturnAListPersonDTOCoveredByFireStationWithPeterDuncan() throws Exception{
		// GIVEN
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/firestation?station=1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPersonDTO[0].firstName", is("Peter"))).andExpect(jsonPath("$.listPersonDTO[0].lastName", is("Duncan")))
				.andExpect(jsonPath("$.listPersonDTO[0].address", is("644 Gershwin Cir"))).andExpect(jsonPath("$.listPersonDTO[0].phone", is("841-874-6512")))
				.andExpect(jsonPath("$.adultsCounter", is(5)))
				.andExpect(jsonPath("$.childsCounter", is(1)))
				.andDo(print());
	}
	
	/**
	 * Method that test getListPersonsCoveredByStation when the Station Number is five and not exist then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testgetAddressCoveredByFireStation_whenStationNumberIsFive_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/firestation?station=5")).andExpect(status().isNotFound())
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
	public void testGetPhoneAlertResidentsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonDTOCoveredByFireStation() throws Exception{
		// GIVEN
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/phoneAlert?station=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPhoneAlert[0]", is("841-874-6512")))
				.andExpect(jsonPath("$.listPhoneAlert[10]", is("841-874-6741")))
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
		mockMvcByStationNumberDTO.perform(get("/phoneAlert?station=5")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
