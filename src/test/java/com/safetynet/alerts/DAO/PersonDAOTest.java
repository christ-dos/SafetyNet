package com.safetynet.alerts.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.Person;

@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {


	private PersonDAO personDAOTest;

	@Mock
	private List<Person> mockList;


	@BeforeEach
	public void initEachTest() {
		mockList = new ArrayList<>();
		personDAOTest = new PersonDAO(mockList);

		Person person0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person person1 = new Person("Lily", "Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845",
				"lily@email.com");
		Person person2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		mockList.add(person0);
		mockList.add(person1);
		mockList.add(person2);
	}

	@Test
	public void testGetListPersonsDAO_resultShouldVerifythatListContainThreePersons() {
		// GIVEN
		Person personJohnBoyd = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		// WHEN
		List<Person> resultListgetted = personDAOTest.getPersons();
		// THEN
		assertEquals(personJohnBoyd, resultListgetted.get(0));
		assertEquals(3, resultListgetted.size());
	}

	@Test
	public void testGetPersonDAO_whenPersonExistInArray_resultShouldGivenLillyCooper() {
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

	@Test
	public void testGetPersonDAO_whenNotExist_resultShouldGivenNull() {
		// GIVEN
		String firstName = "Toto";
		String lastName = "Zero";
		// WHEN
		Person getPersonResult = personDAOTest.getPerson(firstName, lastName);
		// THEN
		assertNull(getPersonResult);
	}

	@Test
	public void testSavePersonDAO_whenPersonNotExist_resultPersonAddedInEndOfArray() {
		// GIVEN

		Person personTest = new Person("Tata", "Zorro", "15 rue des Templiers", "Relvas", "6230", "000-000-0000",
				"zorrod@email.com");
		int index = mockList.indexOf(personTest);
		// WHEN
		Person resultPerson = personDAOTest.save(index, personTest);
		// THEN
		assertSame(personTest, resultPerson);
		assertEquals("Tata", resultPerson.getFirstName());
		//verify array contain 4 persons
		assertEquals(4, mockList.size());
		//verify Person was Added at the end of Array
		assertEquals(3, mockList.indexOf(resultPerson));
	}

	@Test
	public void testSavePersonDAO_whenPersonExist_shouldSavedPerssonInIndexOne () {
		// GIVEN
		Person personToSaved = new Person("Lily", "Cooper", "489 Rue des Fleurs", "Croix", "59170", "841-874-9845",
				"lily@email.com");
		Person person = personDAOTest.getPerson("Lily", "Cooper");
		int indexLilyCooper = mockList.indexOf(person);
		
		// WHEN
		Person result = personDAOTest.save(indexLilyCooper, personToSaved);
		int indexPersonSaved = mockList.indexOf(personToSaved);
		// THEN
		assertEquals(person.getFirstName(), result.getFirstName());
		//verify that the person was saved at the index of Lily Cooper
		assertEquals(indexLilyCooper, indexPersonSaved);
		//verify that new address was saved in Object Lily Cooper
		assertEquals(personToSaved.getAddress(), result.getAddress());

	}

	@Test
	public void testDeletePersonDAO_whenrCallMethodGetWithTenleyBoyd_resultShouldReturnMessageSUCESS() {
		// GIVEN
		Person personTest = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		String firstName = personTest.getFirstName();
		String lastName = personTest.getLastName();
		// WHEN
		String result = personDAOTest.delete(personTest);
		Person resultCallGetPersonAfterDelete = personDAOTest.getPerson(firstName, lastName);
		// THEN
		//verify that person Tenley Boyd not exist in Array
		assertNull(resultCallGetPersonAfterDelete);
		assertEquals("SUCESS", result);
		assertEquals(2, mockList.size());
	}
}
