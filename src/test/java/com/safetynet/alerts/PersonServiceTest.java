package com.safetynet.alerts;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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
import com.safetynet.alerts.DAO.ReadFileJson;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.PersonService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@WebMvcTest(PersonService.class)
@Data
@Slf4j
@AutoConfigureMockMvc
public class PersonServiceTest {

	//@Autowired
	//private MockMvc mockMvc;
	
	@Autowired
	private IPersonService iPersonService;
	
	@MockBean
	private IPersonDAO ipersonDAO;
	
	@Autowired
	private ReadFileJson readFileJson;
	
	//private Person person;
	
	
	@Test
	public void testGetPerson_whenInputExistWithFirstNameJohnAndLastNameBoyd_resultShouldGetObjectPersonJohnBoyd() {
		//GIVEN
		Person personInput = new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
		String firstName = personInput.getFirstName();
		String lastName = personInput.getLastName();
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(personInput);
		//WHEN
		Person resultPersonGetted = iPersonService.getPerson(firstName,lastName);
		//THEN
		verify(ipersonDAO, times(1)).getPerson(anyString(), anyString());
		assertNotNull(resultPersonGetted);
		assertSame(personInput, resultPersonGetted);
	}
	
	@Test
	public void testGetPerson_whenInputNotExist_resultShouldGiveNull() {
		//GIVEN
		String firstName = "Lubin";
		String lastName = "Dupond";
		//WHEN
		 Person resultNullExpected = iPersonService.getPerson(firstName, lastName);
		//THEN
		verify(ipersonDAO, times(1)).getPerson(anyString(), anyString());
		assertNull(resultNullExpected);
	}
	
	@Test
	public void testGetPerson_whenFielsFirstNameAndLastNameIsNull_thenReturnNullPonterException() {
		//GIVEN
		String firstName = "";
		String lastName = "";
		//WHEN
		
		//THEN
		verify(ipersonDAO, times(0)).getPerson(anyString(), anyString());
		assertThrows(NullPointerException.class, ()-> iPersonService.getPerson(firstName, lastName));
	}
	
	@Test
	public void testAddPerson_whenPersonToAddNotExist_thenVerifyIfPersonIsAdded() {
		//GIVEN
		Person personToAdd = new Person("Jojo", "Dupond", "1509 rue des fleurs","Rouvbaix","59100","000-000-0012","jojod@email.com");
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(null);
		when(ipersonDAO.save(any())).thenReturn(personToAdd);
		//WHEN
		Person resultAfterAddPerson = iPersonService.addPerson(personToAdd);
		//THEN
		//verify(personDAO, times(1)).getPerson(anyString(), anyString());
		verify(ipersonDAO, times(1)).save(any());
		assertSame(personToAdd, resultAfterAddPerson);
		assertEquals(personToAdd.getEmail(), resultAfterAddPerson.getEmail());
		assertEquals(personToAdd.getFirstName(), resultAfterAddPerson.getFirstName());
	}
	
	@Test
	public void testAddPerson_whenPersonToAddExist_thenVerifyIfMethodUpdateIsCalled() {
		//GIVEN
		List <Person> myListPersons = readFileJson.getPersons();
		Person personRecordedInArray = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
		Person personToAddThatExist = new Person("Tenley", "Boyd", "15 NouvelleAdresse", "Culver", "97451", "841-874-6512", "tenz@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(ipersonDAO.getPersons()).thenReturn(myListPersons);
		when(ipersonDAO.update(index, personToAddThatExist)).thenReturn(personToAddThatExist);
		//WHEN
		Person resultPersonExistAdded = iPersonService.addPerson(personToAddThatExist);
		//THEN
		verify(ipersonDAO, times(2)).getPerson(anyString(), anyString());
		verify(ipersonDAO, times(1)).update(index, personToAddThatExist);
		assertNotEquals("1509 Culver St", resultPersonExistAdded.getAddress());
	}
	
	@Test
	public void testUpdatePerson_whenPersonExistFirstNameJonanthanLastNameMarrack_thenReturnPersonWithAdressandPhoneAndEmailUpdated() {
		//GIVEN
		List <Person> myListPersons = readFileJson.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "15 NouvelleAdresse", "Culver", "97451", "841-874-6512", "jojo@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(ipersonDAO.getPersons()).thenReturn(myListPersons);
		when(ipersonDAO.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = iPersonService.updatePerson(personToUpdate);
		//THEN
		verify(ipersonDAO, times(1)).getPerson(anyString(), anyString());
		verify(ipersonDAO, times(1)).update(index, personToUpdate);
		
		assertNotSame(resultPersonUpdated, personRecordedInArray);
		assertNotSame("29 15th St", resultPersonUpdated.getAddress());
		assertNotSame("drk@email.com", resultPersonUpdated.getEmail());
		assertNotSame("841-874-6513", resultPersonUpdated.getPhone());
		
		assertSame("Culver", resultPersonUpdated.getCity());
	}
	@Test
	public void testUpdatePerson_whenPersonExistFirstNameJonanthanLastNameMarrack_thenReturnPersonJonanathanMarrackWithTheFieldAdressModified() {
		//GIVEN
		List <Person> myListPersons = readFileJson.getPersons();
		Person personRecordedInArray = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513", "drk@email.com" );
		Person personToUpdate = new Person("Jonanathan", "Marrack", "30 rue des UrsulinesS", "Culver", "97451", "841-874-6513", "drk@email.com");
		int index = myListPersons.indexOf(personRecordedInArray);
		
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(personRecordedInArray);
		when(ipersonDAO.getPersons()).thenReturn(myListPersons);
		when(ipersonDAO.update(index, personToUpdate)).thenReturn(personToUpdate);
		//WHEN
		Person resultPersonUpdated = iPersonService.updatePerson(personToUpdate);
		//THEN
		verify(ipersonDAO, times(1)).getPerson(anyString(), anyString());
		verify(ipersonDAO, times(1)).update(index, personToUpdate);
		
		assertNotSame(resultPersonUpdated, personRecordedInArray);
		assertNotSame("29 15th St", resultPersonUpdated.getAddress());
		
		assertSame("drk@email.com", resultPersonUpdated.getEmail());
		assertSame("841-874-6513", resultPersonUpdated.getPhone());
		assertSame("Culver", resultPersonUpdated.getCity());
	}
	
	@Test
	public void testUpdatePerson_whenPersonToUpdatedIsNull_thenReturnPersonNotFoundException() {
		//GIVEN
		Person personToUpdateButNotExist = new Person("Babar", "Elephant", "29 15th St", "Culver", "97451", "841-874-6513", "babar@email.com" );
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(null);
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
		
		when(ipersonDAO.getPerson(anyString(), anyString())).thenReturn(personToDeleted);
		doNothing().doCallRealMethod().when(personDAO).delete(personToDeleted);
		//WHEN
		iPersonService.deletePerson(firstName, lastName);
		//THEN
		verify(ipersonDAO, times(1)).getPerson(firstName, lastName);
		verify(ipersonDAO, times(1)).delete(personToDeleted);
		
	}
 
	
	
	
}
