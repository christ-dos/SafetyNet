package com.safetynet.alerts;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@WebMvcTest(PersonDAO.class)
@Slf4j
public class PersonDAOTest {
	
	@Autowired
	private PersonDAO personDAO;
	
	
	@Test
	public void testGetPersonDAOThatExist_resultShouldGivenJacobBoyd() {
		//GIVEN
		String firstName = "Jacob";
		String lastName = "Boyd";
		//WHEN
		Person getPersonResult = personDAO.getPerson(firstName, lastName);
		//THEN
		assertEquals("Jacob", getPersonResult.getFirstName());
		assertEquals("Boyd", getPersonResult.getLastName());
	}
	
	@Test
	public void testGetPersonDAOThatNotExist_resultShouldGivenNull() {
		//GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		//WHEN
		Person getPersonResult = personDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(getPersonResult);
	}
	
	@Test
	public void testSavePersonDAO_resultShouldVerifyThatPersonTestIsSameThatResultExpected() {
		//GIVEN
		Person personTest = new Person("Tata","Zorro", "15 rue des Templiers","Relvas","6230","000-000-0000","zorrod@email.com");
		//WHEN
		Person resultPerson = personDAO.save(personTest);
		//THEN
		assertSame(personTest,resultPerson);
		assertEquals("Tata",resultPerson.getFirstName());
	}
	
	@Test
	public void testDeletePersonDAO_afterCallMethodGetWithJohnBoyd_resultShouldBeNull() {
		//GIVEN
		Person personTest = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personTest.getFirstName();
		String lastName = personTest.getLastName();
		//WHEN
		personDAO.delete(personTest);
		Person resultCallGetPersonAfterDelete = personDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(resultCallGetPersonAfterDelete);
	}
	
	@Test
	public void testUpdatePersonDAO() {
		//GIVEN
		Person person = personDAO.getPerson("Lily", "Cooper");
		int indexLillyCooper= personDAO.getPersons().indexOf(person);
		Person personToUpdate = new Person("Lily","Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845","lily@email.com");
		//WHEN
		Person resultAfterUpdate = personDAO.update(indexLillyCooper, personToUpdate);
		int indexLillyCooperUpdated = personDAO.getPersons().indexOf(personToUpdate);
		//THEN
		assertEquals(person.getFirstName(), resultAfterUpdate.getFirstName());
		assertEquals(indexLillyCooper, indexLillyCooperUpdated);
		assertEquals(personToUpdate.getAddress(), resultAfterUpdate.getAddress());
	}
	
}
