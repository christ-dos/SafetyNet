package com.safetynet.alerts;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@WebMvcTest(PersonDAO.class)
@Slf4j
public class PersonDAOTest {
	
	@Autowired
	private IPersonDAO ipersonDAO;
	
	
	@Test
	public void testGetPersonDAO_whenExist_resultShouldGivenJacobBoyd() {
		//GIVEN
		String firstName = "Jacob";
		String lastName = "Boyd";
		//WHEN
		Person getPersonResult = ipersonDAO.getPerson(firstName, lastName);
		//THEN
		assertEquals("Jacob", getPersonResult.getFirstName());
		assertEquals("Boyd", getPersonResult.getLastName());
	}
	
	@Test
	public void testGetPersonDAO_whenNotExist_resultShouldGivenNull() {
		//GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		//WHEN
		Person getPersonResult = ipersonDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(getPersonResult);
	}
	
	@Test
	public void testSavePersonDAO_whenPersonNotExist_resultShouldVerifyThatPersonTestIsSameThatResultExpected() {
		//GIVEN
		Person personTest = new Person("Tata","Zorro", "15 rue des Templiers","Relvas","6230","000-000-0000","zorrod@email.com");
		//WHEN
		Person resultPerson = ipersonDAO.save(personTest);
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
		ipersonDAO.delete(personTest);
		Person resultCallGetPersonAfterDelete = ipersonDAO.getPerson(firstName, lastName);
		//THEN
		assertNull(resultCallGetPersonAfterDelete);
	}
	
	@Test
	public void testUpdatePersonDAO() {
		//GIVEN
		Person person = ipersonDAO.getPerson("Lily", "Cooper");
		int indexLillyCooper= ipersonDAO.getPersons().indexOf(person);
		Person personToUpdate = new Person("Lily","Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845","lily@email.com");
		//WHEN
		Person resultAfterUpdate = ipersonDAO.update(indexLillyCooper, personToUpdate);
		int indexLillyCooperUpdated = ipersonDAO.getPersons().indexOf(personToUpdate);
		//THEN
		assertEquals(person.getFirstName(), resultAfterUpdate.getFirstName());
		assertEquals(indexLillyCooper, indexLillyCooperUpdated);
		assertEquals(personToUpdate.getAddress(), resultAfterUpdate.getAddress());
	}
	
}
