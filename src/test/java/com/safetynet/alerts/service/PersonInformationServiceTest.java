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
import com.safetynet.alerts.DTO.PersonChildAlertDisplaying;
import com.safetynet.alerts.DTO.PersonCoveredByStation;
import com.safetynet.alerts.DTO.PersonCoveredByStationDisplaying;
import com.safetynet.alerts.DTO.PersonFireDisplaying;
import com.safetynet.alerts.DTO.PersonFlood;
import com.safetynet.alerts.DTO.PersonFloodDisplaying;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.exceptions.AddressNotFoundException;
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
	private FireStationDAO fireStationDAOMock;

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
	 * A mock of the arraysList of String containing Addresses of person covered by
	 * Station number
	 */
	@Mock
	private List<String> mockListAddress;

	/**
	 * Method that create a mocks of the ArrayLists mockListAddress,
	 * mockListFireStation, mockList, mockListMedicalRecord
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListAddress = new ArrayList<>();
		mockListAddress.add("29 15th St");
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
		Person index1 = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person index2 = new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		Person index3 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"drk@email.com");
		Person index4 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index5 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		Person index6 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index2);
		mockList.add(index3);
		mockList.add(index4);
		mockList.add(index5);
		mockList.add(index6);

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Foster", "Shepard", "01/08/1980",
				new ArrayList<>(Arrays.asList()), new ArrayList<>());
		MedicalRecord indexMRecord3 = new MedicalRecord("Jacob", "Boyd", "03/06/1989",
				new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord4 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord5 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord6 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));

		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		mockListMedicalRecord.add(indexMRecord3);
		mockListMedicalRecord.add(indexMRecord4);
		mockListMedicalRecord.add(indexMRecord5);
		mockListMedicalRecord.add(indexMRecord6);

		personInformationService = PersonInformationService.builder().medicalRecordService(medicalRecordServiceMock)
				.personService(personServiceMock).fireStationDAO(fireStationDAOMock).personDAO(personDAOMock)
				.medicalRecordDAO(medicalRecordDAOMock).build();
	}

	/**
	 * Method that test getListPersonsCoveredByFireStation when fireStation exits
	 * then return a list of address covered by fireSation number
	 */
	@Test
	public void testgetListPersonsCoveredByFireStation_whenStationNumberFireStationExist_thenReturnListPersonsAdultsAndChilds() {
		// GIVEN
		String stationNumber = "3";
		PersonCoveredByStation expectedJohnBoyd = new PersonCoveredByStation("John", "Boyd", "1509 Culver St",
				"841-874-6512");
		PersonCoveredByStation expectedFoster = new PersonCoveredByStation("Foster", "Shepard", "748 Townings Dr",
				"841-874-6544");
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(stationNumber)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockList)).thenReturn(mockListMedicalRecord);
		// WHEN
		PersonCoveredByStationDisplaying PersonsCovededByStationThree = personInformationService
				.getPersonCoveredByFireStation(stationNumber);
		// THEN
		// verify that the list contained 7 elements of personDTO
		assertEquals(7, PersonsCovededByStationThree.getListPartialPersons().size());
		// verify the list contain in index 0 John Boyd
		assertEquals(expectedJohnBoyd, PersonsCovededByStationThree.getListPartialPersons().get(0));
		assertEquals(expectedFoster, PersonsCovededByStationThree.getListPartialPersons().get(2));
		assertEquals(4, PersonsCovededByStationThree.getAdultsCounter());
		assertEquals(3, PersonsCovededByStationThree.getChildsCounter());
	}

	/**
	 * Method that test getListPersonsCoveredByFireStation when station number is
	 * five and not exist then throw a FireStationNotFoundException
	 */
	@Test
	public void testgetListPersonsCoveredByFireStation_whenStationNumberNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		String station = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(anyString())).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(FireStationNotFoundException.class,
				() -> personInformationService.getPersonCoveredByFireStation(station));
	}

	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when fireStation
	 * exits then return a list of address covered by fireSation number
	 */
	@Test
	public void testgetPhoneAlertResidentsCoveredByStation_whenStationNumberFireStationExist_thenReturnListOfPhoneOfPersonCoveredByStation() {
		// GIVEN

		String fireStation = "3";
		PersonCoveredByStation expectedJohnBoyd = new PersonCoveredByStation("John", "Boyd", "1509 Culver St",
				"841-874-6512");
		PersonCoveredByStation expectedFoster = new PersonCoveredByStation("Foster", "Shepard", "748 Townings Dr",
				"841-874-6544");
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(fireStation)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		// WHEN
		List<String> phoneAlertPersonCovededByStationThree = personInformationService
				.getPhoneAlertResidentsCoveredByStation(fireStation);
		// THEN
		// verify that the list contained 7 elements of personDTO
		assertEquals(7, phoneAlertPersonCovededByStationThree.size());
		// verify the list contain in index 0 John Boyd
		assertEquals(expectedJohnBoyd.getPhone(), phoneAlertPersonCovededByStationThree.get(0));
		assertEquals(expectedFoster.getPhone(), phoneAlertPersonCovededByStationThree.get(2));
	}

	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when station number
	 * is five and not exist then throw a FireStationNotFoundException
	 */
	@Test
	public void testgetPhoneAlertResidentsCoveredByStation_whenStationNumberNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		String fireStation = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(anyString())).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(FireStationNotFoundException.class,
				() -> personInformationService.getPhoneAlertResidentsCoveredByStation(fireStation));
	}

	/**
	 * Method that test GetPersonInformationDTO with firstName John and LastName
	 * Boyd when return the person informations,age is 37 and two arrayLists with
	 * the medication and allergies forJohn Boyd
	 * 
	 */
	@Test
	public void testGetPersonInformation_whenfirstNameIsJohnAndLastNameIsBoyd_thenReturnFirstNameLastNameAddressAgeEmailAndMedicalHistoryOfPeson() {
		// GIVEN
		Person personExpected = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(personExpected);
		when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		// WHEN
		PersonInfoDisplaying resultInformationPerson = personInformationService
				.getPersonInformation(personExpected.getFirstName(), personExpected.getLastName());
		// THEN
		assertEquals("John", resultInformationPerson.getFirstName());
		assertEquals("Boyd", resultInformationPerson.getLastName());
		assertEquals(37, resultInformationPerson.getAge());
		assertEquals("nillacilan", resultInformationPerson.getAllergies().get(0));
		assertEquals("aznol:350mg", resultInformationPerson.getMedication().get(0));
	}

	/**
	 * Method that test getPersonInformationDTO when person not exist then should
	 * throw a {@link PersonNotFoundException} and verify that the method in DAO was
	 * not called
	 */
	@Test
	public void testGetPersonInformation__whenInputPersonNotExist_resultThrowPersonNotFoundException() {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		when(personServiceMock.getPerson(firstName, lastName))
				.thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		assertThrows(PersonNotFoundException.class,
				() -> personInformationService.getPersonInformation(firstName, lastName));
	}

	/**
	 * Method that test getChildAlertList when address is "1509 Culver St" then
	 * return A list with 2 childs: TenLey and Roger Boyd and a list with 3 adults :
	 * John, Jacob and Felicia Boyd
	 */
	@Test
	public void testGetChildAlertList_whenAddressExist_thenReturnListOfChildAndListOfAdultsLivingInSameAddress() {
		// GIVEN
		List<Person> mockListByAddress = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"drk@email.com");
		Person index2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index3 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		Person index4 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		mockListByAddress.add(index0);
		mockListByAddress.add(index1);
		mockListByAddress.add(index2);
		mockListByAddress.add(index3);
		mockListByAddress.add(index4);

		List<MedicalRecord> mockListMedicalRecordByAddress = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Jacob", "Boyd", "03/06/1989",
				new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord4 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));

		mockListMedicalRecordByAddress.add(indexMRecord0);
		mockListMedicalRecordByAddress.add(indexMRecord1);
		mockListMedicalRecordByAddress.add(indexMRecord2);
		mockListMedicalRecordByAddress.add(indexMRecord3);
		mockListMedicalRecordByAddress.add(indexMRecord4);

		String address = "1509 Culver St";
		when(personDAOMock.getListPersonByAddress(address)).thenReturn(mockListByAddress);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockListByAddress))
				.thenReturn(mockListMedicalRecordByAddress);
		// WHEN
		PersonChildAlertDisplaying childAlertResult = personInformationService.getChildAlertList(address);
		// THEN
		// verify that Tenley Boyd is contained in childs list
		assertEquals("Tenley", childAlertResult.getListChild().get(0).getFirstName());
		assertEquals("Boyd", childAlertResult.getListChild().get(0).getLastName());
		assertEquals(9, childAlertResult.getListChild().get(0).getAge());
		// verify that John Boyd is contained in list of other person living in same
		// address
		assertEquals("John", childAlertResult.getListOtherPersonInHouse().get(0).getFirstName());
		assertEquals("Boyd", childAlertResult.getListOtherPersonInHouse().get(0).getLastName());
		assertEquals(37, childAlertResult.getListOtherPersonInHouse().get(0).getAge());
	}

	/**
	 * Method that test getChildAlertList when address not exist then should throw a
	 * {@link AddressNotFoundException}
	 */
	@Test
	public void testGetChildAlertList__whenInputAddressNotExist_thenThrowAddressNotFoundException() {
		// GIVEN
		String address = "10 Flower St";
		when(personDAOMock.getListPersonByAddress(address)).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(AddressNotFoundException.class, () -> personInformationService.getChildAlertList(address));
	}

	/**
	 * Method that test getHouseHoldsCoveredByFireStation when stations number are
	 * "3" and "2" and addresses covered by stations are: "1509 Culver St", "29 15th
	 * St" then return a list containing persons grouping by address, a list of
	 * personFlood with firstName, lastName, phone, age and medical history for each
	 * person
	 */
	@Test
	public void testGetHouseHoldsCoveredByFireStation_whenStationNumberIsTwoAndThree_thenReturnPersonsCoveredByFireStationTwoAndThree() {
		// GIVEN
		List<String> stations = new ArrayList<>(Arrays.asList("3", "2"));
		List<String> mockListAddressStationThree = new ArrayList<>(Arrays.asList("1509 Culver St"));
		List<String> mockListAddressStationTwo = new ArrayList<>(Arrays.asList("29 15th St"));

		List<Person> mockListByAddressStationThree = new ArrayList<>(Arrays.asList(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
				new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
				new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6514", "tenz@email.com"),
				new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6515", "jaboyd@email.com"),
				new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-644", "jaboyd@email.com")));

		Person PersonMarrack = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513",
				"drk@email.com");

		List<MedicalRecord> mockListMedicalRecordStationsTwoAndThree = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Jacob", "Boyd", "03/06/1989",
				new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord4 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));
		MedicalRecord indexMRecord5 = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989", new ArrayList<>(),
				new ArrayList<>());

		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord0);
		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord1);
		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord2);
		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord3);
		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord4);
		mockListMedicalRecordStationsTwoAndThree.add(indexMRecord5);

		PersonFlood PersonFloodJohnBoyd = PersonFlood.builder().firstName("John").lastName("Boyd")
				.medication(new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")))
				.allergies(new ArrayList<>(Arrays.asList("nillacilan"))).age(37).phone("841-874-6512").build();
		PersonFlood PersonFloodFeliciaBoyd = PersonFlood.builder().firstName("Felicia").lastName("Boyd")
				.medication(new ArrayList<>(Arrays.asList("tetracyclaz:650mg")))
				.allergies(new ArrayList<>(Arrays.asList("xilliathal"))).age(35).phone("841-874-644").build();
		PersonFlood PersonFloodJonanathanMarrack = PersonFlood.builder().firstName("Jonanathan").lastName("Marrack")
				.medication(new ArrayList<>()).allergies(new ArrayList<>()).age(32).phone("841-874-6513").build();

		List<Person> listByAddressMarrack = new ArrayList<>(Arrays.asList(PersonMarrack));
		when(fireStationDAOMock.getAddressesCoveredByStationNumber("2")).thenReturn(mockListAddressStationTwo);
		when(fireStationDAOMock.getAddressesCoveredByStationNumber("3")).thenReturn(mockListAddressStationThree);
		when(personDAOMock.getListPersonByAddress("29 15th St")).thenReturn(listByAddressMarrack);
		when(personDAOMock.getListPersonByAddress("1509 Culver St")).thenReturn(mockListByAddressStationThree);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(indexMRecord0, indexMRecord1, indexMRecord2,
				indexMRecord3, indexMRecord4, indexMRecord5);
		// WHEN
		List<PersonFloodDisplaying> listPersonsgroupingByAddress = personInformationService
				.getHouseHoldsCoveredByFireStation(stations);
		// THEN
		// persons covered by station 3
		// verify that John boyd is recorded in address "1509 Culver St" in index 0
		assertEquals("1509 Culver St", listPersonsgroupingByAddress.get(0).getAddress());
		assertEquals(PersonFloodJohnBoyd, listPersonsgroupingByAddress.get(0).getListPersonsFlood().get(0));
		assertEquals(PersonFloodFeliciaBoyd, listPersonsgroupingByAddress.get(0).getListPersonsFlood().get(4));
		// persons covered by Station 2
		assertEquals("29 15th St", listPersonsgroupingByAddress.get(1).getAddress());
		assertEquals(PersonFloodJonanathanMarrack, listPersonsgroupingByAddress.get(1).getListPersonsFlood().get(0));
	}

	/**
	 * Method that test getHouseHoldsCoveredByFireStation when station not exist
	 * then should throw a {@link FireStationNotFoundException}
	 */
	@Test
	public void testGetHouseHoldsCoveredByFireStation_whenStationsNumberNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		List<String> stations = new ArrayList<>(Arrays.asList("8", "9"));
		// WHEN
		// THEN
		assertThrows(FireStationNotFoundException.class,
				() -> personInformationService.getHouseHoldsCoveredByFireStation(stations));
	}

	/**
	 * Method that test getPersonsFireByAddress when stations address "1509 Culver
	 * St" then return a list containing persons with informations:firstName,
	 * lastName, phone, age, list of medication and list of Allergies and the
	 * station number that covers this address
	 * 
	 */
	@Test
	public void testGetPersonsFireByAddress_whenAddressExist_thenReturnListPersons() {
		// GIVEN
		String address = "1509 Culver St";
		List<Person> mockListByAddress = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"drk@email.com");
		Person index2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index3 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		Person index4 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		mockListByAddress.add(index0);
		mockListByAddress.add(index1);
		mockListByAddress.add(index2);
		mockListByAddress.add(index3);
		mockListByAddress.add(index4);

		List<MedicalRecord> mockListMedicalRecordByAddress = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Jacob", "Boyd", "03/06/1989",
				new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord3 = new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord4 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));

		mockListMedicalRecordByAddress.add(indexMRecord0);
		mockListMedicalRecordByAddress.add(indexMRecord1);
		mockListMedicalRecordByAddress.add(indexMRecord2);
		mockListMedicalRecordByAddress.add(indexMRecord3);
		mockListMedicalRecordByAddress.add(indexMRecord4);

		FireStation fireStationMock = new FireStation("3", "1509 Culver St");
		when(personDAOMock.getListPersonByAddress(address)).thenReturn(mockListByAddress);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(indexMRecord0, indexMRecord1, indexMRecord2,
				indexMRecord3, indexMRecord4);
		when(fireStationDAOMock.get(address)).thenReturn(fireStationMock);

		// WHEN
		PersonFireDisplaying resultListPersonFireDisplaying = personInformationService.getPersonsFireByAddress(address);
		// THEN
		// in households living 5 persons
		assertEquals(5, resultListPersonFireDisplaying.getListPersonFire().size());
		assertEquals("Tenley", resultListPersonFireDisplaying.getListPersonFire().get(2).getFirstName());
		assertEquals("Boyd", resultListPersonFireDisplaying.getListPersonFire().get(2).getLastName());
		assertEquals(9, resultListPersonFireDisplaying.getListPersonFire().get(2).getAge());
		assertEquals("John", resultListPersonFireDisplaying.getListPersonFire().get(0).getFirstName());
		assertEquals("Boyd", resultListPersonFireDisplaying.getListPersonFire().get(0).getLastName());
		// allergies of John Boyd
		assertEquals("nillacilan", resultListPersonFireDisplaying.getListPersonFire().get(0).getAllergies().get(0));
	}

	/**
	 * Method that test GetPersonsFireByAddress when address not exist then should
	 * throw a {@link AddressNotFoundException}
	 */
	@Test
	public void testGetPersonsFireByAddress_whenAddressNotExist_thenThrowAddressNotFoundException() {
		// GIVEN
		String address = "2 Backer St";
		when(personDAOMock.getListPersonByAddress(address)).thenReturn(null);
		// WHEN
		// THEN
		assertThrows(AddressNotFoundException.class, () -> personInformationService.getPersonsFireByAddress(address));
	}
}
