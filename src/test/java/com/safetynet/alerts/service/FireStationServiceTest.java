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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;

/**
 * Class that test the FireStationService class
 * 
 * @author Chrsitine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

	private FireStationService fireStationServiceTest;

	@Mock
	private FireStationDAO fireStationDAOMock;

	@Mock
	private List<FireStation> mockListFireStation;

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

		fireStationDAOMock = mock(FireStationDAO.class);
		fireStationServiceTest = new FireStationService(fireStationDAOMock);
	}

	@Test
	public void testGetFireStation_whenFireStationExist_thenReturnFireStation() throws EmptyFieldsException {
		// GIVEN
		FireStation fireStationExist = new FireStation("3", "1509 Culver St");
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationExist);
		// WHEN
		FireStation resultGetFireStationExist = fireStationServiceTest.getFireStation(fireStationExist.getAddress());
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		assertEquals(fireStationExist, resultGetFireStationExist);
	}

	@Test
	public void testGetFireStation_whenFireStationNotExist_thenThrowEmptyFieldsEceptionException() {
		// GIVEN
		FireStation fireStationNotExist = new FireStation("3", "");
		// WHEN

		// THEN
		// verify that the method get of fireStationDAO is not called
		verify(fireStationDAOMock, times(0)).get(fireStationNotExist.getAddress());
		assertThrows(EmptyFieldsException.class, () -> fireStationServiceTest.getFireStation(fireStationNotExist.getAddress()));
	}

	@Test
	public void testGetFireStation_whenFireStationFieldAddressIsEmpty_thenThrowFireStationEmptyfieldException() {
		// GIVEN
		FireStation fireStationNotExist = new FireStation("3", "15 Flower St");
		// WHEN

		// THEN
		// verify that the method get of fireStationDAO is not called
		verify(fireStationDAOMock, times(0)).get(fireStationNotExist.getAddress());
		assertThrows(FireStationNotFoundException.class,
				() -> fireStationServiceTest.getFireStation(fireStationNotExist.getAddress()));
	}

	@Test
	public void testAddFireStation_whenFireStationToSaveExist_thenThrowFireStationAlreadyExistException() {
		// GIVEN
		FireStation fireStationToSaveExist = new FireStation("3", "834 Binoc Ave");
		when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		// WHEN

		// THEN
		// verify that the method save was not invoked
		verify(fireStationDAOMock, times(0)).save(any(), anyInt());
		assertThrows(FireStationAlreadyExistException.class,
				() -> fireStationServiceTest.addFireStation(fireStationToSaveExist));
	}

	@Test
	public void testAddFireStation_whenFireStationNotExistInArray_thenFireStationIsAdded() {
		// GIVEN
		FireStation fireStationToAddNotExist = new FireStation("3", "834 Palacium st");
		when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.save(any(), anyInt())).thenReturn(fireStationToAddNotExist);
		// WHEN
		FireStation resultFireStationAdded = fireStationServiceTest.addFireStation(fireStationToAddNotExist);
		// THEN
		verify(fireStationDAOMock, times(1)).getListFireStations();
		verify(fireStationDAOMock, times(1)).save(any(), anyInt());
		assertEquals(fireStationToAddNotExist, resultFireStationAdded);
		assertEquals(fireStationToAddNotExist.getAddress(), resultFireStationAdded.getAddress());
		assertEquals(fireStationToAddNotExist.getStation(), resultFireStationAdded.getStation());
	}

	@Test
	public void testUpdateFireStation_whenFireStationExistInArray_thenReturnFireStationNumberThreeWithTheFieldAdressUpdated()
			throws EmptyFieldsException {
		// GIVEN
		FireStation fireStationRecordedInArray = new FireStation("3", "1509 Culver St");
		FireStation fireStationToUpdate = new FireStation("2", "1509 Culver St");
		when(fireStationDAOMock.get(anyString())).thenReturn(fireStationRecordedInArray);
		when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.save(any(), anyInt())).thenReturn(fireStationToUpdate);
		// WHEN
		FireStation resultPersonUpdated = fireStationServiceTest.updateFireStation(fireStationToUpdate);
		// THEN
		verify(fireStationDAOMock, times(1)).get(anyString());
		verify(fireStationDAOMock, times(1)).save(any(), anyInt());
		// the field station that was been modified has been updated
		assertEquals("2", resultPersonUpdated.getStation());
	}

	@Test
	public void testUpdateFireStation_whenFireStationNotExistInArray_thenThrowAFireStationNotFoundException()
			throws EmptyFieldsException {
		// GIVEN
		FireStation fireStationToUpdate = new FireStation("2", "1 wall Street");
		when(fireStationDAOMock.get(anyString())).thenReturn(null);
		when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		// WHEN
		// THEN
		verify(fireStationDAOMock, times(0)).save(any(), anyInt());
		assertThrows(FireStationNotFoundException.class,
				() -> fireStationServiceTest.updateFireStation(fireStationToUpdate));
	}

	/**@Test
	public void testdeleteFireStation_whenInputStationNumberThreeAllAddressMappedInFireSationNumberThreeWasDeleted_returnMessageSUCCESS() {
		// GIVEN
		String numberStation = "3";
		String address = null;

		when(fireStationDAOMock.getListFireStations()).thenReturn(mockListFireStation);
		when(fireStationDAOMock.delete(any())).thenReturn("SUCCESS");
		// WHEN
		String resultDeleteAllElementsStationThree = fireStationServiceTest.deleteFireStation(address);
		// THEN
		//verify(fireStationDAOMock, times(1)).getListFireStations();
		// method delete was called 3 times we are 3 elements with station number 3
		verify(fireStationDAOMock, times(1)).delete(any());
		assertEquals("SUCCESS", resultDeleteAllElementsStationThree);
	}*/

	@Test
	public void testdeleteFireStation_whenWeWantDeleteFireStationWithAddress_thenRetunMessageSUCCESS() {
		// GIVEN
		FireStation fireStationToDeleted = new FireStation("3", "748 Townings Dr");
		String numberStation = null;
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

}
