package com.safetynet.alerts.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * Class service that process the request of controller
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
public class PersonServiceTest {

	private PersonService personServiceTest;

	@Mock
	private PersonDAO personDAOMock;

	@Mock
	private List<Person> mockList;


	@BeforeEach
	public void setUpPerTest() {
		mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");
		Person index2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person index3 = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513",
				"drk@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index2);
		mockList.add(index3);

		personDAOMock = mock(PersonDAO.class);
		personServiceTest = new PersonService(personDAOMock);
	}

	@Test
	public void testGetPerson_whenPersonExistWithFirstNameJohnAndLastNameBoyd_resultShouldGetObjectPersonJohnBoyd()
			throws EmptyFieldsException {
		// GIVEN
		Person personInput = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		String firstName = personInput.getFirstName();
		String lastName = personInput.getLastName();
		when(personDAOMock.getPerson(firstName, lastName)).thenReturn(personInput);
		// WHEN
		Person resultPersonGetted = personServiceTest.getPerson(firstName, lastName);
		// THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		assertNotNull(resultPersonGetted);
		assertEquals(personInput, resultPersonGetted);
	}

	@Test
	public void testGetPerson_whenInputPersonNotExist_resultShouldThrowPersonNotFoundException()
			throws EmptyFieldsException {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		// WHEN

		// THEN
		// verify that method getPerson was never called
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(PersonNotFoundException.class, () -> personServiceTest.getPerson(firstName, lastName));
	}

	@Test
	public void testGetPerson_whenFielsFirstNameOrLastNameIsNull_thenReturnNullPonterException() {
		// GIVEN
		String firstName = "John";
		String lastName = "";
		// WHEN

		// THEN
		// verify that method getPerson was never called
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(EmptyFieldsException.class, () -> personServiceTest.getPerson(firstName, lastName));
	}

	@Test
	public void testAddPerson_whenPersonToAddNotExist_thenVerifyIfPersonIsAdded()
			throws EmptyFieldsException, PersonAlreadyExistException {
		// GIVEN
		Person personToAdd = new Person("Jojo", "Dupond", "1509 rue des fleurs", "Roubaix", "59100", "000-000-0012",
				"jojod@email.com");
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToAdd);
		// WHEN
		Person resultAfterAddPerson = personServiceTest.addPerson(personToAdd);
		// THEN
		verify(personDAOMock, times(1)).getPersons();
		verify(personDAOMock, times(1)).save(anyInt(), any());
		assertEquals(personToAdd, resultAfterAddPerson);
		assertEquals(personToAdd.getEmail(), resultAfterAddPerson.getEmail());
		assertEquals(personToAdd.getFirstName(), resultAfterAddPerson.getFirstName());
	}

	@Test
	public void testAddPerson_whenPersonToAddExist_thenThrowPersonAlreadyExistException() {
		// GIVEN
		Person personToAddAlreadyExist = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6512", "tenz@email.com");
		when(personDAOMock.getPersons()).thenReturn(mockList);
		// WHEN

		// THEN
		assertThrows(PersonAlreadyExistException.class, () -> personServiceTest.addPerson(personToAddAlreadyExist));
	}

	@Test
	public void testUpdatePerson_whenPersonExistFirstNameJonanthanLastNameMarrack_thenReturnPersonJonanathanMarrackWithTheFieldAdressUpdated()
			throws EmptyFieldsException {
		// GIVEN
		// List<Person> mockList1 = mock(ArrayList.class);
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		Person personToUpdate = new Person("Jonanathan", "Marrack", "30 rue des Ursulines", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personDAOMock.save(anyInt(), any(Person.class))).thenReturn(personToUpdate);
		// WHEN
		Person resultPersonUpdated = personServiceTest.updatePerson(personToUpdate);
		// THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).save(anyInt(), any());
		// the field address that was been modified has been updated
		assertEquals("30 rue des Ursulines", resultPersonUpdated.getAddress());
	}

	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() {
		// GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451",
				"841-874-6513", "babar@email.com");
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(null);
		// WHEN

		// THEN
		assertThrows(PersonNotFoundException.class, () -> personServiceTest.updatePerson(personToUpdateButNotExist));
	}

	@Test
	public void testDeletePerson_whenPersonExist() {
		// GIVEN
		Person personToDeleted = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");
		String firstName = personToDeleted.getFirstName();
		String lastName = personToDeleted.getLastName();

		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personToDeleted);
		when(personDAOMock.delete(personToDeleted)).thenReturn("SUCESS");
		// WHEN
		String result = personServiceTest.deletePerson(firstName, lastName);
		// THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).delete(any());
		assertEquals("SUCESS", result);

	}

	@Test
	public void testDeletePerson_whenPersonNotExist() {
		// GIVEN
		Person personToDeleteNotExist = new Person("Alice", "Lefevre", "1509 Culver St", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		String firstName = personToDeleteNotExist.getFirstName();
		String lastName = personToDeleteNotExist.getLastName();

		when(personDAOMock.getPerson(firstName, lastName)).thenReturn(null);
		// WHEN
		String resultMessage = personServiceTest.deletePerson(firstName, lastName);
		// THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		// verify that method delete was not invoked
		verify(personDAOMock, times(0)).delete(any());
		assertEquals("Person Not Deleted", resultMessage);

	}
}
