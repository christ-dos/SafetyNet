package com.safetynet.alerts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;



@WebMvcTest(PersonController.class)
public class PersonControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IPersonService iPersonService;
	
	@Autowired
	ObjectMapper objetMapper;
	
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    }catch (Exception e) {
	        throw new RuntimeException("The obj does not be writting", e);
	    }
	}
	
	@Test
	public void testGetPersonDataJsonInSuccess() throws Exception {
		//Person personTest = new Person("John","Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		
		mockMvc.perform(get("/person?firstName=John&lastName=Boyd"))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void testSavePerson() throws Exception {
		Person personTest = new Person("John","Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		
		 mockMvc.perform(MockMvcRequestBuilders
				.post("/person")
				.content(asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
		
	}
	
	@Test
	public void testDeletePersonByFirstNameAndLastName() throws Exception {
		mockMvc.perform(delete("/person?firstName=john&lastName=Boyd"))
		.andExpect(status().isOk());
	}

	@Test
	public void testUpdatePerson() throws Exception {
		Person personTest = new Person("John","Boyd", "1509 Culver St","Croix","97451","841-874-6512","jaboyd@email.com");
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/person")
				.content(asJsonString(personTest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
