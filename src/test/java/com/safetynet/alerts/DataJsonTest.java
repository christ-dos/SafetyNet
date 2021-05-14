package com.safetynet.alerts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.safetynet.alerts.model.Person;



//@WebMvcTest
@JsonTest
public class DataJsonTest {
	
	@Autowired
	 private JacksonTester<Person> json;
	
	@Autowired
	DataJson dataJsonTest;
	
	
	
	
	@Test
	public void listPersonsTest_testWhenGetIndex2_shouldReturnPersonTenleyBoyd(){
		//GIVEN
		Person personInIndex2 = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");
		//WHEN
		List<Person> resultListPersons = dataJsonTest.listPersons();
		//THEN
		assertNotNull(resultListPersons);
		assertEquals(personInIndex2, resultListPersons.get(2));
		assertEquals(personInIndex2.getFirstName(), resultListPersons.get(2).getFirstName());
	}

	 /** @Test
	    void testSerialize() throws Exception {
	        VehicleDetails details = new VehicleDetails("Honda", "Civic");
	        // Assert against a `.json` file in the same package as the test
	        assertThat(this.json.write(details)).isEqualToJson("expected.json");
	        // Or use JSON path based assertions
	        assertThat(this.json.write(details)).hasJsonPathStringValue("@.make");
	        assertThat(this.json.write(details)).extractingJsonPathStringValue("@.make")
	                .isEqualTo("Honda");
	    }*/

	    @Test
	    void testDeserialize() throws Exception {
	        String PersonJohn = "{\"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\" }";
	        assertThat(json.parse(PersonJohn))
	                .isEqualTo( new Person("John", "Boyd", "1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
	        assertThat(this.json.parseObject(PersonJohn).getFirstName()).isEqualTo("John");
	    }
}
