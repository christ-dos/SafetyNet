package com.safetynet.alerts.DAO;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.FireStation;

/**
 * Class that test the FireStationDAO class
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class FireStationDAOTest {

	private FireStationDAO fireStationDAOTest;

	@Mock
	private List<FireStation> mockListFireStation;

	@BeforeEach
	public void setUpPerTest() {
		mockListFireStation = new ArrayList<>();
		fireStationDAOTest = new FireStationDAO(mockListFireStation);

		FireStation fireStationIndex0 = new FireStation("3", "1509 Culver St");
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		FireStation fireStationIndex2 = new FireStation("3", "834 Binoc Ave");
		mockListFireStation.add(fireStationIndex0);
		mockListFireStation.add(fireStationIndex1);
		mockListFireStation.add(fireStationIndex2);
	}

	@Test
	public void testGetListFireStations_thenReturnListWithThreeElements() {
		// GIVEN
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		// WHEN
		List<FireStation> resultListFireStations = fireStationDAOTest.getFireStations();
		// THEN
		assertEquals(3, resultListFireStations.size());
		// verify that the station in index 1 in the list is equal to fireStationindex1
		assertEquals(fireStationIndex1, resultListFireStations.get(1));

	}

	@Test
	public void testGetDAOFireStation_whenStationExistInArray_thenReturnTheStation() {
		// GIVEN
		String address = "1509 Culver St";
		// WHEN

		FireStation fireStationResult = fireStationDAOTest.get(address);
		// THEN
		assertEquals("1509 Culver St", fireStationResult.getAddress());
		assertEquals("3", fireStationResult.getStation());

	}

	@Test
	public void testGetDAOFireStation_whenStationNotExistInArray_thenReturnNull() {
		// GIVEN
		String address = "1509 rue des Ursulines";
		// WHEN

		FireStation fireStationResult = fireStationDAOTest.get(address);
		// THEN
		assertNull(fireStationResult);

	}
	
	@Test
	public void testSaveDAOFireStation_whenFireStationExistInArray_thenReturnFireSationSavedAtTheIndex() {
		//GIVEN
		FireStation fireStationToSaveExist  = new FireStation("2", "29 15th St");
		int indexFireStationToSave = mockListFireStation.indexOf(fireStationToSaveExist);
		//WHEN
		FireStation resultFireStationSavedThatExist = fireStationDAOTest.save(fireStationToSaveExist, indexFireStationToSave);
		//THEN
		assertEquals(fireStationToSaveExist, resultFireStationSavedThatExist);
		//after saved the list contain already 3 elements
		assertEquals(3, mockListFireStation.size());
		//the fireStation that already exist was saved at the index one
		assertEquals(1, mockListFireStation.indexOf(resultFireStationSavedThatExist));
		
	}
	
	@Test
	public void testSaveDAOFireStation_whenFireStationNotExistInArray_thenReturnFireStationSavedAtTheEnd() {
		//GIVEN
		FireStation fireStationToSaveNotExist = new FireStation("1", "29 rue du Port");
		int indexFireStation = mockListFireStation.indexOf(fireStationToSaveNotExist);
		//WHEN
		FireStation resultFireStationSavedNotExist = fireStationDAOTest.save(fireStationToSaveNotExist, indexFireStation);
		//THEN
		assertEquals(fireStationToSaveNotExist, resultFireStationSavedNotExist);
		//verify that the list of fireStations contain 4 elements after called of the method save
		assertEquals(4, mockListFireStation.size());
		//verify that the fireStation was saved at the end of the Array
		assertEquals(3, mockListFireStation.indexOf(resultFireStationSavedNotExist));
	}
	
	@Test
	public void testDeleteDAOFireStation_whenFireStationExistInArray_thenReturnStringMessageWithSUCCESS() {
		//GIVEN
		FireStation fireStationToDelete = new FireStation("3", "834 Binoc Ave");
		//WHEN
		String resultMessageAfterDeletion = fireStationDAOTest.delete(fireStationToDelete);
		//THEN
		assertEquals("SUCCESS", resultMessageAfterDeletion);
		//verify that after deletion the list contain 2 elements
		assertEquals(2, mockListFireStation.size());
	}

}
