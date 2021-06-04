package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
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
	 * A mock of {@link MedicalRecordDAO}
	 */
	@Mock
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * Method that test GetPersonInformationDTO with firstName John and LastName Boyd when
	 * return the person informations,age is 37 and two arrayLists with the medication and allergies forJohn Boyd
	 * 
	 */
	@Test
	public void testGetPersonInformationDTO_whenfirstNameIsJohnAndLastNameIsBoyd_thenReturnFirstNameLastNameAddressAgeEmailAndMedicalHistoryOfPeson() {
		//GIVEN
		personInfoDTOService = PersonInfoDTOService.builder()
				.medicalRecordDAO(medicalRecordDAOMock)
				.personDAO(personDAOMock).build();
		Person personExpected = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personExpected);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		when(personDAOMock.getAge(anyString())).thenReturn(37);
		//WHEN
		PersonInfoDTO resultInformationPerson = personInfoDTOService.getPersonInformationDTO(personExpected.getFirstName(), personExpected.getLastName());
		//THEN
		assertEquals("John", resultInformationPerson.getFirstName());
		assertEquals("Boyd", resultInformationPerson.getLastName());
		assertEquals(37, resultInformationPerson.getAge());
		assertEquals("nillacilan", resultInformationPerson.getAllergies().get(0));
		assertEquals("aznol:350mg", resultInformationPerson.getMedications().get(0));
	}
}
