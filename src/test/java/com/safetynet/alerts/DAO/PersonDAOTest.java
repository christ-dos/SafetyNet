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
	private IPersonDAO iPersonDAO;
	
	
	
	@Test
	public void testGetPersonDAO_whenExist_resultShouldGiven() {
		//GIVEN
		String firstName = "Tessa";
		String lastName = "Carman";
		//WHEN
		Person getPersonResult = iPersonDAO.getPerson(firstName, lastName);
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
		Person getPersonResult = iPersonDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(getPersonResult);
	}
	
	@Test
	public void testSavePersonDAO_whenPersonNotExist_resultShouldVerifyThatPersonTestIsSameThatResultExpected() {
		//GIVEN
		Person personTest = new Person("Tata","Zorro", "15 rue des Templiers","Relvas","6230","000-000-0000","zorrod@email.com");
		//WHEN
		Person resultPerson = iPersonDAO.save(personTest);
		//THEN
		assertSame(personTest,resultPerson);
		assertEquals("Tata",resultPerson.getFirstName());
	}
	
	@Test
	public void testDeletePersonDAO_whenrCallMethodGetWithJohnBoyd_resultShouldBeNull() {
		//GIVEN
		Person personTest = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personTest.getFirstName();
		String lastName = personTest.getLastName();
		//WHEN
		iPersonDAO.delete(personTest);
		Person resultCallGetPersonAfterDelete = iPersonDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(resultCallGetPersonAfterDelete);
	}
	
	@Test
	public void testUpdatePersonDAO() {
		//GIVEN
		Person person = iPersonDAO.getPerson("Lily", "Cooper");
		int indexLillyCooper= iPersonDAO.getPersons().indexOf(person);
		Person personToUpdate = new Person("Lily","Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845","lily@email.com");
		//WHEN
		Person resultAfterUpdate = iPersonDAO.update(indexLillyCooper, personToUpdate);
		int indexLillyCooperUpdated = iPersonDAO.getPersons().indexOf(personToUpdate);
		//THEN
		assertEquals(person.getFirstName(), resultAfterUpdate.getFirstName());
		assertEquals(indexLillyCooper, indexLillyCooperUpdated);
		assertEquals(personToUpdate.getAddress(), resultAfterUpdate.getAddress());
	}
	
}
