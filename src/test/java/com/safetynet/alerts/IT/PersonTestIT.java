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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.safetynet.alerts.controller.PersonControllerTest;
import com.safetynet.alerts.exceptions.CityNotFoundException;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

/**
 * Class integration test for {@link Person} entity which verify that all
 * classes works correctly together
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonTestIT {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Method that test requestGet when person exist then the status of the request
	 * isOk and the firstName and lastName is Roger Boyd
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetPersonExist_whenInputFirstNameRogerAndLastNameBoyd_thenReturnStatusOK()
			throws Exception {
		// GIVEN

		// WHEN

		// THEN
		mockMvc.perform(get("/person?firstName=Roger&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Roger"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.email", is("jaboyd@email.com"))).andDo(print());
	}

	/**
	 * Method that test requestGet when person not exist then throw a
	 * {@link PersonNotFoundException} and the status of the request isNotFound
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetPersonNotExist_whenInputFirstNameLilyAndLastNameSaguet_thenReturnPersonNotFoundException()
			throws Exception {
		// GIVEN

		// WHEN

		// THEN
		mockMvc.perform(get("/person?firstName=Lily&lastName=Saguet")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Person not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test requestGet when input firstName or lastName is empty then
	 * throw a {@link EmptyFieldsException} and the status isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestGetPerson_whenInputFirstNameOrLastNameIsEmpty_thenReturnAnEmptyFieldsException()
			throws Exception {
		// GIVEN

		// WHEN

		// THEN
		mockMvc.perform(get("/person?firstName=&lastName=")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field cannot be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test requestPost when person to save already exist then throw
	 * {@link PersonAlreadyExistException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenPersonAlreadyExist_thenThrowPersonAlreadyExistException() throws Exception {
		// GIVEN
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/person").content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message",
						is("The Person that we try to save already exist, please proceed to an update")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonAlreadyExistException))
				.andExpect(result -> assertEquals("Person already exist", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test requestPost when person to save not exist then return person
	 * saved
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenPersonNotExist_thenSavePerson() throws Exception {
		// GIVEN
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Joana", "Martin", "22 Croix St", "TerraNova", "59000", "000-000-1203",
				"jomartin@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/person").content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Joana"))).andExpect(jsonPath("$.lastName", is("Martin")))
				.andExpect(jsonPath("$.address", is("22 Croix St"))).andExpect(jsonPath("$.city", is("TerraNova")))
				.andDo(print());
	}

	/**
	 * Method that test requestDelete when person to delete exist then return a
	 * String "SUCCESS" to confirm the deletion
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenPersonExist_PersonWithFirstNameJacobAndLastNameBoyd_thenReturnMessageSUCCESS()
			throws Exception {
		// GIVEN

		// WHEN

		// THEN
		mockMvc.perform(delete("/person?firstName=jacob&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("SUCCESS"))).andDo(print());

		mockMvc.perform(get("/person?firstName=jacob&lastName=Boyd")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.firstName").doesNotExist()).andExpect(jsonPath("$.lastName").doesNotExist())
				.andDo(print());
	}

	/**
	 * Method that test requestDelete when person to delete not exist then return a
	 * String "person not deleted"
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenPersonNotExist__thenReturnMessagePersonNotDeleted() throws Exception {
		// GIVEN

		// WHEN

		// THEN
		mockMvc.perform(delete("/person?firstName=Zozor&lastName=Zeros")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Person not Deleted"))).andDo(print());
	}

	/**
	 * Method that test requestPut when person to update exist then return person
	 * with the field city updated with "Croix"
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenOnefieldIsUpdated_thenVerifyThatFieldCityWasUpdated() throws Exception {
		// GIVEN
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("John", "Boyd", "1509 Culver St", "Croix", "97451", "841-874-6512",
				"jaboyd@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.city", is("Croix"))).andDo(print());
	}

	/**
	 * Method that test requestPut when person to update exist then return person
	 * with all fields updated
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenAllfieldsAreUpdated_thenVerifyThatPersonWasbeenUpdated() throws Exception {
		// GIVNE
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "NewYork", "97450",
				"841-874-6512", "jojo@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jonanathan"))).andExpect(jsonPath("$.lastName", is("Marrack")))
				.andExpect(jsonPath("$.address", is("15 NouvelleAdresse"))).andExpect(jsonPath("$.city", is("NewYork")))
				.andExpect(jsonPath("$.zip", is("97450"))).andExpect(jsonPath("$.phone", is("841-874-6512")))
				.andExpect(jsonPath("$.email", is("jojo@email.com"))).andDo(print());
	}

	/**
	 * Method that test requestPut when person to update not exist then throw a
	 * {@link PersonNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenPersonToUpdateNotExist_thenVerifyIfPersonNotFoundExceptionIsThrown()
			throws Exception {
		// GIVEN
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513",
				"babar@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Person not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(
						result -> assertEquals("The person that we want update not exist: " + personTest.getFirstName()
								+ " " + personTest.getLastName(), result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test requestPut when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenInputFieldsIsInvalid_thenReturnMethodArgumentNotValidExceptionMustNotBeBlank()
			throws Exception {
		// GIVEN
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personToUpdate = new Person("", "Boyd", "1509 Culver St", "Croix", "97451", "841-874-6512",
				"jaboyd@email.com");
		// WHEN

		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(personControllerTest.asJsonString(personToUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}

	/**
	 * Method that test request getEmailResident when city input is Culver and city
	 * exist then return a list of email
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetEmailResident_whenCityIsCulverAndExist__thenReturnListOfEmail() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$.[0]", is("jaboyd@email.com")))
				.andExpect(jsonPath("$.[2]", is("tenz@email.com"))).andDo(print());
	}

	/**
	 * Method that test request getEmailResident when city input is Boston and not
	 * exist throw a {@link CityNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetEmailResident_whenCityNotExistIsBoston_thenReturnACityNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/communityEmail?city=Boston")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("The city not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CityNotFoundException))
				.andExpect(result -> assertEquals("The City not found, please try again",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
