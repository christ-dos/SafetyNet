package com.safetynet.alerts.controller;

import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;

/**
 * Class that test FireStationController
 * 
 * @author Christine Duarte
 *
 */
//@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {
	
	@Autowired
	private MockMvc mockMvcFireStation;
	
	@MockBean
	private FireStationDAO fireStationDAOMock;
	
	@MockBean
	private FireStationService fireStationServiceMock;
	
	@Mock
	private List<FireStation> mockListFireStation;
	
	/**
	 * Method that write an object as JsonString to build the body of the request
	 * mock
	 * 
	 * @param obj - The object that we want send in the request
	 * @return The value as JsonString of the object
	 */
	public String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("The obj does not be writting", e);
		}
	}
	
	/**@Test
	public void testGetFireStation_whenFireStationExist_thenReturnStatusOk() throws Exception {
		// GIVEN
		FireStation fireStationTest = new FireStation("3", "1509 Culver St");
		//when(fireStationServiceMock
		//when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personTest);
		// WHEN

		// THEN
		mockMvcFireStation.perform(get("/firesation?address=1509 Culver St"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("1509 Culver St"))).andExpect(jsonPath("$.station", is("3")))
				.andDo(print());
	}*/

}
