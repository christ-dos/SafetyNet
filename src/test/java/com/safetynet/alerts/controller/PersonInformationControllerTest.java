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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.PartialPerson;
import com.safetynet.alerts.DTO.PersonChildAlert;
import com.safetynet.alerts.DTO.PersonFire;
import com.safetynet.alerts.DTO.PersonFireDisplaying;
import com.safetynet.alerts.DTO.PersonFlood;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;
import com.safetynet.alerts.exceptions.AddressNotFoundException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonInformationService;

/**
 * A class which test {@link PersonInformationController}
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(PersonInformationController.class)
@ExtendWith(MockitoExtension.class)
public class PersonInformationControllerTest {
	
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcPersonInformation;
	
	/**
	 * An instance of {@link PersonInformationService}
	 * 
	 */
	@MockBean
	private PersonInformationService personInformationServiceMock;
	
	/**
	 * An instance of {@link PersonDAOMock}
	 * 
	 */
	@MockBean
	private PersonDAO personDAOMock;
	
	/**
	 * An instance of {@link MedicalRecordDAO}
	 * 
	 */
	@MockBean
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * An instance of {@link fireStationDAOMock}
	 * 
	 */
	@MockBean
	private FireStationDAO fireStationDAOMock;
	
	
	/**
	 * A mock of the arraysList of {@link Person}
	 */
	@Mock
	private List<Person> mockList;
	
	/**
	 * A mock of the arraysList of {@link FireStation}
	 */
	@Mock
	private List<FireStation> mockListFireStation;

	/**
	 * A mock of the arraysList of {@link MedicalRecord}
	 */
	@Mock
	private List<MedicalRecord> mockListMedicalRecord;
	
	/**
	 * An instance of {@link PersonInfoDisplaying}
	 */
	private PersonInfoDisplaying mockPersonInfoDisplaying;
	
	/**
	 * A mock of the arraysList of String containing Addresses of person covered by Station number
	 */
	@Mock
	private List<String> mockListAddress;
	
	/**
	 * An instance of {@link PersonsCoveredByStation}
	 */
	private PersonsCoveredByStation mockPersonsCoveredByStation;
	
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
		
		mockPersonsCoveredByStation = new PersonsCoveredByStation(
				new ArrayList<>(Arrays.asList(new PartialPerson("John", "Boyd", "1509 Culver St", "841-874-6512"),
						new PartialPerson("Tessa", "Carman", "834 Binoc Ave", "841-874-6512"),
						new PartialPerson("Foster", "Shepard", "748 Townings Dr", "841-874-6544"))),
				2, 1);
	}
	
	/**
	 * Method that test getAddressCoveredByFireStation when station exist the status of the request is OK
	 * and the listPersonDTO in index 0 contain a personDTO firstName and lastName is John Boyd, address is "1509 Culver St"
	 * and a adultsCounter with 2 adults
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonDTOCoveredByFireStation() throws Exception{
		// GIVEN
		String station = "3";
		when(personInformationServiceMock.getPersonCoveredByFireStation(station)).thenReturn(mockPersonsCoveredByStation);
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockList)).thenReturn(mockListMedicalRecord);
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/firestation?station=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPartialPersons[0].firstName", is("John"))).andExpect(jsonPath("$.listPartialPersons[0].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listPartialPersons[0].address", is("1509 Culver St"))).andExpect(jsonPath("$.listPartialPersons[0].phone", is("841-874-6512")))
				.andExpect(jsonPath("$.adultsCounter", is(2)))
				.andDo(print());
	}
	
	/**
	 * Method that test getAddressCoveredByFireStation when the station number is five then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenListAddressCoveredByFireStationIsNull_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		String station = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(null);
		when(personInformationServiceMock.getPersonCoveredByFireStation(station)).thenThrow(new FireStationNotFoundException("The FireStation number not found"));
		// WHEN

		// THEN
		mockMvcPersonInformation.perform(get("/firestation?station=5")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when station exist the status of the request is OK
	 * and the list with PhoneAlertDTO  in index 0 contain a phone number "841-874-6512" of John Boyd  and in index 2 contain phone number "841-874-6544" of foster Shepard
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonDTOCoveredByFireStation() throws Exception{
		// GIVEN
		String station = "3";
		List<String> listPhoneCovrededByStationThree = new ArrayList<>(Arrays.asList("841-874-6512", "841-874-6512", "841-874-6544"));
		when(personInformationServiceMock.getPhoneAlertResidentsCoveredByStation(station)).thenReturn(listPhoneCovrededByStationThree);
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/phoneAlert?station=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("841-874-6512")))
				.andExpect(jsonPath("$.[2]", is("841-874-6544")))
				.andDo(print());
	}
	
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when the station number is "5" then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenListAddressCoveredByFireStationIsNull_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		String station = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(null);
		when(personInformationServiceMock.getPhoneAlertResidentsCoveredByStation(station)).thenThrow(new FireStationNotFoundException("The FireStation number not found"));
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/phoneAlert?station=5")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test getPersonInfo when person exist fistName John and lastName Boyd
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
		mockPersonInfoDisplaying = new PersonInfoDisplaying(personJohnBoyd.getFirstName(), personJohnBoyd.getLastName(), personJohnBoyd.getAddress(), age, personJohnBoyd.getEmail(), new ArrayList<>(medicalRecordJohnBoyd.getMedications()), new ArrayList<>(medicalRecordJohnBoyd.getAllergies()));
		when(personInformationServiceMock.getPersonInformation(anyString(), anyString())).thenReturn(mockPersonInfoDisplaying);
		when(personDAOMock.getPerson(anyString(), anyString())).thenReturn(personJohnBoyd);
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordJohnBoyd);
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/personinfo?firstName=John&lastName=Boyd")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("John"))).andExpect(jsonPath("$.lastName", is("Boyd")))
				.andExpect(jsonPath("$.address", is("1509 Culver St"))).andExpect(jsonPath("$.medication[0]", is("aznol:350mg")))
				.andExpect(jsonPath("$.allergies[0]", is("nillacilan")))
				.andExpect(jsonPath("$.age", is(37)))
				.andDo(print());
	}
	
	/**
	 * Method that test getPersonInfo when person not exist fistName Lily and lastName Sacha
	 * then throw a PersonNotFoundException
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonNotExist_thenThrowPersonNotFoundException() throws Exception {
		// GIVEN
		when(personInformationServiceMock.getPersonInformation(anyString(), anyString())).thenThrow(new PersonNotFoundException("Person not found exception"));
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/personinfo?firstName=Lily&lastName=Sacha")).andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
		.andExpect(result -> assertEquals("Person not found exception",
				result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	/**
	 * Method that test getChildAlertList when address is "1509 Culver St"
	 * then return a list with childs : Tenley and Roger Boyd and a list with adults: John, Jacob, Felicia
	 * @throws Exception
	 */
	@Test
	public void testGetChildAlertList_whenAddressIs1509CulverStAndExist_thenReturnAListWithRogerAndTenleyBoydandAListWithJohnJacobAndFeliciBoyd() throws Exception {
		// GIVEN
		List<Person> mockListByAddress = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Jacob", "Boyd","1509 Culver St","Culver","97451", "841-874-6512"
				, "drk@email.com" );
		Person index2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index3 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person index4 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
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
		
		ChildAlertDisplaying childAlertDisplaying = new ChildAlertDisplaying(
				new ArrayList<>(
						Arrays.asList(new PersonChildAlert("Tenley", "Boyd", 9), 
								new PersonChildAlert("Roger", "Boyd", 3))), new ArrayList<>(
										Arrays.asList(new PersonChildAlert("John", "Boyd", 37),
												new PersonChildAlert("Jacob", "Boyd", 32),
												new PersonChildAlert("Felicia", "Boyd", 35))));
										
		String address = "1509 Culver St";
		when(personDAOMock.getPersons()).thenReturn(mockListByAddress);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockListByAddress)).thenReturn(mockListMedicalRecordByAddress);
		when(personInformationServiceMock.getChildAlertList(address)).thenReturn(childAlertDisplaying);
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listChild.[1].firstName", is("Roger"))).andExpect(jsonPath("$.listChild.[1].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listChild.[1].age", is(3)))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].firstName", is("John"))).andExpect(jsonPath("$.listOtherPersonInHouse.[0].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].age", is(37)))
				.andDo(print());
	}

	/**
	 * Method that test getChildAlertList when address is "15 Boston St" and not exist 
	 * then throw a AddressNotFoundException
	 * @throws Exception
	 */
	@Test
	public void testGetChildAlertList_whenAddressNotExist_thenThrowAddressNotFoundException() throws Exception {
		// GIVEN
		when(personInformationServiceMock.getChildAlertList(anyString())).thenThrow(new AddressNotFoundException("Address not found exception"));
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/childAlert?address=15 Boston St")).andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof AddressNotFoundException))
		.andExpect(result -> assertEquals("Address not found exception",
				result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	/**
	 * Method that test getFloodPersonsCoveredByStationList when 
	 * stations number exist then return a map with persons informations
	 * (firstName, lastName, phone, age, and medical history)grouping by address
	 * @throws Exception
	 */
	@Test
	public void testGetFloodPersonsCoveredByStationList_whenStationsNumberExist_thenReturnListPersonsGroupingByAddress() throws Exception {
		//GIVEN
		List<String> stations = new ArrayList<>(Arrays.asList("2", "3"));
		
		Map<String, List<PersonFlood>> mockMapPersonFlood = new HashMap<>();
		mockMapPersonFlood.put("1509 Culver St",new ArrayList<>(Arrays.asList(
				new PersonFlood("John", "Boyd",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")), "1509 Culver St", "841-874-6512", 37),
				new PersonFlood("Felicia", "Boyd",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")),
				new ArrayList<>(Arrays.asList("xilliathal")), "1509 Culver St", "841-874-6544", 35))));
		mockMapPersonFlood.put("834 Binoc Ave", new ArrayList<>(Arrays.asList(new PersonFlood("Tessa", "Carman",
				new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList()), "834 Binoc Ave", "841-874-6512", 9))));
		mockMapPersonFlood.put("748 Townings Dr", new ArrayList<>(Arrays.asList(new PersonFlood("Foster", "Shepard",
				new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList()), "748 Townings Dr", "841-874-6544", 41))));
		mockMapPersonFlood.put("29 15th St", new ArrayList<>(Arrays.asList( new PersonFlood("Jonanathan", "Marrack",
				new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList()), "29 15th St", "841-874-6513",32))));
		
		when(personInformationServiceMock.getHouseHoldsCoveredByFireStation(stations)).thenReturn(mockMapPersonFlood);
		//WHEN
		//THEN
		mockMvcPersonInformation.perform(get("/flood/stations?stations=2,3")).andExpect(status().isOk())
		.andExpect(jsonPath("$.['748 Townings Dr'].[0].firstName", is("Foster"))).andExpect(jsonPath("$.['748 Townings Dr'].[0].lastName", is("Shepard")))
		.andExpect(jsonPath("$.['748 Townings Dr'].[0].age", is(41)))
		.andExpect(jsonPath("$.['1509 Culver St'].[1].firstName", is("Felicia"))).andExpect(jsonPath("$.['1509 Culver St'].[1].allergies[0]", is("xilliathal")))
		.andExpect(jsonPath("$.['1509 Culver St'].[1].age", is(35)))
		.andDo(print());
	}
	
		
	/**
	 * Method that test getFloodPersonsCoveredByStationList when stations number is "8" and "9" and not exist 
	 * then throw {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFloodPersonsCoveredByStationList_whenStationsNumberNotExist_thenReturnFireStationNotFoundException() throws Exception {
		// GIVEN
		List<String> stations = new ArrayList<>(Arrays.asList("8", "9"));
		when(personInformationServiceMock.getHouseHoldsCoveredByFireStation(stations)).thenThrow(new FireStationNotFoundException("The FireStation number not found"));
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/flood/stations?stations=8,9")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
	
	/**
	 * Method that test getPersonsFireByAddress when 
	 * address exist and is "112 Steppes Pl" then return a list with persons informations:
	 * firstName, lastName, phone, age, and medical history of Tony Cooper, Ron Peters and Allison Boyd
	 * as well as FireStation number "3" that covers this address
	 * @throws Exception
	 */
	@Test
	public void testGetPersonsFireByAddress_whenAddressExist_thenReturnListPersonAndStationNumberThatCoversAddress() throws Exception {
		//GIVEN
		String address = "112 Steppes Pl";
		PersonFireDisplaying personFireDisplayingMock = new PersonFireDisplaying(
				Arrays.asList(
						new PersonFire("Tony", "Cooper", "841-874-8888", 27, 
								new ArrayList<>(Arrays.asList("hydrapermazol:300mg", "dodoxadin:30mg")), 
								new ArrayList<>(Arrays.asList("shellfish"))
						),
						new PersonFire("Ron", "Peters", "841-874-6874", 56, 
								new ArrayList<>(), 
								new ArrayList<>()),
						new PersonFire("Allison", "Boyd", "841-874-9888", 56, 
								new ArrayList<>(Arrays.asList("aznol:200mg")), 
								new ArrayList<>(Arrays.asList("nillacilan")))), "3");
		
		when(personInformationServiceMock.getPersonsFireByAddress(address)).thenReturn(personFireDisplayingMock);
		//WHEN
		//THEN
		mockMvcPersonInformation.perform(get("/fire?address=112 Steppes Pl")).andExpect(status().isOk())
		.andExpect(jsonPath("$.listPersonFire.[0].firstName", is("Tony"))).andExpect(jsonPath("$.listPersonFire[0].lastName", is("Cooper")))
		.andExpect(jsonPath("$.listPersonFire.[0].age", is(27))).andExpect(jsonPath("$.listPersonFire.[0].medication.[1]", is("dodoxadin:30mg")))
		.andExpect(jsonPath("$.listPersonFire.[2].firstName", is("Allison"))).andExpect(jsonPath("$.listPersonFire[2].lastName", is("Boyd")))
		.andExpect(jsonPath("$.listPersonFire.[2].medication[0]", is("aznol:200mg")))
		.andExpect(jsonPath("$.listPersonFire.[2].age", is(56)))
		.andExpect(jsonPath("$.stationNumber", is("3")))
		.andDo(print());
	}

	/**
	 * Method that test getPersonsFireByAddress when address is "15 Backer St" and not exist 
	 * then throw {@link AddressNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFirePersonsByAddress_whenAddressNotExist_thenReturnAddressNotFoundException() throws Exception {
		// GIVEN
		String address = "15 Backer St";
		when(personInformationServiceMock.getPersonsFireByAddress(address)).thenThrow(new AddressNotFoundException("Address not found"));
		// WHEN
		// THEN
		mockMvcPersonInformation.perform(get("/fire?address=15 Backer St")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AddressNotFoundException))
				.andExpect(result -> assertEquals("Address not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
