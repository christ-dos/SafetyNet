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

	/**
	 * An instance of {@link FireStationDAO}
	 */
	private FireStationDAO fireStationDAOTest;

	/**
	 * A mock of a arrayList that contain {@link FireStation}
	 */
	@Mock
	private List<FireStation> mockListFireStation;

	/**
	 * Method that created a mock of the ArrayList mockListFireStation with 3
	 * elements
	 * 
	 * the mockListFireStation is injected by a builder
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

		fireStationDAOTest = FireStationDAO.builder().listFireStations(mockListFireStation).build();
	}

	/**
	 * Method that test getFireStations then return a list of fireStations with 3
	 * elements and verify that fireStation with address "29 15th St" is present in
	 * the list at the index 1
	 */
	@Test
	public void testGetDAOListFireStations_thenReturnListWithThreeElements() {
		// GIVEN
		FireStation fireStationTest = new FireStation("2", "29 15th St");
		// WHEN
		List<FireStation> resultListFireStations = fireStationDAOTest.getFireStations();
		// THEN
		assertEquals(4, resultListFireStations.size());
		// verify that the station in index 1 in the list is equal to fireStationindex1
		assertEquals(fireStationTest, resultListFireStations.get(1));

	}

	/**
	 * Method that test getAddressesCoveredByStationNumber then return a list of
	 * addresses with 3 elements and verify that fireStation number "3" contain
	 * address "1509 Culver St" in index 0
	 */
	@Test
	public void testGetListAddressesCoveredByStationDAO_thenReturnListWithAddresseswithThreeElements() {
		// GIVEN
		String fireStationNumber = "3";
		// WHEN
		List<String> resultListAddressesFireStations = fireStationDAOTest
				.getAddressesCoveredByStationNumber(fireStationNumber);
		// THEN
		assertEquals(3, resultListAddressesFireStations.size());
		// verify that the station in index 1 in the list is equal to
		// resultListAddressesFireStations index 1
		assertEquals("1509 Culver St", resultListAddressesFireStations.get(0));
	}

	/**
	 * Method that test get with address "1509 Culver St" when person exist then
	 * return the fireStation number "3" with address "1509 Culver St"
	 * 
	 */
	@Test
	public void testGetDAOFireStation_whenAddressExistInArray_thenReturnTheStation() {
		// GIVEN
		String address = "1509 Culver St";
		// WHEN

		FireStation fireStationResult = fireStationDAOTest.get(address);
		// THEN
		assertEquals("1509 Culver St", fireStationResult.getAddress());
		assertEquals("3", fireStationResult.getStation());

	}

	/**
	 * Method that test get with address "1509 rue des Ursulines" when fireStation
	 * not exist then return null
	 * 
	 */
	@Test
	public void testGetDAOFireStation_whenAddressNotExistInArray_thenReturnNull() {
		// GIVEN
		String address = "1509 rue des Ursulines";
		// WHEN

		FireStation fireStationResult = fireStationDAOTest.get(address);
		// THEN
		assertNull(fireStationResult);

	}

	/**
	 * Method that test save in FireStationDAO when firteStation exist in array then
	 * firteStation with address"29 15th St" is saved at the index one and verify
	 * that the station modified to "5" is saved in arrayList
	 */
	@Test
	public void testSaveDAOFireStation_whenFireStationExistInArray_thenReturnFireSationSavedAtTheIndex() {
		// GIVEN
		FireStation fireStationToSaveExist = new FireStation("2", "29 15th St");
		FireStation fireStationToSaveWithStationModified = new FireStation("5", "29 15th St");
		int indexFireStationToSave = mockListFireStation.indexOf(fireStationToSaveExist);
		// WHEN
		FireStation resultFireStationSavedThatExist = fireStationDAOTest.save(fireStationToSaveWithStationModified,
				indexFireStationToSave);
		// THEN
		assertEquals(fireStationToSaveWithStationModified, resultFireStationSavedThatExist);
		// after saved the list contain already 4 elements
		assertEquals(4, mockListFireStation.size());
		assertEquals("5", resultFireStationSavedThatExist.getStation());
		// the fireStation that already exist was saved at the index one
		assertEquals(1, mockListFireStation.indexOf(resultFireStationSavedThatExist));

	}

	/**
	 * Method that test save in FireStationDAO when fireStation not exist then
	 * return the fireStation saved and verify if the fireStation is saved at the
	 * end of the array and verify that we are 4 elements in the arrayList
	 */
	@Test
	public void testSaveDAOFireStation_whenFireStationNotExistInArray_thenReturnFireStationSavedAtTheEnd() {
		// GIVEN
		FireStation fireStationToSaveNotExist = new FireStation("1", "29 rue du Port");
		int indexFireStation = mockListFireStation.indexOf(fireStationToSaveNotExist);
		// WHEN
		FireStation resultFireStationSavedNotExist = fireStationDAOTest.save(fireStationToSaveNotExist,
				indexFireStation);
		// THEN
		assertEquals(fireStationToSaveNotExist, resultFireStationSavedNotExist);
		// verify that the list of fireStations contain 5 elements after called of the
		// method save
		assertEquals(5, mockListFireStation.size());
		// verify that the fireStation was saved at the end of the Array
		assertEquals(4, mockListFireStation.indexOf(resultFireStationSavedNotExist));
	}

	/**
	 * Method that test delete in FireStationDAO when fireStation exist in array
	 * then return a String with "SUCCESS" when the fireStation with address "834
	 * Binoc Ave" is deleted with success and verify that the arrayList contain 2
	 * elements after the deletion
	 */
	@Test
	public void testDeleteDAOFireStation_whenFireStationExistInArray_thenReturnStringMessageWithSUCCESS() {
		// GIVEN
		FireStation fireStationToDelete = new FireStation("3", "834 Binoc Ave");
		// WHEN
		String resultMessageAfterDeletion = fireStationDAOTest.delete(fireStationToDelete);
		// THEN
		assertEquals("SUCCESS", resultMessageAfterDeletion);
		// verify that after deletion the list contain 2 elements
		assertEquals(3, mockListFireStation.size());
	}

}
