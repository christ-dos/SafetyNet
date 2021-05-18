package com.safetynet.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.safetynet.alerts.model.Person;

@JsonTest
public class DataJsonTest {

	@Autowired
	private JacksonTester<Person> personJson;

	@Autowired
	DataJson dataJsonTest;

	/**
	 * @Test public void
	 *       listPersonsTest_testWhenGetIndex2_shouldReturnPersonTenleyBoyd() { //
	 *       GIVEN Person personInIndex2 = new Person("Tenley", "Boyd", "1509 Culver
	 *       St", "Culver", "97451", "841-874-6512", "tenz@email.com"); // WHEN
	 *       List<Person> resultListPersons = dataJsonTest.listPersons(); // THEN
	 *       assertNotNull(resultListPersons); assertEquals(personInIndex2,
	 *       resultListPersons.get(2)); assertEquals(personInIndex2.getFirstName(),
	 *       resultListPersons.get(2).getFirstName()); }
	 */

	/**@Test
	void testDeserialize() throws Exception {
		String PersonJohnBoyd = "{\"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\" }";
		assertThat(personJson.parse(PersonJohnBoyd)).isEqualTo(
				new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"));
		assertThat(this.personJson.parseObject(PersonJohnBoyd).getFirstName()).isEqualTo("John");
	}*/
}
