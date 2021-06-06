package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

/**
 * Class that test the MedicalRecordController
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(MedicalRecordController.class)
@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcMedicalRecord;
	
	/**
	 * An instance of MedicalRecordService
	 * 
	 * @see MedicalRecordService
	 */
	@MockBean
	private MedicalRecordService medicalRecordServiceMock;
	
	/**
	 * An instance of MedicalRecordDAO
	 * 
	 * @see MedicalRecordDAO
	 */
	@MockBean
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * A mock of an arrayList of medicalRecords
	 */
	@Mock
	private List<MedicalRecord> mockListMedicalRecord;
	
	
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
	/**
	 * Method that test getMedicalRecord when medicalRecord exist the status of the request is OK
	 * and the medicalRecord of the person firstName and lastName is John Boyd and birthDate is "03/06/1984"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMedicalRecord_whenMedicalRecordExist_thenReturnStatusOk() throws Exception{
		// GIVEN
		MedicalRecord medicalRecordTest = new MedicalRecord("John", "Boyd", "03/06/1984",
				   new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				   new ArrayList<>(Arrays.asList("nillacilan")));
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(medicalRecordTest);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordTest);
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=John&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.birthdate", is("03/06/1984"))).andExpect(jsonPath("$.medications[0]", is("aznol:350mg")));
	}
	
	/**
	 * Method that test getMedicalRecord when medicalRecord not exist then throw
	 * {@link MedicalRecordNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMedicalRecord_whenMedicalRecordNotexist_thenReturnMedicalRecordNotFoundException() throws Exception {
		// GIVEN
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString()))
				.thenThrow(new MedicalRecordNotFoundException("MedicalRecord not found exception"));
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=Lilly&lastName=Saguet")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicalRecordNotFoundException))
				.andExpect(result -> assertEquals("MedicalRecord not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test getMedicalRecord when input firstName or lastName is empty then
	 * throw a {@link EmptyFieldsException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetMedicalRecord_whenInputFirstNameOrLastNameIsEmpty_shouldReturnAnEmptyFieldsException() throws Exception
		{
		// GIVEN
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString()))
				.thenThrow(new EmptyFieldsException("Field cannot be empty"));
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=&lastName=Boyd")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field cannot be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}
	
	/**
	 * Method that test saveMedicalRecord when medicalRecord to save exist then throw
	 * {@link MedicalRecordAlreadyExistException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testSaveMedicalRecord_whenMedicalRecordToSaveExist_thenReturnMedicalRecordAlreadyExistException() throws Exception {
		// GIVEN
		MedicalRecord medicalRecordToAddExist = new MedicalRecord("Tenley", "Boyd", "02/08/2012", 
				   new ArrayList<>(Arrays.asList()),
				   new ArrayList<>(Arrays.asList("peanut")));
		when(medicalRecordServiceMock.addMedicalRecord(any())).thenThrow(new MedicalRecordAlreadyExistException("MedicalRecord already exist"));
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(MockMvcRequestBuilders.post("/medicalRecord").content(asJsonString(medicalRecordToAddExist))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicalRecordAlreadyExistException))
				.andExpect(result -> assertEquals("MedicalRecord already exist", result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test saveMedicalRecord when medicalRecord to save not exist then the medicalRecord is
	 * saved and the status isOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testSaveMedicalRecord_whenMedicalRecordToSaveNotExist_thenMedicalRecordIsSaved() throws Exception {
		// GIVEN
		MedicalRecord medicalRecordToAddNotExist = new MedicalRecord("Jojo", "Dupond", "02/08/2020", 
				   new ArrayList<>(Arrays.asList()),
				   new ArrayList<>(Arrays.asList("strawberry")));
		when(medicalRecordServiceMock.addMedicalRecord(any())).thenReturn(medicalRecordToAddNotExist);
		when(medicalRecordDAOMock.save(anyInt(), any())).thenReturn(medicalRecordToAddNotExist);
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(MockMvcRequestBuilders.post("/medicalRecord").content(asJsonString(medicalRecordToAddNotExist))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jojo"))).andExpect(jsonPath("$.lastName", is("Dupond")))
				.andExpect(jsonPath("$.birthdate", is("02/08/2020"))).andExpect(jsonPath("$.allergies[0]", is("strawberry")))
				.andDo(print());
	}
	/**
	 * Method that test deleteMedicalRecord when medicalRecord exist then return a String
	 * "SUCCESS" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteMedicalRecord_whenInputFirstNameJohnAndLastNameBoydMedicalRecordExist_shouldReturnAStringWithSUCCESS()
			throws Exception {
		// GIVEN
		when(medicalRecordDAOMock.delete(any())).thenReturn("SUCESS");
		when(medicalRecordServiceMock.deleteMedicalRecord(any(), any())).thenReturn("SUCCESS");
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(delete("/medicalRecord?firstName=john&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("SUCCESS"))).andDo(print());
	}
	
	/**
	 * Method that test deleteMedicalRecord when medicalRecord not exist then return a String
	 * "MedicalRecord cannot be deleted" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeleteMedicalRecord_whenMedicalRecordNotExist_shouldReturnAStringWithMedicalRecordCannotBeDeleted() throws Exception {
		// GIVEN
		when(medicalRecordServiceMock.deleteMedicalRecord(any(), any())).thenReturn("MedicalRecord cannot be deleted");
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(delete("/medicalRecord?firstName=jo&lastName=Lapin")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("MedicalRecord cannot be deleted"))).andDo(print());
	}
	/**
	 * Method that test updateMedicalRecord when medicalRecord exist and the field medications and allergies were
	 * modified then return the MedicalRecord updated with the fields medications and allergies updated
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateMedicalRecord_whenfieldMedicationsWasModified_thenReturnMedicalRecordWithFieldMedicationsUpdated() throws Exception {
		// GIVEN
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("John", "Boyd", "03/06/1984",
				   new ArrayList<>(Arrays.asList("ibupurin:200mg", "tradoxidine:400mg")),
				   new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord medicalRecordRecordedInArray  = new MedicalRecord("John", "Boyd", "03/06/1984",
				   new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				   new ArrayList<>(Arrays.asList("nillacilan")));

		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(medicalRecordRecordedInArray);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordRecordedInArray);
		when(medicalRecordServiceMock.updateMedicalRecord(any())).thenReturn(medicalRecordToUpdate);
		when(medicalRecordDAOMock.save(anyInt(), any())).thenReturn(medicalRecordToUpdate);
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(MockMvcRequestBuilders.put("/medicalRecord").content(asJsonString(medicalRecordToUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.birthdate", is("03/06/1984"))).andExpect(jsonPath("$.medications[0]", is("ibupurin:200mg"))).andExpect(jsonPath("$.medications[1]", is("tradoxidine:400mg")))
				.andExpect(jsonPath("$.allergies[0]", is("peanut")))
				.andDo(print());
	}
	/**
	 * Method that test updateMedicalRecord when medicalRecord not exist then throw
	 * {@link MedicalRecordNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateMedicalRecord_whenMedicalRecordToUpdateNotExist_thenReturnMedicalRecordNotFoundException() throws Exception {
		// GIVEN
		MedicalRecord medicalRecordToUpdateButNBotExist = new MedicalRecord("Babar", "Elephant", "03/06/1950",
				   new ArrayList<>(),
				   new ArrayList<>(Arrays.asList("banana")));
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(null);
		when(medicalRecordServiceMock.updateMedicalRecord(medicalRecordToUpdateButNBotExist))
				.thenThrow(new MedicalRecordNotFoundException("The MedicalRecord that we want update for the person: "
						+ medicalRecordToUpdateButNBotExist.getFirstName() + " " + medicalRecordToUpdateButNBotExist.getLastName() + " not exist"));
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(MockMvcRequestBuilders.put("/medicalRecord").content(asJsonString(medicalRecordToUpdateButNBotExist))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", is("MedicalRecord not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MedicalRecordNotFoundException))
				.andExpect(
						result -> assertEquals(
								"The MedicalRecord that we want update for the person: "
										+ medicalRecordToUpdateButNBotExist.getFirstName() + " " + medicalRecordToUpdateButNBotExist.getLastName() + " not exist",
								result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test updateMedicalRecord when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateMedicalRecord_whenInputFieldsIsInvalid_shouldReturnMethodArgumentNotValidExceptionMustNotBeBlank()
			throws Exception {
		// GIVEN
		MedicalRecord medicalRecordToUpdateButInvalidField  = new MedicalRecord("", "Boyd", "03/06/1984",
				   new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				   new ArrayList<>(Arrays.asList("nillacilan")));
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(MockMvcRequestBuilders.put("/medicalRecord").content(asJsonString(medicalRecordToUpdateButInvalidField))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}
}
