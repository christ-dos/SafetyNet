package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;

/**
 * Class that test FireStationController
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(FireStationController.class)
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

	@Test
	public void testGetFireStation_whenFireStationExist_thenReturnStatusOk() throws Exception {
		// GIVEN
		FireStation fireStationTest = new FireStation("3", "1509 Culver St");

		when(fireStationServiceMock.getFireStation(anyString())).thenReturn(fireStationTest);
		// when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationTest);
		// WHEN

		// THEN
		mockMvcFireStation.perform(get("/firestation?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("1509 Culver St"))).andExpect(jsonPath("$.station", is("3")))
				.andDo(print());
	}

	@Test
	public void testGetFireStation_whenFireStationNotExist_thenReturnFireStationNotFoundException() throws Exception {
		// GIVEN
		when(fireStationServiceMock.getFireStation(anyString()))
				.thenThrow(new FireStationNotFoundException("The FireStation not found"));
		// WHEN

		// THEN
		mockMvcFireStation.perform(get("/firestation?address=15 Flower St")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(
						result -> assertEquals("The FireStation not found", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	@Test
	public void testGetFireStation_whenFieldAddressIsEmpty_shouldReturnAnEmptyFieldsException() throws Exception {
		// GIVEN
		when(fireStationServiceMock.getFireStation(anyString()))
				.thenThrow(new EmptyFieldsException("Field can not be empty"));
		// WHEN

		// THEN
		mockMvcFireStation.perform(get("/firestation?address=")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field can not be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field can not be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	@Test
	public void testSaveFireStation_whenFireStationToSaveExist_thenReturnFireStationAlreadyExistException()
			throws Exception {
		// GIVEN
		FireStation fireStationToAddThatExist = new FireStation("3", "1509 Culver St");
		when(fireStationServiceMock.addFireStation(any()))
				.thenThrow(new FireStationAlreadyExistException("The FireStation that we try to save already Exist"));
		// WHEN

		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString(fireStationToAddThatExist))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof FireStationAlreadyExistException))
				.andExpect(result -> assertEquals("The FireStation that we try to save already Exist",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	@Test
	public void testSaveFireStation_whenFireStationToSaveNotExist_thenReturnFireStationSaved() throws Exception {
		// GIVEN
		FireStation fireStationToAddNotExist = new FireStation("3", "15 wall Street");
		when(fireStationServiceMock.addFireStation(any())).thenReturn(fireStationToAddNotExist);
		when(fireStationDAOMock.save(any(), anyInt())).thenReturn(fireStationToAddNotExist);
		// WHEN

		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString(fireStationToAddNotExist))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.address", is("15 wall Street")))
				.andExpect(jsonPath("$.station", is("3"))).andDo(print());
	}

	@Test
	public void testUpdateFireStation_whenFireStationToUpdateNotExist_thenReturnFireStationNotFoundException()
			throws Exception {
		// GIVEN
		FireStation fireStationToAddNotExist = new FireStation("3", "15 Baltimore St");

		when(fireStationServiceMock.getFireStation(anyString())).thenReturn(null);
		when(fireStationServiceMock.updateFireStation(fireStationToAddNotExist))
				.thenThrow(new FireStationNotFoundException("The FireStation not found"));
		// WHEN

		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.put("/firestation").content(asJsonString(fireStationToAddNotExist))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", is("The FireStation not found")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(
						result -> assertEquals("The FireStation not found", result.getResolvedException().getMessage()))
				.andDo(print());
	}

}
