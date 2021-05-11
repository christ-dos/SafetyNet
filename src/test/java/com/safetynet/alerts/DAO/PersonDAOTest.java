package com.safetynet.alerts.DAO;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@WebMvcTest(PersonDAO.class)
@Slf4j
public class PersonDAOTest {
	
	@Autowired
	private IPersonDAO personDAOTest;
	
	
	
	@Test
	public void testGetPersonDAO_whenExist_resultShouldGiven() {
		//GIVEN
		String firstName = "Tessa";
		String lastName = "Carman";
		//WHEN
		Person getPersonResult = personDAOTest.getPerson(firstName, lastName);
		//THEN
		assertEquals("Tessa", getPersonResult.getFirstName());
		assertEquals("Carman", getPersonResult.getLastName());
	}
	
	@Test
	public void testGetPersonDAO_whenNotExist_resultShouldGivenNull() {
		//GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		//WHEN
		Person getPersonResult = personDAOTest.getPerson(firstName, lastName);
		//THEN
		assertNull(getPersonResult);
	}
	
	@Test
	public void testSavePersonDAO_whenPersonNotExist_resultShouldVerifyThatPersonTestIsSameThatResultExpected() {
		//GIVEN
		int indexEnd = personDAOTest.getPersons().size();
		Person personTest = new Person("Tata","Zorro", "15 rue des Templiers","Relvas","6230","000-000-0000","zorrod@email.com");
		//WHEN
		Person resultPerson = personDAOTest.save(indexEnd, personTest);
		//THEN
		assertSame(personTest,resultPerson);
		assertEquals("Tata",resultPerson.getFirstName());
	}
	
	@Test
	public void testSavePersonDAO_whenPersonExist_shouldSavedPersonInIndex() {
		//GIVEN
		Person person = personDAOTest.getPerson("Lily", "Cooper");
		int indexLillyCooper= personDAOTest.getPersons().indexOf(person);
		Person personToSaved = new Person("Lily","Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845","lily@email.com");
		//WHEN
		Person result = personDAOTest.save(indexLillyCooper, personToSaved);
		int indexPersonSaved= personDAOTest.getPersons().indexOf(personToSaved);
		//THEN
		assertEquals(person.getFirstName(), result.getFirstName());
		assertEquals(indexLillyCooper, indexPersonSaved);
		assertEquals(personToSaved.getAddress(), result.getAddress());
	}
	
	@Test
	public void testDeletePersonDAO_whenrCallMethodGetWithJohnBoyd_resultShouldReturnMessageSUCESS() {
		//GIVEN
		Person personTest = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personTest.getFirstName();
		String lastName = personTest.getLastName();
		//WHEN
		String result = personDAOTest.delete(personTest);
		Person resultCallGetPersonAfterDelete = personDAOTest.getPerson(firstName, lastName);
		//THEN
		assertNull(resultCallGetPersonAfterDelete);
		assertEquals("SUCESS", result);
	}
}
