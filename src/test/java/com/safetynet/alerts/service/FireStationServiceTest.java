package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 * Class that test the FireStationService class
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {
	/**
	 * an instance of {@link FireStationService}
	 */
	private FireStationService fireStationServiceTest;

	/**
	 * A mock of {@link FireStationDAO}
	 */
	@Mock
	private FireStationDAO fireStationDAOMock;
	
	@Mock
	private PersonDAO personDAOMock;
	
	@Mock
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * A mock of the arraysList of {@link FireStation}
	 */
	@Mock
	private List<FireStation> mockListFireStation;

	/**
	 * Method that create a mock of the ArrayList mockListFireStation with 4
	 * elements
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListFireStation = new ArrayList<>();

		FireStation fireStationIndex0 = new FireStation("3", "1509 Culver St");
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		FireStation fireStationIndex2 = new FireStation("3", "834 Binoc Ave");
		FireStation fireStationIndex3 = new FireStation("3", "748 Townings Dr");
		mockListFireStation.add(fireStationIndex0);
		mockListFireStation.add(fireStationIndex1);
		mockListFireStation.add(fireStationIndex2);
		mockListFireStation.add(fireStationIndex3);

		fireStationServiceTest = FireStationService.builder().fireStationDAO(fireStationDAOMock)
															  .personDAO(personDAOMock)
															  .medicalRecordDAO(medicalRecordDAOMock)
															  .build();
	}

	/**
	 * Method that test get with address "1509 Culver St" when fireStation exist
	 * then return a fireStation and verify that the method get was called
	 * 
	 * throws {@link EmptyFieldsException}
	 */
	@Test
	public void testGetFireStation_whenFireStationExist_thenReturnFireStation() {
		// GIVEN
		FireStation fireStationExist = new FireStation("3", "1509 Culver St");
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationExist);
		// WHEN
		FireStation resultGetFireStationExist = fireStationServiceTest.getFireStation(fireStationExist.getAddress());
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		assertEquals(fireStationExist, resultGetFireStationExist);
	}

	/**
	 * Method that test get when field address is empty then throw an
	 * {@link EmptyFieldsException} and verify that the method get was not called
	 */
	@Test
	public void testGetFireStation_whentheFieldAddressIsEmpty_thenThrowEmptyFieldsException() {
		// GIVEN
		FireStation fireStationNotExist = new FireStation("3", "");
		// WHEN

		// THEN
		// verify that the method get of fireStationDAO is not called
		verify(fireStationDAOMock, times(0)).get(fireStationNotExist.getAddress());
		assertThrows(EmptyFieldsException.class,
				() -> fireStationServiceTest.getFireStation(fireStationNotExist.getAddress()));
	}

	/**
	 * Method that test get when fireStation not exist then should throw a
	 * {@link FireStationNotFoundException} and verify that the method get was not
	 * called
	 * 
	 */
	@Test
	public void testGetFireStation_whenFireStationNotExist_thenThrowFireStationNotFoundException() {
		// GIVEN
		FireStation fireStationNotExist = new FireStation("3", "15 Flower St");
		// WHEN

		// THEN
		// verify that the method get of fireStationDAO is not called
		verify(fireStationDAOMock, times(0)).get(fireStationNotExist.getAddress());
		assertThrows(FireStationNotFoundException.class,
				() -> fireStationServiceTest.getFireStation(fireStationNotExist.getAddress()));
	}

	/**
	 * Method that test addFireStation when fireStation to add exist then throw a
	 * {@link FireStationAlreadyExistException}
	 */
	@Test
	public void testAddFireStation_whenFireStationToSaveExist_thenThrowFireStationAlreadyExistException() {
		// GIVEN
		FireStation fireStationToSaveExist = new FireStation("3", "834 Binoc Ave");
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		// WHEN

		// THEN
		// verify that the method save was not invoked
		verify(fireStationDAOMock, times(0)).save(any(), anyInt());
		assertThrows(FireStationAlreadyExistException.class,
				() -> fireStationServiceTest.addFireStation(fireStationToSaveExist));
	}

	/**
	 * Method that test addFireStation when fireStation to add not exist then return
	 * the fireStation saved and verify if the method save was called
	 */
	@Test
	public void testAddFireStation_whenFireStationNotExistInArray_thenFireStationIsAdded() {
		// GIVEN
		FireStation fireStationToAddNotExist = new FireStation("3", "834 Palacium st");
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.save(any(), anyInt())).thenReturn(fireStationToAddNotExist);
		// WHEN
		FireStation resultFireStationAdded = fireStationServiceTest.addFireStation(fireStationToAddNotExist);
		// THEN
		verify(fireStationDAOMock, times(1)).getFireStations();
		verify(fireStationDAOMock, times(1)).save(any(), anyInt());
		assertEquals(fireStationToAddNotExist, resultFireStationAdded);
		assertEquals(fireStationToAddNotExist.getAddress(), resultFireStationAdded.getAddress());
		assertEquals(fireStationToAddNotExist.getStation(), resultFireStationAdded.getStation());
	}

	/**
	 * Method that test updateFireStation when fireStation to update exist then
	 * return fireStation ("3", "1509 Culver St") with the field station updated
	 * with sation number "2" and verify that the method save was called
	 */
	@Test
	public void testUpdateFireStation_whenFireStationExistInArray_thenReturnFireStationNumberThreeWithTheFieldAdressUpdated() {
		// GIVEN
		FireStation fireStationRecordedInArray = new FireStation("3", "1509 Culver St");
		FireStation fireStationToUpdate = new FireStation("2", "1509 Culver St");
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationRecordedInArray);
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.save(any(), anyInt())).thenReturn(fireStationToUpdate);
		// WHEN
		FireStation resultPersonUpdated = fireStationServiceTest.updateFireStation(fireStationToUpdate);
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		verify(fireStationDAOMock, times(1)).save(any(), anyInt());
		// the field station that was been modified has been updated
		assertEquals("2", resultPersonUpdated.getStation());
	}

	/**
	 * Method that test updateFireStation when fireStation to update not exist then
	 * throw a FireStationNotFoundException and verify that the method save was not
	 * called
	 */
	@Test
	public void testUpdateFireStation_whenFireStationNotExistInArray_thenThrowAFireStationNotFoundException() {
		// GIVEN
		FireStation fireStationToUpdate = new FireStation("2", "1 wall Street");
		when(fireStationDAOMock.get(anyString())).thenReturn(null);
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		// WHEN
		// THEN
		verify(fireStationDAOMock, times(0)).save(any(), anyInt());
		assertThrows(FireStationNotFoundException.class,
				() -> fireStationServiceTest.updateFireStation(fireStationToUpdate));
	}

	/**
	 * Method that test deleteFireStation when fireStation to delete exist then
	 * return a String containing "SUCCESS" and verify that the method delete was
	 * called
	 */
	@Test
	public void testdeleteFireStation_whenWeWantDeleteFireStationWithAddressAndExist_thenRetunMessageSUCCESS() {
		// GIVEN
		FireStation fireStationToDeleted = new FireStation("3", "748 Townings Dr");
		String address = "748 Townings Dr";

		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationToDeleted);
		when(fireStationDAOMock.delete(any())).thenReturn("SUCCESS");
		// WHEN
		String resultDeleteAdress = fireStationServiceTest.deleteFireStation(address);
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		verify(fireStationDAOMock, times(1)).delete(any());
		assertEquals("SUCCESS", resultDeleteAdress);
	}

	/**
	 * Method that test deleteFireStation when fireStation to delete not exist then
	 * return a String containing "FireStation cannot be deleted" and verify that
	 * the method delete was not called
	 */
	@Test
	public void testdeleteFireStation_whenWeWantDeleteFireStationWithAddressButNotExist_theReturnMessageFireStationCannotBeDeleted() {
		// GIVEN
		String addressFireStationToDeleted = "7 Toontown St";
		when(fireStationDAOMock.get(addressFireStationToDeleted)).thenReturn(null);
		// WHEN
		String resultDeleteAdress = fireStationServiceTest.deleteFireStation(addressFireStationToDeleted);
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		// Method delete not invoked because fireStation not exist
		verify(fireStationDAOMock, times(0)).delete(any());
		assertEquals("FireStation cannot be deleted", resultDeleteAdress);
	}
	
	@Test
	public void testgetListPersonsCoveredByFireStation_whenNumberFireStationExist_thenReturnListPersonsAdultsAndChilds() throws Exception {
		//GIVEN
		List<Person> mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");
		Person index2 = new Person("Tenley", "Boyd", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person index3 = new Person("Jonanathan", "Marrack", "748 Townings Dr", "Culver", "97451", "841-874-6513",
				"drk@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index2);
		mockList.add(index3);
		
		List<MedicalRecord> mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
							   new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
							   new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Lily", "Cooper", "03/06/1994", 
							   new ArrayList<>(), 
							   new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Tenley", "Boyd", "02/08/2012", 
							   new ArrayList<>(Arrays.asList()),
							   new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord3 = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989",
							   new ArrayList<>(Arrays.asList()), 
							   new ArrayList<>(Arrays.asList()));
		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		mockListMedicalRecord.add(indexMRecord3);
		
		String station = "3";
		when(fireStationDAOMock.getFireStations()).thenReturn(mockListFireStation);
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		Person expectedJohnBoyd = new Person ("John", "Boyd", "1509 Culver St", "841-874-6512");
		Person expectedJonanathanMarrack = new Person ("Jonanathan", "Marrack", "748 Townings Dr", "841-874-6513");
		//WHEN
		List<Object> listPersonsCovededByStationThree = fireStationServiceTest.getAddressCoveredByFireStation(station);
		//THEN
		//verify that the list contained 3 elements of person and the one counter for child and one counter for adults
		assertEquals(5, listPersonsCovededByStationThree.size());
		assertEquals(expectedJohnBoyd, listPersonsCovededByStationThree.get(0));
		assertEquals(expectedJonanathanMarrack, listPersonsCovededByStationThree.get(2));
	}
	
	@Test
	public void testgetListPersonsCoveredByFireStation_whenStationNumberNotExist_thenThrowFireStationNotFoundException() {
		//GIVEN
		FireStation fireStationNotExist = new FireStation("5", "1509 Culver St");
		List<String> listAddressCoveredByFireStationMock  = mock(ArrayList.class);
		when(mockListFireStation.stream()
				.filter(fireStation -> fireStation.getStation().equalsIgnoreCase(fireStationNotExist.getStation()))
				.map(fireStation -> fireStation.getAddress())
				.collect(Collectors.toList())).thenReturn(listAddressCoveredByFireStationMock);
		when(listAddressCoveredByFireStationMock.size()).thenReturn(0);
		//WHEN
		//THEN
		assertThrows(FireStationNotFoundException.class,
				() -> fireStationServiceTest.getAddressCoveredByFireStation(fireStationNotExist.getStation()));
	}
}
