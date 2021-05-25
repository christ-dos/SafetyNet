package com.safetynet.alerts.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * Class that test MedicalRecordDAO
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class MedicalRecordDAOTest {
	/**
	 * An instance of {@link MedicalRecordDAO}
	 */
	private MedicalRecordDAO medicalRecordDAOTest;

	/**
	 * A mock of a arrayList that contain {@link MedicalRecord}
	 */
	@Mock
	private List<MedicalRecord> mockListMedicalRecord;

	/**
	 * Method that created a mock of the ArrayList mockListMedicalRecord with 3
	 * elements
	 * 
	 * the mockList is injected at the method by a builder
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord index0 = new MedicalRecord("John", "Boyd", "03/06/1984",
							   new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
							   new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord index1 = new MedicalRecord("Lily", "Cooper", "03/06/1994",
							   new ArrayList<>(),
							   new ArrayList<>());
		MedicalRecord index2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012", 
							   new ArrayList<>(Arrays.asList()),
							   new ArrayList<>(Arrays.asList("peanut")));
		mockListMedicalRecord.add(index0);
		mockListMedicalRecord.add(index1);
		mockListMedicalRecord.add(index2);
		medicalRecordDAOTest = MedicalRecordDAO.builder().listMedicalRecords(mockListMedicalRecord).build();
	}

	/**
	 * Method that test getMedicalRecords in DAO then return a list of
	 * medicalRecords with 3 elements and verify that the medicalRecord of John Boyd
	 * is present in the list in index 0 and the birthDate is "03/06/1984" and
	 * medications saved in arrayList medications in index 1 is "hydrapermazol:100mg"
	 */
	@Test
	public void testGetListMedicalRecords_resultShouldVerifythatListContainThreeMedicalRecords() {
		// GIVEN
		MedicalRecord medicalRecordJohnBoydIndex0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		// WHEN
		List<MedicalRecord> resultListgetted = medicalRecordDAOTest.getMedicalRecords();
		// THEN
		assertEquals(medicalRecordJohnBoydIndex0, resultListgetted.get(0));
		assertEquals("03/06/1984", resultListgetted.get(0).getBirthDate());
		assertEquals("hydrapermazol:100mg", resultListgetted.get(0).getMedications().get(1));
		assertEquals(3, resultListgetted.size());
	}

	/**
	 * Method that test get MedicalRecord in DAO with firstName Lily and LastName
	 * Cooper when medicalRecord exist then return the medicalRecord of Lily Cooper
	 * 
	 */
	@Test
	public void testGetMedicalRecordDAO_whenMedicalRecordExistInArray_resultShouldReturnMedicalRecordLilyCooper() {
		// GIVEN
		String firstName = "Lily";
		String lastName = "Cooper";

		// WHEN
		MedicalRecord getMedicalRecordOfLilyCooperResult = medicalRecordDAOTest.get(firstName, lastName);
		// THEN
		assertEquals("Lily", getMedicalRecordOfLilyCooperResult.getFirstName());
		assertEquals("Cooper", getMedicalRecordOfLilyCooperResult.getLastName());
		assertEquals("03/06/1994", getMedicalRecordOfLilyCooperResult.getBirthDate());
		// verify that the medicalRecord recorded in the mockListMedicalRecord in index
		// 1 is the medicalRecord of Lily Cooper
		assertEquals(mockListMedicalRecord.get(1), getMedicalRecordOfLilyCooperResult);
	}

	/**
	 * Method that test getMedicalRecord in DAO with firstName Toto and LastName
	 * Zero when medicalRecord not exist then return null
	 * 
	 */
	@Test
	public void testGetMedicalRecord_whenMedicalRecordNotExist_resultShouldReturnNull() {
		// GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		// WHEN
		MedicalRecord getResultMedicalRecordNotExist = medicalRecordDAOTest.get(firstName, lastName);
		// THEN
		assertNull(getResultMedicalRecordNotExist);
	}

	/**
	 * Method that test save MedicalRecord in DAO when medicalRecord not exist then
	 * return the medicalRecord saved and verify if the medicalRecord is saved at
	 * the end of the array and verify that we are 4 elements in the arrayList
	 */
	@Test
	public void testSaveMedicalRecord_whenMedicalRecordNotExist_thenResultMedicalRecordAddedInEndOfArray() {
		// GIVEN
		MedicalRecord medicalRecordNotExit = new MedicalRecord("Tata", "Zorro", "03/09/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg")), new ArrayList<>(Arrays.asList()));
		int index = mockListMedicalRecord.indexOf(medicalRecordNotExit);
		// WHEN
		MedicalRecord resultMedicalRecordNotExist = medicalRecordDAOTest.save(index, medicalRecordNotExit);
		// THEN
		assertSame(medicalRecordNotExit, resultMedicalRecordNotExist);
		assertEquals("Tata", resultMedicalRecordNotExist.getFirstName());
		// verify array contain 4 medicalRecords
		assertEquals(4, mockListMedicalRecord.size());
		// verify medicalRecord was Added at the end of Array
		assertEquals(3, mockListMedicalRecord.indexOf(resultMedicalRecordNotExist));
	}

	/**
	 * Method that test save MedicalRecord in DAO when medicalRecord exist in
	 * arrayList then medicalRecord of Lily Cooper is saved at the index one and
	 * verify that the allergies that was modified is saved in arrayList
	 */
	@Test
	public void testSaveMedicalRecordDAO_whenMedicalRecordExist_shouldSavedMedicalRecordInIndexOne() {
		// GIVEN
		MedicalRecord medicalRecordToSaveWithAllergiesModified = new MedicalRecord("Lily", "Cooper", "03/06/1994",
				new ArrayList<>(), new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord medicalRecordLilyCooperRecordedInArray = new MedicalRecord("Lily", "Cooper", "03/06/1994",
				new ArrayList<>(), new ArrayList<>());
		int indexLilyCooper = mockListMedicalRecord.indexOf(medicalRecordLilyCooperRecordedInArray);
		// WHEN
		MedicalRecord resultMedicalRecordExist = medicalRecordDAOTest.save(indexLilyCooper,
				medicalRecordToSaveWithAllergiesModified);
		int indexMedicalRecordSavedWithAllergiesModified = mockListMedicalRecord
				.indexOf(medicalRecordToSaveWithAllergiesModified);
		// THEN
		assertEquals(medicalRecordToSaveWithAllergiesModified.getFirstName(), resultMedicalRecordExist.getFirstName());
		// verify that the medicalRecord was saved at the index of Lily Cooper
		assertEquals(indexLilyCooper, indexMedicalRecordSavedWithAllergiesModified);
		// verify that allergies was saved in Object Lily Cooper
		assertEquals("nillacilan", resultMedicalRecordExist.getAllergies().get(0));
	}

	/**
	 * Method that test delete MedicalRecord in DAO when medicalRecord exist in
	 * arrayList then return a String with "SUCCESS" when the medicalRecord of
	 * Tenley Boyd is deleted with success and verify that the arrayList contain 2
	 * elements after the deletion
	 */
	@Test
	public void testDeleteMedicalRecordDAO_whenMedicalRecordTenleyBoydExist_resultShouldReturnMessageSUCCESS() {
		// GIVEN
		MedicalRecord medicalRecordTenleyBoydExist = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		String firstName = medicalRecordTenleyBoydExist.getFirstName();
		String lastName = medicalRecordTenleyBoydExist.getLastName();
		// WHEN
		String resultMessage = medicalRecordDAOTest.delete(medicalRecordTenleyBoydExist);
		// we called method get medicalRecord to verify that medicalRecord of Tenley
		// Boyd no longer exists
		MedicalRecord resultCallGetPersonAfterDelete = medicalRecordDAOTest.get(firstName, lastName);
		// THEN
		// verify that medicalRecord of Tenley Boyd no long exists in ArrayList
		assertNull(resultCallGetPersonAfterDelete);
		assertEquals("SUCCESS", resultMessage);
		assertEquals(2, mockListMedicalRecord.size());
	}
}
