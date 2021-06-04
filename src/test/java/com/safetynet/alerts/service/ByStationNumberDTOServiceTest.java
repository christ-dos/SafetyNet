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
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.ListPersonByStationNumberDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonDTO;
import com.safetynet.alerts.model.PhoneAlertDTO;

/**
 * Class that test the ByStationNumberDTOService class
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class ByStationNumberDTOServiceTest {
	/**
	 * An instance of {@link ByStationNumberDTOService}
	 */
	private ByStationNumberDTOService byStationNumberDTOService;

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

		byStationNumberDTOService = ByStationNumberDTOService.builder()
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
		PersonDTO expectedJohnBoyd = new PersonDTO("John", "Boyd", "1509 Culver St", "841-874-6512");
		PersonDTO expectedFoster = new PersonDTO("Foster", "Shepard", "748 Townings Dr", "841-874-6544");
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(stationNumber)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockList)).thenReturn(mockListMedicalRecord);
		when(personDAOMock.getAge(anyString())).thenReturn(37, 9, 41);
		// WHEN
		ListPersonByStationNumberDTO PersonsCovededByStationThree = byStationNumberDTOService
				.getAddressCoveredByFireStation(stationNumber);
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
				() -> byStationNumberDTOService.getAddressCoveredByFireStation(station));
	}
	
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation
	 * when fireStation exits then return a list of address covered by fireSation number
	 */
	@Test
	public void testgetPhoneAlertResidentsCoveredByStation_whenStationNumberFireStationExist_thenReturnListOfPhoneOfPersonCoveredByStation() {
		// GIVEN
		
		String fireStation = "3";
		PersonDTO expectedJohnBoyd = new PersonDTO("John", "Boyd", "1509 Culver St", "841-874-6512");
		PersonDTO expectedFoster = new PersonDTO("Foster", "Shepard", "748 Townings Dr", "841-874-6544");
		when(fireStationDAOmock.getAddressesCoveredByStationNumber(fireStation)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		// WHEN
		PhoneAlertDTO phoneAlertPersonCovededByStationThree = byStationNumberDTOService
				.getPhoneAlertResidentsCoveredByStation(fireStation);
		// THEN
		// verify that the list contained 3 elements of personDTO
		assertEquals(3, phoneAlertPersonCovededByStationThree.getListPhoneAlert().size());
		//verify the list contain in index 0 John Boyd
		assertEquals(expectedJohnBoyd.getPhone(), phoneAlertPersonCovededByStationThree.getListPhoneAlert().get(0));
		assertEquals(expectedFoster.getPhone(), phoneAlertPersonCovededByStationThree.getListPhoneAlert().get(2));
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
				() -> byStationNumberDTOService.getPhoneAlertResidentsCoveredByStation(fireStation));
	}
}