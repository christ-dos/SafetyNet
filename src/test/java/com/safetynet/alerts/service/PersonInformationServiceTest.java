package com.safetynet.alerts.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.DisplayPartialPerson;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.DateUtils;

/**
 * Class that test the PersonInfoDTOService class
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class PersonInformationServiceTest {
	/**
	 * An instance of {@link PersonInformationService}
	 */
	private PersonInformationService personInformationService;
	
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
	 * A mock of {@link FireStationDAO}
	 */
	@Mock
	private FireStationDAO fireStationDAOmock;
	
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
	 * A mock of {@link DateUtils}
	 */
	@Mock
	private DateUtils dateUtilsMock;
	
	/**
	 * A mock of the arraysList of {@link FireStation}
	 */
	@Mock
	private List<FireStation> mockListFireStation;

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
	 * A mock of the arraysList of String containing Addresses of person covered by Station number
	 */
	@Mock
	private List<String> mockListAddress;
	
	/**
	 * Method that create a mocks of the ArrayLists mockListAddress, mockListFireStation, mockList,
	 * mockListMedicalRecord 
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListAddress = new ArrayList<>();
		mockListAddress.add("1509 Culver St");
		mockListAddress.add("834 Binoc Ave");
		mockListAddress.add("748 Townings Dr");
		
		mockListFireStation = new ArrayList<>();
		FireStation fireStationIndex0 = new FireStation("3", "1509 Culver St");
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		FireStation fireStationIndex2 = new FireStation("3", "834 Binoc Ave");
		FireStation fireStationIndex3 = new FireStation("3", "748 Townings Dr");
		mockListFireStation.add(fireStationIndex0);
		mockListFireStation.add(fireStationIndex1);
		mockListFireStation.add(fireStationIndex2);
		mockListFireStation.add(fireStationIndex3);

		mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Tessa", "Carman","834 Binoc Ave","Culver","97451", "841-874-6512", "tenz@email.com");
		Person index3 = new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		Person index4 = new Person("Jacob", "Boyd","1509 Culver St","Culver","97451", "841-874-6512"
				, "drk@email.com" );
		Person index5 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index6 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person index7 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index3);
		mockList.add(index4);
		mockList.add(index5);
		mockList.add(index6);
		mockList.add(index7);

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Foster", "Shepard", "01/08/1980",
				new ArrayList<>(Arrays.asList()), new ArrayList<>());
		MedicalRecord indexMRecord3 = new MedicalRecord("Jacob", "Boyd", "03/06/1989", new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord4 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord5 = new MedicalRecord("Roger", "Boyd", "09/06/2017",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord6 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));
		
		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		mockListMedicalRecord.add(indexMRecord3);
		mockListMedicalRecord.add(indexMRecord4);
		mockListMedicalRecord.add(indexMRecord5);
		mockListMedicalRecord.add(indexMRecord6);

		personInformationService = PersonInformationService.builder()
				.medicalRecordService(medicalRecordServiceMock)
				.personService(personServiceMock)
				.fireStationDAO(fireStationDAOmock)
				.personDAO(personDAOMock)
				.medicalRecordDAO(medicalRecordDAOMock)
				.build();
	}
	
	/**
	 * Method that test getListPersonsCoveredByFireStation
	 * when fireStation exits then return a list of address covered by fireSation number
	 */
	@Test
	public void testgetListPersonsCoveredByFireStation_whenStationNumberFireStationExist_thenReturnListPersonsAdultsAndChilds() {
		// GIVEN
		
		String stationNumber = "3";
		DisplayPartialPerson expectedJohnBoyd = new DisplayPartialPerson("John", "Boyd", "1509 Culver St", "841-874-6512");
		DisplayPartialPerson expectedFoster = new DisplayPartialPerson("Foster", "Shepard", "748 Townings Dr", "841-874-6544");
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(stationNumber)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockList)).thenReturn(mockListMedicalRecord);
		//when(dateUtilsMock.getAge(anyString())).thenReturn(37, 9, 41);
		// WHEN
		PersonsCoveredByStation PersonsCovededByStationThree = personInformationService
				.getPersonCoveredByFireStation(stationNumber);
		// THEN
		// verify that the list contained 3 elements of personDTO
		assertEquals(3, PersonsCovededByStationThree.getListPersonDTO().size());
		//verify the list contain in index 0 John Boyd
		assertEquals(expectedJohnBoyd, PersonsCovededByStationThree.getListPersonDTO().get(0));
		assertEquals(expectedFoster, PersonsCovededByStationThree.getListPersonDTO().get(2));
		assertEquals(2, PersonsCovededByStationThree.getAdultsCounter());
		assertEquals(1, PersonsCovededByStationThree.getChildsCounter());
	}
	/**
	 * Method that test getListPersonsCoveredByFireStation
	 * when station number is five and not exist
	 * then throw a FireStationNotFoundException
	 */
	@Test
	public void testgetListPersonsCoveredByFireStation_whenStationNumberNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		String station = "5";
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(anyString())).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(FireStationNotFoundException.class,
				() -> personInformationService.getPersonCoveredByFireStation(station));
	}
	
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation
	 * when fireStation exits then return a list of address covered by fireSation number
	 */
	@Test
	public void testgetPhoneAlertResidentsCoveredByStation_whenStationNumberFireStationExist_thenReturnListOfPhoneOfPersonCoveredByStation() {
		// GIVEN
		
		String fireStation = "3";
		DisplayPartialPerson expectedJohnBoyd = new DisplayPartialPerson("John", "Boyd", "1509 Culver St", "841-874-6512");
		DisplayPartialPerson expectedFoster = new DisplayPartialPerson("Foster", "Shepard", "748 Townings Dr", "841-874-6544");
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(fireStation)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		// WHEN
		List<String> phoneAlertPersonCovededByStationThree = personInformationService
				.getPhoneAlertResidentsCoveredByStation(fireStation);
		// THEN
		// verify that the list contained 3 elements of personDTO
		assertEquals(3, phoneAlertPersonCovededByStationThree.size());
		//verify the list contain in index 0 John Boyd
		assertEquals(expectedJohnBoyd.getPhone(), phoneAlertPersonCovededByStationThree.get(0));
		assertEquals(expectedFoster.getPhone(), phoneAlertPersonCovededByStationThree.get(2));
	}
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation
	 * when station number is five and not exist
	 * then throw a FireStationNotFoundException
	 */
	@Test
	public void testgetPhoneAlertResidentsCoveredByStation_whenStationNumberNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		String fireStation = "5";
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(anyString())).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(FireStationNotFoundException.class,
				() -> personInformationService.getPhoneAlertResidentsCoveredByStation(fireStation));
	}

	
	/**
	 * Method that test GetPersonInformationDTO with firstName John and LastName Boyd when
	 * return the person informations,age is 37 and two arrayLists with the medication and allergies forJohn Boyd
	 * 
	 */
	@Test
	public void testGetPersonInformation_whenfirstNameIsJohnAndLastNameIsBoyd_thenReturnFirstNameLastNameAddressAgeEmailAndMedicalHistoryOfPeson() {
		//GIVEN
		Person personExpected = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personExpected);
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		//WHEN
		PersonInfoDisplaying resultInformationPerson = personInformationService.getPersonInformation(personExpected.getFirstName(), personExpected.getLastName());
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
	public void testGetPersongetPersonInformation__whenInputPersonNotExist_resultThrowPersonNotFoundException() {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		when(personServiceMock.getPerson(firstName, lastName)).thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		assertThrows(PersonNotFoundException.class, () -> personInformationService.getPersonInformation(firstName, lastName));
	}
	
	@Test
	public void testGetChildAlertList_whenAddressExist_thenReturnListOfChildAndListOfAdultsLivingInSameAddress() {
		//GIVEN
		String address = "1509 Culver St";
		when(personDAOMock.getPersons()).thenReturn(mockList);
		//WHEN
		ChildAlertDisplaying childAlertResult = personInformationService.getChildAlertList(address);
		//THEN
		assertEquals("Tenley", childAlertResult.getListChild().get(0).getFirstName());
		assertEquals("Boyd", childAlertResult.getListChild().get(0).getLastName());
	}
	
}
