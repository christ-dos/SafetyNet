package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;

import lombok.Builder;

/**
 * Class that test FireStationController
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(FireStationController.class)
@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcFireStation;

	/**
	 * An instance of FireStationDAO
	 * 
	 * @see FireStationDAO
	 */
	@MockBean
	private FireStationDAO fireStationDAOMock;

	/**
	 * An instance of FireStationService
	 * 
	 * @see FireStationService
	 */
	@MockBean
	private FireStationService fireStationServiceMock;

	/**
	 * A mock of a arrayList of FireStations
	 */
	@Mock
	private List<FireStation> mockListFireStation;
	
			
	/**
	 * Method that write an object as JsonString to build the body of the request
	 * mock
	 * 
	 * @param obj - The object that we want send in the request
	 * @return The value as JsonString of the object
	 */
	@Builder
	public String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("The obj does not be writting", e);
		}
	}
	
	/**
	 * Method that test getFireStations then return a list of FireStations
	 * and verify that station:"3", address:"1509 Culver St", station:"2", address:"29 15th St"
	 * are contained in the list
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFireStations__thenReturnListOfFireStation() throws Exception {
		//GIVEN
		mockListFireStation = new ArrayList<>();

		FireStation fireStationIndex0 = new FireStation("3", "1509 Culver St");
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		FireStation fireStationIndex2 = new FireStation("3", "834 Binoc Ave");
		FireStation fireStationIndex3 = new FireStation("3", "1509 Culver St");
		mockListFireStation.add(fireStationIndex0);
		mockListFireStation.add(fireStationIndex1);
		mockListFireStation.add(fireStationIndex2);
		mockListFireStation.add(fireStationIndex3);
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		when(fireStationServiceMock.getListFireStations()).thenReturn(mockListFireStation);
		//WHEN
		//THEN
		mockMvcFireStation.perform(get("/firestations")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].address", is("1509 Culver St"))).andExpect(jsonPath("$.[0].station", is("3")))
		.andDo(print());
	}

	/**
	 * Method that test getFireStation when fireStation exist the status of the
	 * request is OK and address is "1509 Culver St"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFireStation_whenFireStationExist_thenReturnStatusOk() throws Exception {
		// GIVEN
		FireStation fireStationTest = new FireStation("3", "1509 Culver St");

		when(fireStationServiceMock.getFireStation(anyString())).thenReturn(fireStationTest);
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationTest);
		// WHEN
		// THEN
		mockMvcFireStation.perform(get("/firestation/address?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("1509 Culver St"))).andExpect(jsonPath("$.station", is("3")))
				.andDo(print());
	}

	/**
	 * Method that test getFireStation when fireStation not exist then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFireStation_whenFireStationNotExist_thenReturnFireStationNotFoundException() throws Exception {
		// GIVEN
		when(fireStationServiceMock.getFireStation(anyString()))
				.thenThrow(new FireStationNotFoundException("The FireStation not found"));
		// WHEN
		// THEN
		mockMvcFireStation.perform(get("/firestation/address?address=15 Flower St")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(
						result -> assertEquals("The FireStation not found", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test getFireStation when input address is empty then throw a
	 * {@link EmptyFieldsException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetFireStation_whenFieldAddressIsEmpty_shouldReturnAnEmptyFieldsException() throws Exception {
		// GIVEN
		when(fireStationServiceMock.getFireStation(anyString()))
				.thenThrow(new EmptyFieldsException("Field cannot be empty"));
		// WHEN
		// THEN
		mockMvcFireStation.perform(get("/firestation/address?address=")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field cannot be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test saveFireStation when fireStation to save exist then throw
	 * {@link FireStationAlreadyExistException}
	 *
	 * @throws Exception
	 */
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

	/**
	 * Method that test saveFireStation when fireStation to save not exist then the
	 * fireStation is saved and the status isOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testSaveFireStation_whenFireStationToSaveNotExist_thenReturnFireStationSaved() throws Exception {
		// GIVEN
		FireStation fireStationToAddNotExist = new FireStation("3", "15 wall Street");
		when(fireStationServiceMock.addFireStation(any())).thenReturn(fireStationToAddNotExist);
		// WHEN
		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString(fireStationToAddNotExist))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.address", is("15 wall Street")))
				.andExpect(jsonPath("$.station", is("3"))).andDo(print());
	}

	/**
	 * Method that test updateFireStation when fireStation exist and the field
	 * station was modified then return the fireStation updated with the field
	 * station updated
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateFireStation_whenfieldStationWasModifiedToNumberFive_thenReturnFireStationWithFieldStationUpdated()
			throws Exception {
		// GIVEN
		FireStation fireStationToUpdate = new FireStation("5", "748 Townings Dr");
		when(fireStationServiceMock.updateFireStation(any())).thenReturn(fireStationToUpdate);
		// WHEN
		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.put("/firestation").content(asJsonString(fireStationToUpdate))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.address", is("748 Townings Dr")))
				.andExpect(jsonPath("$.station", is("5"))).andDo(print());
	}

	/**
	 * Method that test updateFireStation when fireStaion not exist then throw
	 * {@link FireStationNotFoundException}
	 *
	 * @throws Exception
	 */
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
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("The FireStation not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(
						result -> assertEquals("The FireStation not found", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test updateFireStation when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateFireStation_whenInputFieldAddressIsInvalid_shouldReturnMethodArgumentNotValidExceptionMustNotBeBlank()
			throws Exception {
		// GIVEN
		FireStation fireStationToUpadetaNotValid = new FireStation("3", "");
		// WHEN
		// THEN
		mockMvcFireStation
				.perform(MockMvcRequestBuilders.put("/firestation").content(asJsonString(fireStationToUpadetaNotValid))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}

	/**
	 * Method that test deleteFireStation when fireStation exist then return a
	 * String "SUCCESS" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteFireStationWithAddress_whenFireStationExist_thenReturnStringSUCCESS() throws Exception {
		// GIVEN
		when(fireStationServiceMock.deleteFireStation(anyString())).thenReturn("SUCCESS");
		// WHEN
		// THEN
		mockMvcFireStation.perform(delete("/firestation?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("SUCCESS"))).andDo(print());
	}

	/**
	 * Method that test deleteFireStation when fireStation not exist then return a
	 * String "FireStation cannot be deleted" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteFireStationWithAddress_whenFireStationNotExist_thenReturnStringFireStationCannotBeDeleted()
			throws Exception {
		// GIVEN
		when(fireStationServiceMock.deleteFireStation(anyString())).thenReturn("FireStation cannot be deleted");
		// WHEN
		// THEN
		mockMvcFireStation.perform(delete("/firestation?address=15 New York St")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("FireStation cannot be deleted"))).andDo(print());
	}

}
