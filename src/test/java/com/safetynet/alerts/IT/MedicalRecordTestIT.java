package com.safetynet.alerts.IT;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.safetynet.alerts.controller.MedicalRecordControllerTest;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;

/**
 * Class integration test for {@link MedicalRecord} entity which verify that all
 * classes works correctly together
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordTestIT {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcMedicalRecord;

	/**
	 * Method that test request Get when MedicalRecord exist then the status of the
	 * request isOk and the firstName and lastName recorded in medicalRecord is
	 * Roger Boyd
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetMedicalRecordExist_whenInputMedicalRecordWithFirstNameRogerAndLastNameBoyd_shouldReturnStatusOK()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=Roger&lastName=Boyd"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.firstName", is("Roger"))).andExpect(jsonPath("$.lastName", is("Boyd")))
							.andExpect(jsonPath("$.birthdate", is("09/06/2017"))).andDo(print());
	}

	/**
	 * Method that test request Get when medicalRecord not exist then throw a
	 * {@link MedicalRecordNotFoundException} and the status of the request
	 * isNotFound
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetMedicalRecordNotExist_whenMedicalRecordFirstNameLilyAndLastNameSaguet_shouldReturnMedicalRecordNotFoundException()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=Lily&lastName=Saguet"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("MedicalRecord not found, please try again")))
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MedicalRecordNotFoundException))
				.andExpect(result -> assertEquals("MedicalRecord not found, please try again!",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request Get when input firstName or lastName is empty then
	 * throw a {@link EmptyFieldsException} and the status isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestGetMedicanRecord_whenInputFirstNameOrLastNameIsEmpty_shouldReturnAnEmptyFieldsException()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=&lastName="))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field cannot be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request Post when MedicalRecord to save already exist then
	 * throw {@link MedicalRecordAlreadyExistException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenMedicalRecordAlreadyExist_shouldThrowMedicalRecordAlreadyExistException()
			throws Exception {
		// GIVEN
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord MedicalRecordTenleyBoydExistInArray = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.content(medicalRecordControllerTest.asJsonString(MedicalRecordTenleyBoydExistInArray))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",
						is("The medicalRecord that we try to save already exist, please proceed to an update")))
				.andExpect(result -> assertTrue(
						result.getResolvedException() instanceof MedicalRecordAlreadyExistException))
				.andExpect(result -> assertEquals("MedicalRecord already exist",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request Post when MedicalRecord to save not exist then return
	 * MedicalRecord saved
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenMedicalRecordNotExist_shouldSaveTheMedicalRecord() throws Exception {
		// GIVEN
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord medicalRecordNotExistInArray = new MedicalRecord("Joana", "Martin", "02/05/1992",
				new ArrayList<>(Arrays.asList("terazine:500mg")), new ArrayList<>(Arrays.asList("shellfish")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.post("/medicalRecord")
				.content(medicalRecordControllerTest.asJsonString(medicalRecordNotExistInArray))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Joana")))
				.andExpect(jsonPath("$.lastName", is("Martin")))
				.andExpect(jsonPath("$.birthdate", is("02/05/1992")))
				.andExpect(jsonPath("$.medications[0]", is("terazine:500mg")))
				.andExpect(jsonPath("$.allergies[0]", is("shellfish"))).andDo(print());
	}

	/**
	 * Method that test request Delete when MedicalRecord to delete exist then return
	 * a String "SUCCESS" to confirm the deletion
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenMedicalRecordExistWithFirstNameJacobAndLastNameBoyd_thenReturnMessageSUCCESS()
			throws Exception {
		// GIVEN
		// WHEN

		// THEN
		mockMvcMedicalRecord.perform(delete("/medicalRecord?firstName=jacob&lastName=Boyd"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", is("SUCCESS"))).andDo(print());

		mockMvcMedicalRecord.perform(get("/medicalRecord?firstName=jacob&lastName=Boyd"))
							.andExpect(status().isNotFound())
							.andExpect(jsonPath("$.firstName").doesNotExist())
							.andExpect(jsonPath("$.lastName").doesNotExist()).andDo(print());
	}

	/**
	 * Method that test request Delete when MedicalRecord to delete not exist then
	 * return a String "MedicalRecord cannot be Deleted"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenMedicalRecordNotExist__thenReturnMessageMedicalRecordCannotBeDeleted()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvcMedicalRecord.perform(delete("/medicalRecord?firstName=Zozor&lastName=Zeros"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$", is("MedicalRecord cannot be Deleted")))
							.andDo(print());
	}

	/**
	 * Method that test request Put when MedicalRecord to update exist then return
	 * MedicalRecord with the field allergies updated with "strawberry" 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenfieldAllergiesIsModified_thenVerifyThatFieldAllergiesWasUpdated() throws Exception {
		// GIVEN
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord MedicalRecordToUpdateWhenExist = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan", "strawberry")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.content(medicalRecordControllerTest.asJsonString(MedicalRecordToUpdateWhenExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.birthdate", is("03/06/1984")))
				.andExpect(jsonPath("$.allergies[0]", is("nillacilan")))
				.andExpect(jsonPath("$.allergies[1]", is("strawberry")))
				.andDo(print());
	}

	/**
	 * Method that test request Put when MedicalRecord to update exist then return
	 * MedicalRecord with several fields updated
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenSeveralfieldsAreModified_thenVerifyThatMedicalRecordWasbeenUpdated() throws Exception {
		// GIVNE
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord medicalRecordToUpdate = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("shellfish")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.content(medicalRecordControllerTest.asJsonString(medicalRecordToUpdate))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jonanathan")))
				.andExpect(jsonPath("$.lastName", is("Marrack")))
				.andExpect(jsonPath("$.birthdate", is("01/03/1989")))
				.andExpect(jsonPath("$.medications[0]", is("aznol:350mg")))
				.andExpect(jsonPath("$.medications[1]", is("hydrapermazol:100mg")))
				.andExpect(jsonPath("$.allergies[0]", is("shellfish")))
				.andDo(print());
	}

	/**
	 * Method that test request Put when MedicalRecord to update not exist then throw
	 * a {@link MedicalRecordNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenMedicalRecordToUpdateNotExist_thenVerifyIfMedicalRecordNotFoundExceptionIsThrown()
			throws Exception {
		// GIVEN
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord medicalRecordToUpdateNotExist = new MedicalRecord("Babar", "Elephant", "02/08/1950",
				new ArrayList<>(Arrays.asList("thradox:700mg")), new ArrayList<>(Arrays.asList("banana")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.content(medicalRecordControllerTest.asJsonString(medicalRecordToUpdateNotExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("MedicalRecord not found, please try again")))
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MedicalRecordNotFoundException))
				.andExpect(result -> assertEquals(
						"The medicalRecord that we want update not exist for the person: "
								+ medicalRecordToUpdateNotExist.getFirstName() + " "
								+ medicalRecordToUpdateNotExist.getLastName(),
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request Put when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenInputFieldsIsInvalid_shouldReturnMethodArgumentNotValidExceptionMustNotBeBlank()
			throws Exception {
		// GIVEN
		MedicalRecordControllerTest medicalRecordControllerTest = new MedicalRecordControllerTest();
		MedicalRecord medicalRecordToUpdateWhenFieldFirstNameInvalid = new MedicalRecord("", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan", "strawberry")));
		// WHEN

		// THEN
		mockMvcMedicalRecord
				.perform(MockMvcRequestBuilders.put("/medicalRecord")
				.content(medicalRecordControllerTest
				.asJsonString(medicalRecordToUpdateWhenFieldFirstNameInvalid))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}
}
