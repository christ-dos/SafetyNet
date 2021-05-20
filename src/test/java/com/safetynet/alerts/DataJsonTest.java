package com.safetynet.alerts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.safetynet.alerts.DAO.ReadFileJson;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;

/**
 * Class that test data extraction from the file data.json
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class DataJsonTest {
	/**
	 * An instance of class DataJson
	 * 
	 * @see DataJson
	 */
	private DataJson dataJsonTest;

	/**
	 * A mock of ReadFileJson
	 * 
	 * @see ReadFileJson
	 */
	@Mock
	private ReadFileJson readerMock;

	/**
	 * A mock of JsonObject
	 * 
	 * @see JsonObject
	 */
	@Mock
	private JsonObject jsonObjectMock;

	/**
	 * A mock of JsonMapper
	 * 
	 * @see JsonMapper
	 */
	@Mock
	private JsonMapper mapperMock;

	/**
	 * A mock of a list of persons
	 * 
	 */
	@Mock
	private List<Person> mockListPersons;
	
	/**
	 * A mock of a list of fireStations
	 * 
	 */
	@Mock
	private List<FireStation> mockListFireStation;

	/**
	 * Method that create the jsonObjectMock to use in the tests
	 */
	@BeforeEach
	public void setUpPerTest() {
		jsonObjectMock = Json.createObjectBuilder()
				.add("persons",
						Json.createArrayBuilder()
								.add(Json.createObjectBuilder().add("firstName", "Tenley")
										.add("lastName", "Boyd")
										.add("address", "1509 Culver St")
										.add("city", "Culver").add("zip", "97451")
										.add("phone", "841-874-6512")
										.add("email", "tenz@email.com"))
								.add(Json.createObjectBuilder()
										.add("firstName", "John")
										.add("lastName", "Boyd")
										.add("address", "1509 Culver St")
										.add("city", "Culver")
										.add("zip", "97451")
										.add("phone", "841-874-6512")
										.add("email", "jaboyd@email.com")))
				.add("firestations",
						Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
										.add("address", "1509 Culver St")
										.add("station","3"))
								.add(Json.createObjectBuilder()
										.add("address", "29 15th St")
										.add("station","2")))
				.add("medicalrecords",
						Json.createArrayBuilder()
								.add(Json.createObjectBuilder()
										.add("firstName", "John")
										.add("lastName", "Boyd")
										.add("birthdate", "03/06/1984")
										.add("medications",
												Json.createArrayBuilder()
												.add("aznol:350mg")
												.add("hydrapermazol:100mg"))
										.add("allergies", Json.createArrayBuilder()
												.add("nillacilan"))))
				.build();
	}

	/**
	 * Method that test if in the list we find person Tenley Boyd which is recorded
	 * in index 0 and if we have recovered 2 elements of our jsonObjectMock
	 */
	@Test
	public void listPersonsTest_testWhenGetIndex0_shouldReturnPersonTenleyBoyd() {
		// GIVEN
		mockListPersons = new ArrayList<>();
		mapperMock = new JsonMapper();

		Person index0 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		Person index1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index2 = new Person("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845",
				"lily@email.com");

		mockListPersons.add(index0);
		mockListPersons.add(index1);
		mockListPersons.add(index2);
		
		dataJsonTest = new DataJson(readerMock, mapperMock, mockListPersons, null,jsonObjectMock);
		when(readerMock.readJsonFile()).thenReturn(jsonObjectMock);
		Person personInIndex0 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"tenz@email.com");
		// WHEN
		List<Person> resultListPersons = dataJsonTest.listPersons();
		// THEN
		assertNotNull(resultListPersons);
		assertEquals(2, resultListPersons.size());
		assertEquals(personInIndex0, resultListPersons.get(0));
		assertEquals("Tenley", resultListPersons.get(0).getFirstName());
	}
	
	/**
	 * Method that test if in the list we find station "3" with address "1509 Culver St"
	 * in index 0 of the arrayList and if we have recovered 2 elements of our jsonObjectMock
	 */
	@Test
	public void listFireStationsTest_testWhenGetIndex0_shouldReturnFireStationthreeAndAdress1509CulverSt() {
		// GIVEN
		mockListFireStation = new ArrayList<>();
		mapperMock = new JsonMapper();

		FireStation index0 = new FireStation("3","1509 Culver St");
		FireStation index1 = new FireStation("2","29 15th St");

		mockListFireStation.add(index0);
		mockListFireStation.add(index1);
		
		FireStation fireStationInIndex0 = new FireStation("3", "1509 Culver St");
		dataJsonTest = new DataJson(readerMock, mapperMock, null, mockListFireStation,jsonObjectMock);
		when(readerMock.readJsonFile()).thenReturn(jsonObjectMock);
		// WHEN
		List<FireStation> resultListFireStations = dataJsonTest.listFireStations();
		// THEN
		assertNotNull(resultListFireStations);
		assertEquals(2, resultListFireStations.size());
		//verify that in index 0 we are the station "3" address "1509 Culver St"
		assertEquals(fireStationInIndex0, resultListFireStations.get(0));
		assertEquals("3", resultListFireStations.get(0).getStation());
	}

}
