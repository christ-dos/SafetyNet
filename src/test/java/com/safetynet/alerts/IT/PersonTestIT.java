package com.safetynet.alerts.IT;

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

import com.safetynet.alerts.controller.PersonControllerTest;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonTestIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	@Test
	public void testGetPersonThatExist_whenInputFirstNameRogerAndLastNameBoyd_shouldReturnStatusOK() throws Exception {
		mockMvc.perform(get("/person?firstName=Roger&lastName=Boyd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is("Roger")))
		.andExpect(jsonPath("$.lastName", is("Boyd")))
		.andExpect(jsonPath("$.email", is("jaboyd@email.com")))
		.andDo(print());
	}
	
	@Test
	public void testGetPersonThatNotExist_whenInputFirstNameLillyAndLastNameSaguet_shouldReturnNull() throws Exception {
		mockMvc.perform(get("/person?firstName=Lilly&lastName=Saguet"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$").doesNotExist())
		.andDo(print());
	}
	
	@Test
	public void testGetPerson_whenInputFirstNameOrLastNameIsEmpty_shouldReturnAnEmptyFieldsException() throws Exception {
		mockMvc.perform(get("/person?firstName=&lastName="))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$").doesNotExist())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
	    .andExpect(result -> assertEquals("The fields firstName and lastName can not be empty", result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	@Test
	public void testRequestPost_whenPersonAlreadyExist_shouldUpdateThePerson() throws Exception {
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("John", "Boyd", "1509 New address St", "New York","97451","841-874-6512","jaboyd@email.com");
	
		 mockMvc.perform(MockMvcRequestBuilders
				.post("/person")
				.content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.address", is("1509 New address St")))
				.andExpect(jsonPath("$.city", is("New York")))
				.andDo(print());
	}
	
	@Test
	public void testRequestPost_whenPersonNotExist_shouldSaveThePerson() throws Exception {
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Joana","Martin", "22 Croix St","TerraNova","59000","000-000-1203","jomartin@email.com");
	
		 mockMvc.perform(MockMvcRequestBuilders
				.post("/person")
				.content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Joana")))
				.andExpect(jsonPath("$.lastName", is("Martin")))
				.andExpect(jsonPath("$.address", is("22 Croix St")))
				.andExpect(jsonPath("$.city", is("TerraNova")))
				.andDo(print());
	}
	
	@Test
	public void testRequestDelete_whenPersonExist_PersonWithFirstNameJacobAndLastNameBoyd() throws Exception {
		mockMvc.perform(delete("/person?firstName=jacob&lastName=Boyd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("SUCESS")))
		.andDo(print());
		
		/**mockMvc.perform(get("/person?firstName=jacob&lastName=Boyd"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.firstName").doesNotExist())
		.andExpect(jsonPath("$.lastName").doesNotExist())
		.andDo(print());*/
	}
	
	@Test
	public void testRequetePut_whenOnefieldIsUpdated_thenVerifyThatFieldCityWasUpdated() throws Exception {
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("John","Boyd", "1509 Culver St","Croix","97451","841-874-6512","jaboyd@email.com");
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.city", is("Croix")))
				.andDo(print());
	}
	
	@Test
	public void testRequetePut_whenAllfielsAreUpdated_thenVerifyThatPersonWasbeenUpdated() throws Exception {
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "NewYork", "97450", "841-874-6512", "jojo@email.com");
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jonanathan")))
				.andExpect(jsonPath("$.lastName", is("Marrack")))
				.andExpect(jsonPath("$.address", is("15 NouvelleAdresse")))
				.andExpect(jsonPath("$.city", is("NewYork")))
				.andExpect(jsonPath("$.zip", is("97450")))
				.andExpect(jsonPath("$.phone", is("841-874-6512")))
				.andExpect(jsonPath("$.email", is("jojo@email.com")))
				.andDo(print());
	}
	
	@Test
	public void testRequetePut_whenPersonToUpdateNotExist_thenVerifyIfPersonNotFoundExceptionIsThrown() throws Exception {
		PersonControllerTest personControllerTest = new PersonControllerTest();
		Person personTest = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513", "babar@email.com" );
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(personControllerTest.asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").doesNotExist())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
			    .andExpect(result -> assertEquals("The person that we want update not exist : " + personTest.getFirstName() + " " + personTest.getLastName(), result.getResolvedException().getMessage()))
				.andDo(print());
	}
	
	

}
