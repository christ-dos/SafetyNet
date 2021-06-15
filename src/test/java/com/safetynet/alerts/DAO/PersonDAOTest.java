package com.safetynet.alerts.DAO;

import static org.junit.Assert.assertTrue;
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

import com.safetynet.alerts.model.Person;

/**
 * Class that test {@link PersonDAO}
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {

	/**
	 * An instance of {@link PersonDAO}
	 */
	private PersonDAO personDAOTest;

	/**
	 * A mock of a arrayList that contain {@link Person}
	 */
	@Mock
	private List<Person> mockList;

	/**
	 * Method that created a mock of the ArrayList mockList with 3 elements
	 * 
	 * the mockList is injected at the method by a builder
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockList = new ArrayList<>();

		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");
		Person index2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index2);

		personDAOTest = PersonDAO.builder().listPersons(mockList).build();
	}

	/**
	 * Method that test getPersonsDAO then return a list of persons with 3 elements
	 * and verify that John Boyd is present in the list
	 */
	@Test
	public void testGetListPersonsDAO_resultShouldVerifythatListContainThreePersons() {
		// GIVEN
		Person personJohnBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		// WHEN
		List<Person> resultListgetted = personDAOTest.getPersons();
		// THEN
		assertEquals(personJohnBoyd, resultListgetted.get(0));
		// the list contain 3 elements
		assertEquals(3, resultListgetted.size());
	}

	/**
	 * Method that test getPersonDAO with firstName Lily and LastName Cooper when
	 * person exist then return Lily Cooper
	 * 
	 */
	@Test
	public void testGetPersonDAO_whenPersonExistInArray_thenReturnLilyCooper() {
		// GIVEN
		String firstName = "Lily";
		String lastName = "Cooper";
		// WHEN
		Person getPersonResult = personDAOTest.getPerson(firstName, lastName);
		// THEN
		assertEquals("Lily", getPersonResult.getFirstName());
		assertEquals("Cooper", getPersonResult.getLastName());
		assertEquals(mockList.get(1), getPersonResult);
	}

	/**
	 * Method that test getPersonDAO with firstName Toto and LastName Zero when
	 * person not exist then return null
	 * 
	 */
	@Test
	public void testGetPersonDAO_whenPersonNotExist_resultShouldReturnNull() {
		// GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		// WHEN
		Person getPersonResult = personDAOTest.getPerson(firstName, lastName);
		// THEN
		assertNull(getPersonResult);
	}

	/**
	 * Method that test getPersonDAO with address "489 Manchester St" when address
	 * exist then return Lily Cooper
	 * 
	 */
	@Test
	public void testGetPersonDAOByAddress_whenAdressExistInArray_resultShouldReturnLilyCooper() {
		// GIVEN
		String address = "489 Manchester St";
		// WHEN
		Person getPersonResult = personDAOTest.getPersonByAddress(address);
		// THEN
		assertEquals("Lily", getPersonResult.getFirstName());
		assertEquals("Cooper", getPersonResult.getLastName());
		assertEquals(mockList.get(1), getPersonResult);
	}

	/**
	 * Method that test getPersonDAOByAddress with address "25 wall St" when address
	 * not exist then return null
	 * 
	 */
	@Test
	public void testGetPersonDAOByAddress_whenAddressNotExist_resultShouldReturnNull() {
		// GIVEN
		String address = "25 wall St";
		// WHEN
		Person getPersonResult = personDAOTest.getPersonByAddress(address);
		// THEN
		assertNull(getPersonResult);
	}

	/**
	 * Method that test GetPersonsByListAdresses with a list of address "892 Downing
	 * Ct" and "947 E. Rose Dr" when addresses exist then return a list containing 2
	 * persons: Sophia Zemicks and Brian Stelzer
	 * 
	 */
	@Test
	public void testGetPersonsByListAdresses_whenListAddressIs892DowningCtAnd947ERoseDr_thenReturnAListOfPersonswithTwoPersons() {
		// GIVEN
		Person personSophiaZemicks = new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451",
				"841-874-7878", "sophd@email.com");
		Person personBrianStelzer = new Person("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784",
				"bstel@email.com");
		Person personEricCardigan = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458",
				"gramp@email.com");
		mockList.add(personSophiaZemicks);
		mockList.add(personBrianStelzer);
		mockList.add(personEricCardigan);

		List<String> mockListAddress = new ArrayList<>(Arrays.asList("892 Downing Ct", "947 E. Rose Dr"));
		// WHEN
		List<Person> listPersonsByListAddresses = personDAOTest.getPersonsByListAdresses(mockListAddress);
		// THEN
		// the list contain 2 persons
		assertEquals(2, listPersonsByListAddresses.size());
		assertEquals(personSophiaZemicks, listPersonsByListAddresses.get(0));
		assertEquals(personBrianStelzer, listPersonsByListAddresses.get(1));
	}

	/**
	 * Method that test GetPersonsByListAdresses with a list of address when
	 * addresses not exist then return an empty list
	 * 
	 */
	@Test
	public void testGetPersonsByListAdresses_whenListAddressNotExist_thenReturnAnEmptyList() {
		List<String> mockListAddress = new ArrayList<>(Arrays.asList("89 parc St", "94 Hide St"));
		// WHEN
		List<Person> listPersonsByListAddresses = personDAOTest.getPersonsByListAdresses(mockListAddress);
		// THEN
		// if the list is empty then return true
		assertTrue(listPersonsByListAddresses.isEmpty());
	}

	/**
	 * Method that test GetListPersonByAddress with address "112 Steppes Pl" when
	 * address exist then return a list containing 2 persons which living in same
	 * address: Ron Peters and Allison Boyd
	 * 
	 * 
	 */
	@Test
	public void testGetListPersonByAddress_whenAdressExistInArray_thenReturnListPersonLivingInSameAddress() {
		// GIVEN
		String address = "112 Steppes Pl";
		Person personRonPeters = new Person("Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888",
				"jpeter@email.com");
		Person personAllisonBoyd = new Person("Allison", "Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888",
				"aly@email.com");
		mockList.add(personRonPeters);
		mockList.add(personAllisonBoyd);
		// WHEN
		List<Person> getListPersonByAddress = personDAOTest.getListPersonByAddress(address);
		// THEN
		// 2 persons living in same address
		assertEquals(2, getListPersonByAddress.size());
		assertEquals(personRonPeters, getListPersonByAddress.get(0));
		assertEquals(personAllisonBoyd, getListPersonByAddress.get(1));
		assertEquals(mockList.get(4), getListPersonByAddress.get(1));
	}

	/**
	 * Method that test GetListPersonByAddress when the address not exist then
	 * return an empty list
	 * 
	 */
	@Test
	public void testGetListPersonByAddress_whenAddressNotExist_thenReturnAnEmptyList() {
		String addressNotExist = "89 parc St";
		// WHEN
		List<Person> listPersonsByAddress = personDAOTest.getListPersonByAddress(addressNotExist);
		// THEN
		// if the list is empty then return true
		assertNull(listPersonsByAddress);
	}

	/**
	 * Method that test savePersonDAO when person not exist then return the person
	 * saved and verify if the person is saved at the end of the array and verify
	 * that we are 4 elements in the arrayList
	 */
	@Test
	public void testSavePersonDAO_whenPersonNotExist_thenResultPersonAddedInEndOfArray() {
		// GIVEN
		Person personTest = new Person("Tata", "Zorro", "15 rue des Templiers", "Relvas", "6230", "000-000-0000",
				"zorrod@email.com");
		int index = mockList.indexOf(personTest);
		// WHEN
		Person resultPerson = personDAOTest.save(index, personTest);
		// THEN
		assertSame(personTest, resultPerson);
		assertEquals("Tata", resultPerson.getFirstName());
		// verify array contain 4persons
		assertEquals(4, mockList.size());
		// verify Person was Added at the end of Array
		assertEquals(3, mockList.indexOf(resultPerson));
	}

	/**
	 * Method that test savePersonDAO when person exist in arrayList then person
	 * Lily Cooper is saved at the index one and verify that the address modified is
	 * saved in arrayList
	 */
	@Test
	public void testSavePersonDAO_whenPersonExist_shouldSavedPersonInIndexOne() {
		// GIVEN
		Person personToSaveWithAddressModified = new Person("Lily", "Cooper", "489 Rue des Fleurs", "Croix", "59170",
				"841-874-9845", "lily@email.com");
		Person personLilyCooperRecordedInArray = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451",
				"841-874-9845", "lily@email.com");
		int indexLilyCooper = mockList.indexOf(personLilyCooperRecordedInArray);

		// WHEN
		Person result = personDAOTest.save(indexLilyCooper, personToSaveWithAddressModified);
		int indexPersonSavedWithAddressModified = mockList.indexOf(personToSaveWithAddressModified);
		// THEN
		assertEquals(personLilyCooperRecordedInArray.getFirstName(), result.getFirstName());
		// verify that the person was saved at the index of Lily Cooper
		assertEquals(indexLilyCooper, indexPersonSavedWithAddressModified);
		// verify that new address was saved in Object Lily Cooper
		assertEquals(personToSaveWithAddressModified.getAddress(), result.getAddress());
	}

	/**
	 * Method that test deletePersonDAO when person exist in arrayList then return a
	 * String with "SUCCESS" when the person Tenley Boyd is deleted with success and
	 * verify that the arrayList contain 2 elements after the deletion
	 */
	@Test
	public void testDeletePersonDAO_whenPersonTenleyBoydExist_thenReturnMessageSUCCESS() {
		// GIVEN
		Person personTest = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		String firstName = personTest.getFirstName();
		String lastName = personTest.getLastName();
		// WHEN
		String result = personDAOTest.delete(personTest);
		Person resultCallGetPersonAfterDelete = personDAOTest.getPerson(firstName, lastName);
		// THEN
		// verify that person Tenley Boyd not exist in ArrayList
		assertNull(resultCallGetPersonAfterDelete);
		assertEquals("SUCCESS", result);
		assertEquals(2, mockList.size());
	}
}
