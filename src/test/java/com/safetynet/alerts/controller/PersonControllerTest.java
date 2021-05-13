package com.safetynet.alerts.controller;

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

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;



@WebMvcTest(PersonController.class)
public class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IPersonService personServiceMock;
	
	@MockBean
	private IPersonDAO personDAOMock;
	
	@MockBean
	private List<Person> mockList;
	
	@Autowired
	ObjectMapper objetMapper;
	
	
	public String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    }catch (Exception e) {
	        throw new RuntimeException("The obj does not be writting", e);
	    }
	}
	
	@Test
	public void testGetPerson_whenPersonExist_thenReturnStatusOk() throws Exception {
		//GIVEN
		Person personTest = new Person("John","Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personTest);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personTest);
		//WHEN
		
		//THEN
		mockMvc.perform(get("/person?firstName=John&lastName=Boyd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is("John")))
		.andExpect(jsonPath("$.lastName", is("Boyd")))
		.andExpect(jsonPath("$.zip", is("97451")))
		.andDo(print());
	}
	@Test
	public void testGetPerson_whenPersonNotexist_thenReturnStatusOk() throws Exception {
		//GIVEN
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(null);
		//WHEN
		
		//THEN
		mockMvc.perform(get("/person?firstName=Lilly&lastName=Saguet"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName").doesNotExist())
		.andExpect(jsonPath("$.lastName").doesNotExist())
		.andDo(print());
	}
	
	@Test
	public void testGetPerson_whenInputFirstNameOrLastNameIsEmpty_shouldReturnAnEmptyFieldsException() throws Exception {
		//GIVEN
		 when(personServiceMock.getPerson(anyString(), anyString()))
		 .thenThrow(new EmptyFieldsException("The fields firstName and lastName can not be empty"));
		//WHEN
		
		//THEN
		mockMvc.perform(get("/person?firstName=&lastName="))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$").doesNotExist())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
	    .andExpect(result -> assertEquals("The fields firstName and lastName can not be empty", result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	@Test
	public void testSavePerson_whenPersonToSaveExist_thenReturnNull() throws Exception {
		//GIVEN
		List <Person> myListPersons = personDAOMock.getPersons();
		Person personToAddExist = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
		
		when(personDAOMock.getPersons()).thenReturn(myListPersons);
		when(personServiceMock.addPerson(any())).thenReturn(null);
		//WHEN
		
		//THEN
		 mockMvc.perform(MockMvcRequestBuilders
				.post("/person")
				.content(asJsonString(personToAddExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").doesNotExist())
				.andDo(print());
	}
	
	@Test
	public void testSavePerson_whenPersonToSaveNotExist_thenCallMethodSave() throws Exception {
		//GIVEN
		Person personToSave = new Person("Jojo", "Dupond", "1509 rue des fleurs","Rouvbaix","59100","000-000-0012","jojod@email.com");
		when(personServiceMock.addPerson(any())).thenReturn(personToSave);
		when(mockList.size()).thenReturn(23);
		when(mockList.indexOf(any())).thenReturn(-1);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToSave);
		//WHEN
		
		//THEN
		 mockMvc.perform(MockMvcRequestBuilders
				.post("/person")
				.content(asJsonString(personToSave))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jojo")))
				.andExpect(jsonPath("$.lastName", is("Dupond")))
				.andExpect(jsonPath("$.address", is("1509 rue des fleurs")))
				.andExpect(jsonPath("$.zip", is("59100")))
				.andDo(print());
		
	}
	
	@Test
	public void testDeletePerson_whenInputFirstNameJohnAndLastNameBoyd_shouldReturnNothing() throws Exception {
		//GIVEN
		when(personDAOMock.delete(any())).thenReturn("SUCESS");
		when(personServiceMock.deletePerson(any(), any())).thenReturn("SUCESS");
		
		//WHEN
		//THEN
		mockMvc.perform(delete("/person?firstName=john&lastName=Boyd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("SUCESS")))
		.andDo(print());
	}

	@Test
	public void testUpdatePerson_whenfieldCityWasModified_thenReturnPersonWithFieldCityUpdated() throws Exception {
		//GIVEN
		Person personToUpdated = new Person("John","Boyd", "1509 Culver St","Croix","97451","841-874-6512","jaboyd@email.com");
		Person personRecordedInArray = new Person("John","Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(mockList.indexOf(any())).thenReturn(0);
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personServiceMock.updatePerson(any())).thenReturn(personToUpdated);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToUpdated);		
		//WHEN
		
		//THEN
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(asJsonString(personToUpdated))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John")))
				.andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.city", is("Croix")))
				.andDo(print());
	}
	
/**	@throws Exception 
 * @Test
	public void testUpdatePerson_whenAllfieldsWereModified_thenReturnPersonWithAllFieldsUpdated() throws Exception {
		//GIVEN
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdated = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "NewYork", "97450", "841-874-0000", "mynewemail@email.com");
		List <Person> myListPersons = personDAOMock.getPersons();
		
		when(personDAOMock.getPersons()).thenReturn(myListPersons);
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personServiceMock.updatePerson(any())).thenReturn(personToUpdated);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToUpdated);		
		//WHEN
		
		//THEN
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(asJsonString(personToUpdated))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Jonanathan")))
				.andExpect(jsonPath("$.lastName", is("Marrack")))
				.andExpect(jsonPath("$.address", is("15 NouvelleAdresse")))
				.andExpect(jsonPath("$.city", is("NewYork")))
				.andExpect(jsonPath("$.zip", is("97450")))
				.andExpect(jsonPath("$.phone", is("841-874-0000")))
				.andExpect(jsonPath("$.email", is("mynewemail@email.com")))
				.andDo(print());
	}*/
	
	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() throws Exception{
		//GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513", "babar@email.com" );
		
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(null);
		when(personServiceMock.updatePerson(personToUpdateButNotExist))
		.thenThrow(new PersonNotFoundException("The person that we want update not exist : " + personToUpdateButNotExist.getFirstName() + " " + personToUpdateButNotExist.getLastName()));
		//WHEN
		
		//THEN
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(asJsonString(personToUpdateButNotExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").doesNotExist())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
			    .andExpect(result -> assertEquals("The person that we want update not exist : " + personToUpdateButNotExist.getFirstName() + " " + personToUpdateButNotExist.getLastName(), result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
