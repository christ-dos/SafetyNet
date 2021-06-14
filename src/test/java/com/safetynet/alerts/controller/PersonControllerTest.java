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
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.CityNotFoundException;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

/**
 * Class that test the PersonController
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * An instance of PersonService
	 * 
	 * @see PersonService
	 */
	@MockBean
	private PersonService personServiceMock;

	/**
	 * An instance of PersonDAO
	 * 
	 * @see PersonDAO
	 */
	@MockBean
	private PersonDAO personDAOMock;

	/**
	 * A mock of a arrayList of persons
	 */
	@Mock
	private List<Person> mockList;

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
	 * Method that test getPerson when person exist the status of the request is OK
	 * and the firstName and lastName is John Boyd
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPerson_whenPersonExist_thenReturnStatusOk() throws Exception {
		// GIVEN
		Person personTest = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personTest);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personTest);
		// WHEN
		// THEN
		mockMvc.perform(get("/person?firstName=John&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.zip", is("97451"))).andDo(print());
	}

	/**
	 * Method that test getPerson when person not exist then throw
	 * {@link PersonNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPerson_whenPersonNotexist_thenReturnPersonNotFoundException() throws Exception {
		// GIVEN
		when(personServiceMock.getPerson(anyString(), anyString()))
				.thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		mockMvc.perform(get("/person?firstName=Lilly&lastName=Saguet")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test getPerson when input firstName or lastName is empty then
	 * throw a {@link EmptyFieldsException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetPerson_whenInputFirstNameOrLastNameIsEmpty_shouldReturnAnEmptyFieldsException()
			throws Exception {
		// GIVEN
		when(personServiceMock.getPerson(anyString(), anyString()))
				.thenThrow(new EmptyFieldsException("Field cannot be empty"));
		// WHEN
		// THEN
		mockMvc.perform(get("/person?firstName=&lastName=Boyd")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Field cannot be empty")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
				.andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test savePerson when person to save exist then throw
	 * {@link PersonAlreadyExistException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testSavePerson_whenPersonToSaveExist_thenReturnPersonAlreadyExistException() throws Exception {
		// GIVEN
		Person personToAddExist = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		when(personServiceMock.addPerson(any())).thenThrow(new PersonAlreadyExistException("Person already exist"));
		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/person").content(asJsonString(personToAddExist))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonAlreadyExistException))
				.andExpect(result -> assertEquals("Person already exist", result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test savePerson when person to save not exist then the person is
	 * saved and the status isOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testSavePerson_whenPersonToSaveNotExist_thenPersonIsSaved() throws Exception {
		// GIVEN
		Person personToSave = new Person("Jojo", "Dupond", "1509 rue des fleurs", "Roubaix", "59100", "000-000-0012",
				"jojod@email.com");
		when(personServiceMock.addPerson(any())).thenReturn(personToSave);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToSave);
		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.post("/person").content(asJsonString(personToSave))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jojo"))).andExpect(jsonPath("$.lastName", is("Dupond")))
				.andExpect(jsonPath("$.address", is("1509 rue des fleurs"))).andExpect(jsonPath("$.zip", is("59100")))
				.andDo(print());
	}

	/**
	 * Method that test deletePerson when person exist then return a String
	 * "SUCCESS" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeletePerson_whenInputFirstNameJohnAndLastNameBoydPersonExist_shouldReturnAStringWithSUCCESS()
			throws Exception {
		// GIVEN
		when(personDAOMock.delete(any())).thenReturn("SUCESS");
		when(personServiceMock.deletePerson(any(), any())).thenReturn("SUCCESS");
		// WHEN
		// THEN
		mockMvc.perform(delete("/person?firstName=john&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("SUCCESS"))).andDo(print());
	}

	/**
	 * Method that test deletePerson when person not exist then return a String
	 * "Person not deleted" and the status IsOk
	 *
	 * @throws Exception
	 */
	@Test
	public void testDeletePerson_whenPersonNotExist_shouldReturnAStringWithPersonNotDeleted() throws Exception {
		// GIVEN
		when(personServiceMock.deletePerson(any(), any())).thenReturn("Person not deleted");
		// WHEN
		// THEN
		mockMvc.perform(delete("/person?firstName=jo&lastName=Lapin")).andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Person not deleted"))).andDo(print());
	}

	/**
	 * Method that test updatePerson when person exist and the field city was
	 * modified then return the person updated with the field city updated
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdatePerson_whenFieldCityWasModified_thenReturnPersonWithFieldCityUpdated() throws Exception {
		// GIVEN
		Person personToUpdate = new Person("John", "Boyd", "1509 Culver St", "Croix", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person personRecordedInArray = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");

		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personServiceMock.updatePerson(any())).thenReturn(personToUpdate);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToUpdate);
		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(personToUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.city", is("Croix"))).andDo(print());
	}

	/**
	 * Method that test updatePerson when person not exist then throw
	 * {@link PersonNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() throws Exception {
		// GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451",
				"841-874-6513", "babar@email.com");

		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(null);
		when(personServiceMock.updatePerson(personToUpdateButNotExist))
				.thenThrow(new PersonNotFoundException("The person that we want update not exist : "
						+ personToUpdateButNotExist.getFirstName() + " " + personToUpdateButNotExist.getLastName()));
		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(personToUpdateButNotExist))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Person not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(
						result -> assertEquals(
								"The person that we want update not exist : " + personToUpdateButNotExist.getFirstName()
										+ " " + personToUpdateButNotExist.getLastName(),
								result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test updatePerson when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdatePerson_whenInputFieldsIsInvalid_shouldReturnMethodArgumentNotValidExceptionMustNotBeBlank()
			throws Exception {
		// GIVEN
		Person personToUpdate = new Person("", "Boyd", "1509 Culver St", "Croix", "97451", "841-874-6512",
				"jaboyd@email.com");
		// WHEN
		// THEN
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(personToUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}

	/**
	 * Method that test getEmailResident when city input is Culver and city exist
	 * then return a list of email
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetEmailResident_whenCityIsCulverAndExist__thenReturnListOfEmails() throws Exception {
		// GIVEN
		List<String> listEmail = new ArrayList<>(Arrays.asList("jaboyd@email.com", "tenz@email.com", "zarc@email.com"));
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personServiceMock.getEmailResidents(any())).thenReturn(listEmail);
		// WHEN
		// THEN
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("jaboyd@email.com"))).andExpect(jsonPath("$.[2]", is("zarc@email.com")))
				.andDo(print());
	}

	/**
	 * Method that test getEmailResident when city input is Boston and not exist
	 * throw a {@link CityNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetEmailResident_whenCityNotExistIsBoston_thenReturnACityNotFoundException() throws Exception {
		// GIVEN
		when(personServiceMock.getEmailResidents(anyString()))
				.thenThrow(new CityNotFoundException("The city not found, please try again"));
		// WHEN
		// THEN
		mockMvc.perform(get("/communityEmail?city=Boston")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("The city not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof CityNotFoundException))
				.andExpect(result -> assertEquals("The city not found, please try again",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
