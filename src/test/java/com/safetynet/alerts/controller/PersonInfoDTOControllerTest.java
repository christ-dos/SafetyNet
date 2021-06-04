package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonInfoDTO;
import com.safetynet.alerts.service.PersonInfoDTOService;

/**
 * A class which test {@link PersonInfoDTOController}
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(PersonInfoDTOController.class)
@ExtendWith(MockitoExtension.class)
public class PersonInfoDTOControllerTest {
	
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcPersonInfoDTO;
	
	/**
	 * An instance of {@link PersonInfoDTOService}
	 * 
	 */
	@MockBean
	private PersonInfoDTOService personInfoDTOServiceMock;
	
	/**
	 * An instance of {@link PersonDAOMock}
	 * 
	 */
	@MockBean
	private PersonDAO personDAOMock;
	
	/**
	 * An instance of {@link medicalRecordDAOMock}
	 * 
	 */
	@MockBean
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * A mock of the arraysList of {@link Person}
	 */
	@Mock
	private List<Person> mockList;

	/**
	 * A mock of the arraysList of {@link MedicalRecord}
	 */
	@Mock
	private List<MedicalRecord> mockListMedicalRecord;
	
	/**
	 * An instance of {@link PersonByStationNumberDTO}
	 */
	private PersonInfoDTO mockPersonInfoDTO;
	
	/**
	 * Method that create a mocks of the ArrayLists mockList and
	 * mockListMedicalRecord 
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Tessa", "Carman","834 Binoc Ave","Culver","97451", "841-874-6512", "tenz@email.com");
		Person index3 = new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index3);

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Foster", "Shepard", "01/08/1980",
				new ArrayList<>(Arrays.asList()), new ArrayList<>());
		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		
		
	}
	/**
	 * Method that test GetPersonInfo when person exist fistName John and lastName Boyd
	 * then return informations of person: "John", "Boyd", "1509 Culver St", 37,"jaboyd@email.com" and medical history of person
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonIsJohnBoydAndExist_thenReturnPersonInfoDTOJohnBoyd() throws Exception {
		// GIVEN
		Person personJohnBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		Integer age = 37;
		mockPersonInfoDTO = new PersonInfoDTO(personJohnBoyd.getFirstName(), personJohnBoyd.getLastName(), personJohnBoyd.getAddress(), age, personJohnBoyd.getEmail(), new ArrayList<>(medicalRecordJohnBoyd.getMedications()), new ArrayList<>(medicalRecordJohnBoyd.getAllergies()));
		when(personInfoDTOServiceMock.getPersonInformationDTO(anyString(), anyString())).thenReturn(mockPersonInfoDTO);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personJohnBoyd);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		when(personDAOMock.getAge(anyString())).thenReturn(37);
		// WHEN
		// THEN
		mockMvcPersonInfoDTO.perform(get("/personinfo?firstName=John&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.address", is("1509 Culver St"))).andExpect(jsonPath("$.medication[0]", is("aznol:350mg")))
				.andExpect(jsonPath("$.allergies[0]", is("nillacilan")))
				.andExpect(jsonPath("$.age", is(37)))
				.andDo(print());
	}
	
	/**
	 * Method that test GetPersonInfo when person  not exist fistName Lily and lastName Sacha
	 * then throw a PersonNotFoundException
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonNotExist_thenThrowPersonNotFoundException() throws Exception {
		// GIVEN
		when(personInfoDTOServiceMock.getPersonInformationDTO(anyString(), anyString())).thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		mockMvcPersonInfoDTO.perform(get("/personinfo?firstName=Lily&lastName=Sacha")).andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
		.andExpect(result -> assertEquals("Person not found exception",
				result.getResolvedException().getMessage()))
		.andDo(print());
	}
}
