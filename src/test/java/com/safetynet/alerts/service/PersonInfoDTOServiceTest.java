package com.safetynet.alerts.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfoDTO;

/**
 * Class that test the PersonInfoDTOService class
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class PersonInfoDTOServiceTest {
	/**
	 * An instance of {@link PersonInfoDTOService}
	 */
	private IPersonInfoDTOService personInfoDTOService;
	
	/**
	 * A mock of {@link PersonDAO}
	 */
	@Mock
	private PersonDAO personDAOMock;
	
	/**
	 * A mock of {@link PersonService}
	 */
	@Mock
	private PersonService personServiceMock;

	/**
	 * A mock of {@link MedicalRecordDAO}
	 */
	@Mock
	private MedicalRecordService medicalRecordServiceMock;
	
	/**
	 * Method that initialize the instance of personInfoDTOService
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		personInfoDTOService = PersonInfoDTOService.builder()
				.medicalRecordService(medicalRecordServiceMock)
				.personDAO(personDAOMock)
				.personService(personServiceMock)
				.build();
	}
	
	/**
	 * Method that test GetPersonInformationDTO with firstName John and LastName Boyd when
	 * return the person informations,age is 37 and two arrayLists with the medication and allergies forJohn Boyd
	 * 
	 */
	@Test
	public void testGetPersonInformationDTO_whenfirstNameIsJohnAndLastNameIsBoyd_thenReturnFirstNameLastNameAddressAgeEmailAndMedicalHistoryOfPeson() {
		//GIVEN
		Person personExpected = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personExpected);
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		when(personDAOMock.getAge(anyString())).thenReturn(37);
		//WHEN
		PersonInfoDTO resultInformationPerson = personInfoDTOService.getPersonInformationDTO(personExpected.getFirstName(), personExpected.getLastName());
		//THEN
		assertEquals("John", resultInformationPerson.getFirstName());
		assertEquals("Boyd", resultInformationPerson.getLastName());
		assertEquals(37, resultInformationPerson.getAge());
		assertEquals("nillacilan", resultInformationPerson.getAllergies().get(0));
		assertEquals("aznol:350mg", resultInformationPerson.getMedication().get(0));
	}
	
	/**
	 * Method that test getPersonInformationDTO when person not exist then should throw a
	 * {@link PersonNotFoundException} and verify that the method  in DAO was not
	 * called
	 */
	@Test
	public void testGetPersongetPersonInformationDTO__whenInputPersonNotExist_resultThrowPersonNotFoundException() {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		when(personServiceMock.getPerson(firstName, lastName)).thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		// verify that method getPerson in DAO was not called
		verify(personDAOMock, times(0)).getPerson(anyString(), anyString());
		assertThrows(PersonNotFoundException.class, () -> personInfoDTOService.getPersonInformationDTO(firstName, lastName));
	}
}
