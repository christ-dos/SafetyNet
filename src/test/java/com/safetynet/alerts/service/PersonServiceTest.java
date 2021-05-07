package com.safetynet.alerts.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

import lombok.Data;

/**
 * Class service that process the request of controller
 * @author Chrsitine Duarte
 *
 */
@WebMvcTest(PersonService.class)
@Data
@AutoConfigureMockMvc
public class PersonServiceTest {

	//@Autowired
	//private MockMvc mockMvc;
	
	@Autowired
	private IPersonService iPersonService;
	
	@MockBean
	private IPersonDAO iPersonDAO;
	
	
	
	@Test
	public void testGetPerson_whenInputExistWithFirstNameJohnAndLastNameBoyd_resultShouldGetObjectPersonJohnBoyd() throws EmptyFieldsException {
		//GIVEN
		Person personInput = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personInput.getFirstName();
		String lastName = personInput.getLastName();
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(personInput);
		//WHEN
		Person resultPersonGetted = iPersonService.getPerson(firstName,lastName);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(anyString(), anyString());
		assertNotNull(resultPersonGetted);
		assertSame(personInput, resultPersonGetted);
	}
	
	@Test
	public void testGetPerson_whenInputPersonNotExist_resultShouldGiveNull() throws EmptyFieldsException {
		//GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		//WHEN
		 Person resultNullExpected = iPersonService.getPerson(firstName, lastName);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(anyString(), anyString());
		assertNull(resultNullExpected);
	}
	
	@Test
	public void testGetPerson_whenFielsFirstNameOrLastNameIsNull_thenReturnNullPonterException() {
		//GIVEN
		String firstName = "John";
		String lastName = "";
		//WHEN
		
		//THEN
		verify(iPersonDAO, times(0)).getPerson(anyString(), anyString());
		assertThrows(EmptyFieldsException.class, ()-> iPersonService.getPerson(firstName, lastName));
	}
	
	@Test
	public void testAddPerson_whenPersonToAddNotExist_thenVerifyIfPersonIsAdded() throws EmptyFieldsException {
		//GIVEN
		Person personToAdd = new Person("Jojo", "Dupond", "1509 rue des fleurs","Rouvbaix","59100","000-000-0012","jojod@email.com");
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(null);
		when(iPersonDAO.save(any())).thenReturn(personToAdd);
		//WHEN
		Person resultAfterAddPerson = iPersonService.addPerson(personToAdd);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(anyString(), anyString());
		verify(iPersonDAO, times(1)).save(any());
		assertSame(personToAdd, resultAfterAddPerson);
		assertEquals(personToAdd.getEmail(), resultAfterAddPerson.getEmail());
		assertEquals(personToAdd.getFirstName(), resultAfterAddPerson.getFirstName());
	}
	
	@Test
	public void testAddPerson_whenPersonToAddExist_thenVerifyIfMethodUpdateIsCalled() throws EmptyFieldsException {
		//GIVEN
		List <Person> myListPersons = iPersonDAO.getPersons();
		Person personRecordedInArray = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
		Person personToAddThatExist = new Person("Tenley", "Boyd", "15 NouvelleAdresse", "Culver", "97451", "841-874-6512", "tenz@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(iPersonDAO.getPersons()).thenReturn(myListPersons);
		when(iPersonDAO.update(index, personToAddThatExist)).thenReturn(personToAddThatExist);
		//WHEN
		Person resultPersonExistAdded = iPersonService.addPerson(personToAddThatExist);
		//THEN
		//method getPerson is called 2 times, one time in method save and one time in method update
		verify(iPersonDAO, times(2)).getPerson(anyString(), anyString());
		verify(iPersonDAO, times(1)).update(index, personToAddThatExist);
		// the filed address was been updated in arrayList because person already exist
		assertSame(personRecordedInArray.getAddress(), resultPersonExistAdded.getAddress());
	}
	
	@Test 
	public void testUpdatePerson_whenPersonExistAndAllfirldsWereModified_thenReturnPersonWithAllFieldsUpdated() throws EmptyFieldsException {
		//GIVEN
		List <Person> myListPersons = iPersonDAO.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "NewYork", "97450", "841-874-6512", "jojo@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(iPersonDAO.getPersons()).thenReturn(myListPersons);
		when(iPersonDAO.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = iPersonService.updatePerson(personToUpdate);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(anyString(), anyString());
		verify(iPersonDAO, times(1)).update(index, personToUpdate);
		//all fields modified was been updated in arrayList
		assertSame(personRecordedInArray.getAddress(), resultPersonUpdated.getAddress());
		assertSame(personRecordedInArray.getCity(), resultPersonUpdated.getCity());
		assertSame(personRecordedInArray.getZip(), resultPersonUpdated.getZip());
		assertSame(personRecordedInArray.getEmail(), resultPersonUpdated.getEmail());
		assertSame(personRecordedInArray.getPhone(), resultPersonUpdated.getPhone());
	}
	
	@Test
	public void testUpdatePerson_whenPersonExistFirstNameJonanthanLastNameMarrack_thenReturnPersonJonanathanMarrackWithTheFieldAdressUpdated() throws EmptyFieldsException {
		//GIVEN
		List <Person> myListPersons = iPersonDAO.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "30 rue des UrsulinesS", "Culver", "97451", "841-874-6513", "drk@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(iPersonDAO.getPersons()).thenReturn(myListPersons);
		when(iPersonDAO.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = iPersonService.updatePerson(personToUpdate);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(anyString(), anyString());
		verify(iPersonDAO, times(1)).update(index, personToUpdate);
		
		// the field address that was been modified has been updated
		assertSame(resultPersonUpdated.getAddress(), resultPersonUpdated.getAddress());
	}
	
	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() {
		//GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513", "babar@email.com" );
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(null);
		//WHEN
		
		//THEN
		assertThrows(PersonNotFoundException.class, ()-> iPersonService.updatePerson(personToUpdateButNotExist));
	}
	
	@Test
	public void testDeleteService_whenPersonExist() {
		//GIVEN
		PersonDAO personDAO = mock(PersonDAO.class);
		Person personToDeleted = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personToDeleted.getFirstName();
		String lastName = personToDeleted.getLastName();
		
		when(iPersonDAO.getPerson(anyString(), anyString())).thenReturn(personToDeleted);
		doNothing().doCallRealMethod().when(personDAO).delete(personToDeleted);
		//WHEN
		iPersonService.deletePerson(firstName, lastName);
		//THEN
		verify(iPersonDAO, times(1)).getPerson(firstName, lastName);
		verify(iPersonDAO, times(1)).delete(personToDeleted);
		
	}
 
	
	
	
}
