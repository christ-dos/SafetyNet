package com.safetynet.alerts.service;

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

import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.model.ChildAlertDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 * Class that test {@link ChildAlertDTOService} class
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class ChildAlertDTOServiceTest {
	
	/**
	 * An instance of {@link ChildAlertDTOService}
	 */
	private ChildAlertDTOService childAlertDTOService;
	
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
	 * Method that create a mock of the ArrayLists mockList, mockListMedicalRecord 
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Jacob", "Boyd","1509 Culver St","Culver","97451", "841-874-6512"
				, "drk@email.com" );
		Person index3 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person index4 = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person index5 = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person index6 = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-6544",
				"lilyd@email.com" );
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index3);
		mockList.add(index4);
		mockList.add(index5);
		mockList.add(index6);

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Jacob", "Boyd", "03/06/1989", new ArrayList<>(Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord3 = new MedicalRecord("Roger", "Boyd", "09/06/2017",
				new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("peanut")));
		MedicalRecord indexMRecord4 = new MedicalRecord("Felicia", "Boyd", "01/08/1986",
				new ArrayList<>(Arrays.asList("tetracyclaz:650mg")), new ArrayList<>(Arrays.asList("xilliathal")));
		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		mockListMedicalRecord.add(indexMRecord3);
		mockListMedicalRecord.add(indexMRecord4);

		childAlertDTOService = ChildAlertDTOService.builder()
				.personDAO(personDAOMock)
				.medicalRecordDAO(medicalRecordDAOMock).build();
		}

	
	@Test
	public void testGetChildAlertList_whenAddressExist_thenReturnListOfChildAndListOfAdultsLivingInSameAddress() {
		//GIVEN
		String address = "1509 Culver St";
		Person personJohnBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person personJacobBoyd = new Person("Jacob", "Boyd","1509 Culver St","Culver","97451", "841-874-6512"
				, "drk@email.com" );
		Person personTenleyBoyd = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"tenz@email.com");
		Person personRogerBoyd = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person personFeliciaBoyd = new Person("Felicia", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com" );
		Person personLilyCooper = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-6544",
				"lilyd@email.com" );
		when(personDAOMock.getPersons()).thenReturn(mockList);
		when(personDAOMock.getPersonByAddress(anyString())).thenReturn(personJohnBoyd, personJacobBoyd, personTenleyBoyd,
				personRogerBoyd,  personFeliciaBoyd, personLilyCooper);
		//WHEN
		ChildAlertDTO childAlertResult = childAlertDTOService.getChildAlertList(address);
		//THEN
		assertEquals("Tenley", childAlertResult.getListChild().get(0).getFirstName());
		assertEquals("Boyd", childAlertResult.getListChild().get(0).getLastName());
		
	}

}
