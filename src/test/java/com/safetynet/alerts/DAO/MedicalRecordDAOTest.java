package com.safetynet.alerts.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	 * Method that created a mock of the ArrayList mockListMedicalRecord with 3 elements
	 * 
	 * the mockList is injected at the method by a builder
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListMedicalRecord = new ArrayList<>();
		
		MedicalRecord index0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord index1 = new MedicalRecord("Lily", "Cooper", "03/06/1994", 
				new ArrayList<>(),new ArrayList<>());
		MedicalRecord index2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012", 
				new ArrayList<>(Arrays.asList()),new ArrayList<>(Arrays.asList("peanut")));
		mockListMedicalRecord.add(index0);
		mockListMedicalRecord.add(index1);
		mockListMedicalRecord.add(index2);
		System.out.println(mockListMedicalRecord);
		medicalRecordDAOTest = MedicalRecordDAO.builder()
				.listMedicalRecords(mockListMedicalRecord)
				.build();
	}
	
	/**
	 * Method that test getMedicalRecords then return a list of medicalRecords with 3 elements
	 * and verify that the medicalRecord of John Boyd is present in the list in index 0
	 * and the birthDate is "03/06/1984" 
	 * and medication saved in arrayList medications in index 1 is "hydrapermazol:100mg"
	 */
	@Test
	public void testGetListMedicalRecords_resultShouldVerifythatListContainThreeMedicalRecords() {
		// GIVEN
		MedicalRecord medicalRecordJohnBoydIndex0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),new ArrayList<>(Arrays.asList("nillacilan")));
		// WHEN
		List<MedicalRecord> resultListgetted = medicalRecordDAOTest.getMedicalRecords();
		// THEN
		assertEquals(medicalRecordJohnBoydIndex0, resultListgetted.get(0));
		assertEquals("03/06/1984", resultListgetted.get(0).getBirthDate());
		assertEquals("hydrapermazol:100mg", resultListgetted.get(0).getMedications().get(1));
		assertEquals(3, resultListgetted.size());
	}

}
