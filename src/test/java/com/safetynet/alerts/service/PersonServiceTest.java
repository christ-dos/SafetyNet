package com.safetynet.alerts.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Class service that process the request of controller
 * @author Chrsitine Duarte
 *
 */
@WebMvcTest(PersonService.class)
@Data
@AutoConfigureMockMvc
@Slf4j
public class PersonServiceTest {

	//@Autowired
	//private MockMvc mockMvc;
	
	@Autowired
	private IPersonService personServiceTest;
	
	@MockBean
	private IPersonDAO personDAOMock;
	
	
	
	@Test
	public void testGetPerson_whenInputExistWithFirstNameJohnAndLastNameBoyd_resultShouldGetObjectPersonJohnBoyd() throws EmptyFieldsException {
		//GIVEN
		Person personInput = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personInput.getFirstName();
		String lastName = personInput.getLastName();
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personInput);
		//WHEN
		Person resultPersonGetted = personServiceTest.getPerson(firstName,lastName);
		//THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		assertNotNull(resultPersonGetted);
		assertSame(personInput, resultPersonGetted);
	}
	
	@Test
	public void testGetPerson_whenInputPersonNotExist_resultShouldGiveNull() throws EmptyFieldsException {
		//GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		//WHEN
		 Person resultNullExpected = personServiceTest.getPerson(firstName, lastName);
		//THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		assertNull(resultNullExpected);
	}
	
	@Test
	public void testGetPerson_whenFielsFirstNameOrLastNameIsNull_thenReturnNullPonterException() {
		//GIVEN
		String firstName = "John";
		String lastName = "";
		//WHEN
		
		//THEN
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(EmptyFieldsException.class, ()-> personServiceTest.getPerson(firstName, lastName));
	}
	
	@Test
	public void testAddPerson_whenPersonToAddNotExist_thenVerifyIfPersonIsAdded() throws EmptyFieldsException{
		@SuppressWarnings("unchecked")
		//GIVEN
		ArrayList<Person> mockList = mock(ArrayList.class);
		Person personToAdd = new Person("Jojo", "Dupond", "1509 rue des fleurs","Roubaix","59100","000-000-0012","jojod@email.com");
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(mockList.size()).thenReturn(23);
		when(mockList.indexOf(personToAdd)).thenReturn(-1);
		when(personDAOMock.save(23, personToAdd)).thenReturn(personToAdd);
		//WHEN
		Person resultAfterAddPerson = personServiceTest.addPerson(personToAdd);
		//THEN
		verify(personDAOMock, times(1)).getPersons();
		verify(personDAOMock, times(1)).save(23, personToAdd);
		assertSame(personToAdd, resultAfterAddPerson);
		assertEquals(personToAdd.getEmail(), resultAfterAddPerson.getEmail());
		assertEquals(personToAdd.getFirstName(), resultAfterAddPerson.getFirstName());
	}
	
	@Test
	public void testAddPerson_whenPersonToAddExist_thenVerifyThatSavedIsNotCalled() throws EmptyFieldsException {
		@SuppressWarnings("unchecked")
		//GIVEN
		ArrayList<Person> mockList = mock(ArrayList.class);
		Person personToAddAreadyExist = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
		
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(mockList.size()).thenReturn(23);
		when(mockList.indexOf(personToAddAreadyExist)).thenReturn(2);
		when(personDAOMock.getPersons()).thenReturn(mockList);
		//WHEN
		Person resultPersonAlreadyExist = personServiceTest.addPerson(personToAddAreadyExist);
		//THEN
		verify(personDAOMock,times(1)).getPersons();
		//verify that save was not called
		verify(personDAOMock, times(0)).save(2, personToAddAreadyExist);
		assertNull(resultPersonAlreadyExist);
	}
	
	@Test 
	public void testUpdatePerson_whenPersonExistAndAllfirldsWereModified_thenReturnPersonWithAllFieldsUpdated() throws EmptyFieldsException {
		//GIVEN
		List <Person> myListPersons = personDAOMock.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "NewYork", "97450", "841-874-6512", "jojo@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPersons()).thenReturn(myListPersons);
		when(personDAOMock.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = personServiceTest.updatePerson(personToUpdate);
		//THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).update(index, personToUpdate);
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
		List <Person> myListPersons = personDAOMock.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "30 rue des UrsulinesS", "Culver", "97451", "841-874-6513", "drk@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(personDAOMock.getPersons()).thenReturn(myListPersons);
		when(personDAOMock.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = personServiceTest.updatePerson(personToUpdate);
		//THEN
		verify(personDAOMock, times(1)).getPerson(anyString(), anyString());
		verify(personDAOMock, times(1)).update(index, personToUpdate);
		
		// the field address that was been modified has been updated
		assertSame(resultPersonUpdated.getAddress(), resultPersonUpdated.getAddress());
	}
	
	@Test
	public void testUpdatePerson_whenPersonToUpdateNotExist_thenReturnPersonNotFoundException() {
		//GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513", "babar@email.com" );
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(null);
		//WHEN
		
		//THEN
		assertThrows(PersonNotFoundException.class, ()-> personServiceTest.updatePerson(personToUpdateButNotExist));
	}
	
	@Test
	public void testDeleteService_whenPersonExist() {
		//GIVEN
		//PersonDAO personDAO = mock(PersonDAO.class);
		Person personToDeleted = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personToDeleted.getFirstName();
		String lastName = personToDeleted.getLastName();
		
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personToDeleted);
		when(personDAOMock.delete(personToDeleted)).thenReturn("SUCESS");
		//WHEN
		personServiceTest.deletePerson(firstName, lastName);
		//THEN
		verify(personDAOMock, times(1)).getPerson(firstName, lastName);
		verify(personDAOMock, times(1)).delete(personToDeleted);
		
	}
 
	
	
	
}
