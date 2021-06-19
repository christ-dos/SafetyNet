package com.safetynet.alerts.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.CityNotFoundException;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonAlreadyExistException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 * Class that test the PersonService
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	/**
	 * an instance of {@link PersonService}
	 */
	private PersonService personServiceTest;

	/**
	 * A mock of {@link PersonDAO}
	 */
	@Mock
	private PersonDAO personDAOMock;

	/**
	 * A mock of the arraysList of {@link Person}
	 */
	@Mock
	private List<Person> mockList;

	/**
	 * A mock of the arraysList of {@link MedicalRecord}
	 */
	@Mock
	List<MedicalRecord> mockListMedicalRecord;

	/**
	 * Method that create a mock of the ArrayList mockList with 4 elements and a
	 * mockListMedicalRecord
	 * 
	 */
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

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMedicalRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMedicalRecord1 = new MedicalRecord("Lily", "Cooper", "03/06/1994", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord indexMedicalRecord2 = new MedicalRecord("Tenley", "Boyd", "02/08/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMedicalRecord3 = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()));
		mockListMedicalRecord.add(indexMedicalRecord0);
		mockListMedicalRecord.add(indexMedicalRecord1);
		mockListMedicalRecord.add(indexMedicalRecord2);
		mockListMedicalRecord.add(indexMedicalRecord3);

		personServiceTest = PersonService.builder().personDAO(personDAOMock).build();
	}
	
	/**
	 * Method that test getListPersons then return a list of persons with 4 elements
	 * and verify that John Boyd is present in the list
	 */
	@Test
	public void testGetListPersons_thenreturnListOfPersons() {
		// GIVEN
		Person personJohnBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		when(personDAOMock.getPersons()).thenReturn(mockList);
		// WHEN
		List<Person> resultListgetted = personServiceTest.getListPersons();
		// THEN
		assertEquals(personJohnBoyd, resultListgetted.get(0));
		// the list contain 4 elements
		assertEquals(4, resultListgetted.size());
	}
	
	/**
	 * Method that test getPerson with firstName John and LastName Boyd when person
	 * exist then return John Boyd and verify that the method getPerson was called
	 *
	 */
	@Test
	public void testGetPerson_whenPersonExistWithFirstNameJohnAndLastNameBoyd_thenReturnPersonJohnBoyd() {
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
		assertEquals(personInput, resultPersonGetted);
	}

	/**
	 * Method that test getPerson when person not exist then should throw a
	 * {@link PersonNotFoundException} and verify that the method getPerson was not
	 * called
	 */
	@Test
	public void testGetPerson_whenInputPersonNotExist_thenThrowPersonNotFoundException() {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		// WHEN
		// THEN
		// verify that method getPerson was not called
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(PersonNotFoundException.class, () -> personServiceTest.getPerson(firstName, lastName));
	}

	/**
	 * Method that test getPerson when field firstName or lastName is empty then
	 * throw a {@link EmptyFieldsException} and verify that the method getPerson was
	 * not called
	 */
	@Test
	public void testGetPerson_whenFielsFirstNameOrLastNameIsEmpty_thenReturnEmptyFieldsException() {
		// GIVEN
		String firstName = "John";
		String lastName = "";
		// WHEN
		// THEN
		// verify that method getPerson was not called
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(EmptyFieldsException.class, () -> personServiceTest.getPerson(firstName, lastName));
	}

	/**
	 * Method that test addPerson when person to add not exist then return the
	 * person saved and verify if the method savePerson was called
	 */
	@Test
	public void testAddPerson_whenPersonToAddNotExist_thenVerifyIfPersonIsAdded() {
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

	/**
	 * Method that test addPerson when person to add exist then throw a
	 * {@link PersonAlreadyExistException}
	 */
	@Test
	public void testAddPerson_whenPersonToAddExist_thenThrowPersonAlreadyExistException() {
		// GIVEN
		Person personToAddAlreadyExist = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6512", "tenz@email.com");
		when(personDAOMock.getPersons()).thenReturn(mockList);
		// WHEN
		// THEN
		// verify that the method save was not called
		verify(personDAOMock, times(0)).save(anyInt(), any());
		assertThrows(PersonAlreadyExistException.class, () -> personServiceTest.addPerson(personToAddAlreadyExist));
	}

	/**
	 * Method that test updatePerson when person to update exist then return person
	 * Jonanathan Marrack with the field address updated and verify that the method
	 * save was called
	 */
	@Test
	public void testUpdatePerson_whenPersonExistFirstNameJonanthanLastNameMarrack_thenReturnPersonJonanathanMarrackWithTheFieldAdressUpdated() {
		// GIVEN
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		Person personToUpdate = new Person("Jonanathan", "Marrack", "30 rue des Ursulines", "Culver", "97451",
				"841-874-6513", "drk@email.com");
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personDAOMock.save(anyInt(), any())).thenReturn(personToUpdate);
		// WHEN
		Person resultPersonUpdated = personServiceTest.updatePerson(personToUpdate);
		// THEN
		verify(personDAOMock, times(1)).getPersons();
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).save(anyInt(), any());
		// the field address that was been modified has been updated
		assertEquals("30 rue des Ursulines", resultPersonUpdated.getAddress());
	}

	/**
	 * Method that test updatePerson when person to update not exist then throw a
	 * PersonNotFoundException and verify that the method save was not called
	 */
	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() {
		// GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451",
				"841-874-6513", "babar@email.com");
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(null);
		// WHEN
		// THEN
		// verify that the method save was not called
		verify(personDAOMock, times(0)).save(anyInt(), any());
		assertThrows(PersonNotFoundException.class, () -> personServiceTest.updatePerson(personToUpdateButNotExist));
	}

	/**
	 * Method that test deletePerson when person to delete exist then return a
	 * String containing "SUCCESS" and verify that the method delete was called
	 */
	@Test
	public void testDeletePerson_whenPersonToDeleteExist_thenReturnStringSUCCESS() {
		// GIVEN
		Person personToDeleted = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");
		String firstName = personToDeleted.getFirstName();
		String lastName = personToDeleted.getLastName();

		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personToDeleted);
		when(personDAOMock.delete(any())).thenReturn("SUCCESS");
		// WHEN
		String result = personServiceTest.deletePerson(firstName, lastName);
		// THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).delete(any());
		assertEquals("SUCCESS", result);
	}

	/**
	 * Method that test deletePerson when person to delete not exist then return a
	 * String containing "Person not Deleted" and verify that the method delete was
	 * not called
	 */
	@Test
	public void testDeletePerson_whenPersonNotExist_thenReturnAStringPersonNotDeleted() {
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
		assertEquals("Person not Deleted", resultMessage);
	}

	/**
	 * Method that test getEmailResidents when city exist then return the list of
	 * email and verify that list contained 4 elements
	 */
	@Test
	public void testGetEmailResidentsCityGiven_whenCityIsCulver_thenGetAllEmailsOfResidentsCulverCity() {
		// GIVEN
		String city = "Culver";
		when(personDAOMock.getPersons()).thenReturn(mockList);
		// WHEN
		List<String> listEmailResidentsCityCulver = personServiceTest.getEmailResidents(city);
		// THEN
		// the list obtained contain 4 elements
		verify(personDAOMock, times(1)).getPersons();
		assertEquals(4, listEmailResidentsCityCulver.size());
		assertEquals("jaboyd@email.com", listEmailResidentsCityCulver.get(0));
		assertEquals("drk@email.com", listEmailResidentsCityCulver.get(3));
	}

	/**
	 * Method that test getEmailResidents when city not exist then throw a
	 * {@link CityNotFoundException}
	 */
	@Test
	public void testGetEmailResidentsCityGiven_whenCityNotExistInArrayList_thenThrowACityNotFoundException() {
		// GIVEN
		String city = "Croix";
		// WHEN
		// THEN
		assertThrows(CityNotFoundException.class, () -> personServiceTest.getEmailResidents(city));
	}
}
