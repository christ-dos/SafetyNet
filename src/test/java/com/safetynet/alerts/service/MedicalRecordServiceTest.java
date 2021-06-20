package com.safetynet.alerts.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.MedicalRecordAlreadyExistException;
import com.safetynet.alerts.exceptions.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;

/**
 * Class that test the MedicalRecordService
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

	/**
	 * an instance of {@link MedicalRecordService}
	 */
	private MedicalRecordService medicalRecordServiceTest;

	/**
	 * A mock of {@link MedicalRecordDAO}
	 */
	@Mock
	private MedicalRecordDAO medicalRecordDAOMock;

	/**
	 * A mock of the arraysList of {@link MedicalRecord}
	 */
	@Mock
	private List<MedicalRecord> mockListMedicalRecord;

	/**
	 * Method that create a mock of the ArrayList mockListMedicalRecord with 4
	 * elements
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord index0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord index1 = new MedicalRecord("Lily", "Cooper", "03/06/1994", new ArrayList<>(), new ArrayList<>());
		MedicalRecord index2 = new MedicalRecord("Tenley", "Boyd", "02/08/2012", new ArrayList<>(Arrays.asList()),
				new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord index3 = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()));
		mockListMedicalRecord.add(index0);
		mockListMedicalRecord.add(index1);
		mockListMedicalRecord.add(index2);
		mockListMedicalRecord.add(index3);
		
		medicalRecordServiceTest = MedicalRecordService.builder().medicalRecordDAO(medicalRecordDAOMock).build();
	}
	
	/**
	 * Method that test getListMedicalRecord then return a list of MedicalRecord with 4 elements
	 * and verify that John Boyd is present in the list
	 */
	@Test
	public void testGetListMedicalRecord_thenReturnListOfFireStations() {
		// GIVEN
		MedicalRecord medicalRecordTest = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		// WHEN
		List<MedicalRecord> resultListgetted = medicalRecordServiceTest.getListMedicalRecords();
		// THEN
		assertEquals(medicalRecordTest, resultListgetted.get(0));
		// the list contain 4 elements
		assertEquals(4, resultListgetted.size());
	}
	
	/**
	 * Method that test getMedicalRecord with firstName John and LastName Boyd when
	 * medicalRecord exist then return a medicalRecord with name John Boyd and
	 * verify that the method getMedicalRecord was called
	 * 
	 */
	@Test
	public void testGetMedicalRecord_whenMedicalRecordExistWithFirstNameJohnAndLastNameBoyd_thenReturnAMedicalRecordWithNameJohnBoyd() {
		// GIVEN
		MedicalRecord medicalRecordJohnBoyd = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		String firstName = medicalRecordJohnBoyd.getFirstName();
		String lastName = medicalRecordJohnBoyd.getLastName();
		when(medicalRecordDAOMock.get(firstName, lastName)).thenReturn(medicalRecordJohnBoyd);
		// WHEN
		MedicalRecord resultMedicalRecordGetted = medicalRecordServiceTest.getMedicalRecord(firstName, lastName);
		// THEN
		verify(medicalRecordDAOMock, times(1)).get(anyString(), anyString());
		assertNotNull(resultMedicalRecordGetted);
		assertEquals("John", resultMedicalRecordGetted.getFirstName());
		assertEquals("Boyd", resultMedicalRecordGetted.getLastName());
		assertEquals(medicalRecordJohnBoyd, resultMedicalRecordGetted);
	}

	/**
	 * Method that test getMedicalRecord when medicalRecord not exist then should
	 * throw a {@link MedicalRecordNotFoundException} and verify that the method
	 * getMedicalRecord was not called
	 */
	@Test
	public void testGetMedicalRecord_whenInputMedicalRecordNotExist_thenThrowMedicalNotFoundException() {
		// GIVEN
		String firstName = "Lubin";
		String lastName = "Dujardin";
		// WHEN
		// THEN
		// verify that method getMedicalRecord was not called
		verify(medicalRecordDAOMock, times(0)).get(anyString(), anyString());
		assertThrows(MedicalRecordNotFoundException.class,
				() -> medicalRecordServiceTest.getMedicalRecord(firstName, lastName));
	}

	/**
	 * Method that test getMedicalRecord when field firstName or lastName is empty
	 * then throw a {@link EmptyFieldsException} and verify that the method
	 * getMedicalRecord was not called
	 */
	@Test
	public void testGetMedicalRecord_whenFielsFirstNameOrLastNameIsEmpty_thenReturnEmptyFieldsException() {
		// GIVEN
		String firstName = "John";
		String lastName = "";
		// WHEN
		// THEN
		// verify that method getMedicalRecord was not called
		verify(medicalRecordDAOMock, times(0)).get(anyString(), anyString());
		assertThrows(EmptyFieldsException.class, () -> medicalRecordServiceTest.getMedicalRecord(firstName, lastName));
	}

	/**
	 * Method that test addMedicalRecord when medicalRecord to add not exist then
	 * return the medicalRecord saved and verify if the method save in
	 * MedicalRecordDAO was called
	 */
	@Test
	public void testAddMedicalRecord_whenMedicalRecordToAddNotExist_thenVerifyIfMedicalRecordIsAdded() {
		// GIVEN
		MedicalRecord medicalRecordToAdd = new MedicalRecord("Jojo", "Dupond", "03/06/2000",
				new ArrayList<>(Arrays.asList("hydrapermazol:100mg")), new ArrayList<>(Arrays.asList()));
		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		when(medicalRecordDAOMock.save(anyInt(), any())).thenReturn(medicalRecordToAdd);
		// WHEN
		MedicalRecord resultAfterAddMedicalRecord = medicalRecordServiceTest.addMedicalRecord(medicalRecordToAdd);
		// THEN
		verify(medicalRecordDAOMock, times(1)).getMedicalRecords();
		verify(medicalRecordDAOMock, times(1)).save(anyInt(), any());
		assertEquals(medicalRecordToAdd, resultAfterAddMedicalRecord);
		assertEquals("hydrapermazol:100mg", resultAfterAddMedicalRecord.getMedications().get(0));
		assertEquals(medicalRecordToAdd.getFirstName(), resultAfterAddMedicalRecord.getFirstName());
	}

	/**
	 * Method that test addMedicalRecord when medicalRecord to add exist then throw
	 * a {@link MedicalRecordAlreadyExistException}
	 */
	@Test
	public void testAddMedicalRecord_whenMedicalRecordToAddExist_thenThrowMedicalRecordAlreadyExistException() {
		// GIVEN
		MedicalRecord medicalRecordToAddAlreadyExist = new MedicalRecord("Tenley", "Boyd", "02/08/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		// WHEN
		// THEN
		// verify that the method get in MedicalRecordDAO was not called
		verify(medicalRecordDAOMock, times(0)).get(anyString(), anyString());
		assertThrows(MedicalRecordAlreadyExistException.class,
				() -> medicalRecordServiceTest.addMedicalRecord(medicalRecordToAddAlreadyExist));
	}

	/**
	 * Method that test updateMedicalRecord when medicalRecord to update exist then
	 * return the medicalRecord for person Jonanathan Marrack with the field
	 * allergies updated and verify that the method save was called
	 */
	@Test
	public void testUpdateMedicalRecord_whenMedicalRecordExistWithFirstNameJonanthanLastNameMarrack_thenReturnMedicalRecordJonanathanMarrackWithTheFieldAllergiesUpdated() {
		// GIVEN
		MedicalRecord MedicalRecordRecordedInArray = new MedicalRecord("Jonanathan", "Marrack", "01/03/1989",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()));
		MedicalRecord MedicalRecordToUpdateWithFieldAllergiesModified = new MedicalRecord("Jonanathan", "Marrack",
				"01/03/1989", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));

		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(MedicalRecordRecordedInArray);
		when(medicalRecordDAOMock.getMedicalRecords()).thenReturn(mockListMedicalRecord);
		when(medicalRecordDAOMock.save(anyInt(), any())).thenReturn(MedicalRecordToUpdateWithFieldAllergiesModified);
		// WHEN
		MedicalRecord resultMedicalRecordUpdated = medicalRecordServiceTest
				.updateMedicalRecord(MedicalRecordToUpdateWithFieldAllergiesModified);
		// THEN
		verify(medicalRecordDAOMock, times(1)).get(anyString(), anyString());
		verify(medicalRecordDAOMock, times(1)).save(anyInt(), any());
		assertEquals("Jonanathan", resultMedicalRecordUpdated.getFirstName());
		assertEquals("Marrack", resultMedicalRecordUpdated.getLastName());
		assertEquals("01/03/1989", resultMedicalRecordUpdated.getBirthDate());
		// the field allergies that was been modified has been updated
		assertEquals("peanut", resultMedicalRecordUpdated.getAllergies().get(0));
	}

	/**
	 * Method that test updateMedicalRecord when medicalRecord to update not exist
	 * then throw a {@link MedicalRecordNotFoundException} and verify that the
	 * method save was not called
	 */
	@Test
	public void testUpdateMedicalRecord_whenMedicalRecordToUpdateNotExist_thenReturnMedicalRecordNotFoundException() {
		// GIVEN
		MedicalRecord medicalRecordToUpdateNotExist = new MedicalRecord("Babar", "Elephant", "02/08/1950",
				new ArrayList<>(Arrays.asList("thradox:700mg")), new ArrayList<>(Arrays.asList("banana")));
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(null);
		// WHEN
		// THEN
		// verify that the method save was not called
		verify(medicalRecordDAOMock, times(0)).save(anyInt(), any());
		assertThrows(MedicalRecordNotFoundException.class,
				() -> medicalRecordServiceTest.updateMedicalRecord(medicalRecordToUpdateNotExist));
	}

	/**
	 * Method that test deleteMedicalRecord when medicalRecord to delete exist then
	 * return a String containing "SUCCESS" and verify that the method delete was
	 * called
	 */
	@Test
	public void testDeleteeMedicalRecord_wheneMedicalRecordToDeleteExist_thenReturnStringSUCCESS() {
		// GIVEN
		MedicalRecord medicalRecordToDelete = new MedicalRecord("Lily", "Cooper", "03/06/1994", new ArrayList<>(),
				new ArrayList<>());
		String firstName = medicalRecordToDelete.getFirstName();
		String lastName = medicalRecordToDelete.getLastName();
		when(medicalRecordDAOMock.get(anyString(), anyString())).thenReturn(medicalRecordToDelete);
		when(medicalRecordDAOMock.delete(any())).thenReturn("SUCCESS");
		// WHEN
		String result = medicalRecordServiceTest.deleteMedicalRecord(firstName, lastName);
		// THEN
		verify(medicalRecordDAOMock, times(1)).get(anyString(), anyString());
		verify(medicalRecordDAOMock, times(1)).delete(any());
		assertEquals("SUCCESS", result);
	}

	/**
	 * Method that test deleteMedicalRecord when medicalRecord to delete not exist
	 * then return a String containing "MedicalRecord cannot be Deleted" and verify
	 * that the method delete was not called
	 */
	@Test
	public void testDeleteMedicalRecord_whenMedicalRecordNotExist_thenReturnAStringMedicalRecordCannotBeDeleted() {
		// GIVEN
		MedicalRecord medicalRecordToDeleteNotExist = new MedicalRecord("Alice", "Lefevre", "03/06/199",
				new ArrayList<>(), new ArrayList<>(Arrays.asList("strawberry")));
		String firstName = medicalRecordToDeleteNotExist.getFirstName();
		String lastName = medicalRecordToDeleteNotExist.getLastName();
		when(medicalRecordDAOMock.get(firstName, lastName)).thenReturn(null);
		// WHEN
		String resultMessage = medicalRecordServiceTest.deleteMedicalRecord(firstName, lastName);
		// THEN
		verify(medicalRecordDAOMock, times(1)).get(anyString(), anyString());
		// verify that method delete was not invoked
		verify(medicalRecordDAOMock, times(0)).delete(any());
		assertEquals("MedicalRecord cannot be Deleted", resultMessage);
	}
}
