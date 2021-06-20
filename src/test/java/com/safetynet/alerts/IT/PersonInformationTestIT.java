package com.safetynet.alerts.IT;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.exceptions.AddressNotFoundException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.exceptions.PersonNotFoundException;

/**
 * Class integration tests of PersonInfoDTO which verify that all classes works
 * correctly together to test the class {@link PersonInformation}
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonInformationTestIT {

	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Method that test request getListPersonsCoveredByStation when station number
	 * exist the status of the request is OK and the listPersonDTO in index 0
	 * contain a personDTO firstName and lastName is Peter Duncan, address is "644
	 * Gershwin Cir" and a adultsCounter with 5 adults and a childsCounter with 1
	 * child
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenFireStationNumberExistIsOne_thenReturnAListPersonCoveredByFireStationWithPeterDuncan()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/firestation?stationNumber=4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPartialPersons[0].firstName", is("Tony")))
				.andExpect(jsonPath("$.listPartialPersons[0].lastName", is("Cooper")))
				.andExpect(jsonPath("$.listPartialPersons[0].address", is("112 Steppes Pl")))
				.andExpect(jsonPath("$.listPartialPersons[0].phone", is("841-874-6874")))
				.andExpect(jsonPath("$.adultsCounter", is(4))).andExpect(jsonPath("$.childsCounter", is(0)))
				.andDo(print());
	}

	/**
	 * Method that test getListPersonsCoveredByStation when the Station Number is
	 * five and not exist then throw {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenStationNumberIsFive_thenThrowFireStationNotFoundException()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/firestation?stationNumber=9")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request GetPhoneAlertResidentsCoveredByStation when station
	 * number exist the status of the request is OK and the list of phone in index 0
	 * contain "841-874-6512", and in index 10 contain "841-874-6741"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonCoveredByFireStation()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/phoneAlert?station=4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("841-874-6874"))).andExpect(jsonPath("$.[3]", is("841-874-9888")))
				.andDo(print());
	}

	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when the station
	 * number is five then throw {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenStationNumberIsFive_thenThrowFireStationNotFoundException()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/phoneAlert?station=0")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request to GetPersonInfo when person exist fistName John and
	 * lastName Boyd then return informations of person: "John", "Boyd", "1509
	 * Culver St", 37,"jaboyd@email.com" and medical history of person
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonIsJohnBoydAndExist_thenReturnPersonInformationJohnBoyd() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/personinfo?firstName=Kendrik&lastName=Stelzer")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Kendrik"))).andExpect(jsonPath("$.lastName", is("Stelzer")))
				.andExpect(jsonPath("$.address", is("947 E. Rose Dr")))
				.andExpect(jsonPath("$.medication[0]", is("noxidian:100mg")))
				.andExpect(jsonPath("$.allergies").isEmpty()).andExpect(jsonPath("$.age", is(7))).andDo(print());
	}

	/**
	 * Method that test request to GetPersonInfo when person not exist fistName Lily
	 * and lastName Sacha then throw a PersonNotFoundException
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonInfo_whenPersonNotExist_thenThrowPersonNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/personinfo?firstName=Lily&lastName=Sacha")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException))
				.andExpect(result -> assertEquals("Person not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request to getChildAlertList when address is "1509 Culver
	 * St" then return a list with childs : Tenley and Roger Boyd and a list with
	 * adults: John, Jacob, Felicia
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetChildAlertList_whenAddressIs1509CulverStAndExist_thenReturnAListWithRogerAndTenleyBoydandAListWithJohnJacobAndFeliciBoyd()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/childAlert?address=1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listChild.[1].firstName", is("Roger")))
				.andExpect(jsonPath("$.listChild.[1].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listChild.[1].age", is(3)))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].firstName", is("John")))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].age", is(37))).andDo(print());
	}

	/**
	 * Method that test request to getChildAlertList when address is "908 73rd St"
	 * then return an empty list for childs and a list with adults: Reginold Walker
	 * and Jamie Peters
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetChildAlertList_whenAddressIs90873rdStAndExist_thenReturnAnEmptyListWithChildsAndAListOfAdultsWithReginoldWalkerAndJamiePeters()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/childAlert?address=908 73rd St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].firstName", is("Reginold")))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].lastName", is("Walker")))
				.andExpect(jsonPath("$.listOtherPersonInHouse.[0].age", is(41))).andDo(print());
	}

	/**
	 * Method that test request to getChildAlertList when address is "15 Boston St"
	 * and not exist then throw a AddressNotFoundException
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetChildAlertList_whenAddressNotExist_thenThrowAddressNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/childAlert?address=15 Boston St")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AddressNotFoundException))
				.andExpect(result -> assertEquals("Address not found exception",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request to getFloodPersonsCoveredByStationList when stations
	 * number exist and Is "1" and "4" then return a map with persons informations
	 * (firstName, lastName, phone, age, and medical history)grouping by address
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFloodPersonsCoveredByStationList_whenstationsNumberExist_thenReturnListPersonsGroupingByAddress()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/flood/stations?stations=1,4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].address", is("644 Gershwin Cir")))
				.andExpect(jsonPath("$.[0].listPersonsFlood[0].firstName", is("Peter")))
				.andExpect(jsonPath("$.[0].listPersonsFlood[0].lastName", is("Duncan")))
				.andExpect(jsonPath("$.[0].listPersonsFlood[0].age", is(20)))
				.andExpect(jsonPath("$.[0].listPersonsFlood[0].medication").isEmpty())
				.andExpect(jsonPath("$.[0].listPersonsFlood[0].allergies[0]", is("shellfish")))
				.andExpect(jsonPath("$.[3].address", is("112 Steppes Pl")))
				.andExpect(jsonPath("$.[3].listPersonsFlood[0].firstName", is("Tony")))
				.andExpect(jsonPath("$.[3].listPersonsFlood[0].lastName", is("Cooper")))
				.andExpect(jsonPath("$.[3].listPersonsFlood[0].medication.[0]", is("hydrapermazol:300mg")))
				.andExpect(jsonPath("$.[3].listPersonsFlood[0].allergies.[0]", is("shellfish")))
				.andExpect(jsonPath("$.[3].listPersonsFlood[0].age", is(27))).andDo(print());
	}

	/**
	 * Method that test request to getFloodPersonsCoveredByStationList when stations
	 * number is "8" and "9" and not exist then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFloodPersonsCoveredByStationList_whenStationsNumberNotExist_thenReturnFireStationNotFoundException()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/flood/stations?stations=8,9")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("FireStations number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}

	/**
	 * Method that test request to getPersonsFireByAddress when address exist and is
	 * "892 Downing Ct" then return a list with persons informations: firstName,
	 * lastName, phone, age, and medical history of Sophia, Warren et Zach Zemicks
	 * as well as FireStation number "2" that covers this address
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPersonsFireByAddress_whenAddressExist_thenReturnListPersonAndStationNumberThatCoversAddress()
			throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/fire?address=892 Downing Ct")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPersonFire.[0].firstName", is("Sophia")))
				.andExpect(jsonPath("$.listPersonFire[0].lastName", is("Zemicks")))
				.andExpect(jsonPath("$.listPersonFire.[0].age", is(33)))
				.andExpect(jsonPath("$.listPersonFire.[0].medication.[0]", is("aznol:60mg")))
				.andExpect(jsonPath("$.listPersonFire.[0].medication.[3]", is("terazine:500mg")))
				.andExpect(jsonPath("$.listPersonFire.[0].allergies.[0]", is("peanut")))
				.andExpect(jsonPath("$.listPersonFire.[2].firstName", is("Zach")))
				.andExpect(jsonPath("$.listPersonFire[2].lastName", is("Zemicks")))
				.andExpect(jsonPath("$.listPersonFire.[2].medication").isEmpty())
				.andExpect(jsonPath("$.listPersonFire.[2].allergies").isEmpty())
				.andExpect(jsonPath("$.listPersonFire.[2].age", is(4))).andExpect(jsonPath("$.stationNumber", is("2")))
				.andDo(print());
	}

	/**
	 * Method that test request to getPersonsFireByAddress when address is "15
	 * Backer St" and not exist then throw {@link AddressNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetFirePersonsByAddress_whenAddressNotExist_thenReturnAddressNotFoundException() throws Exception {
		// GIVEN
		// WHEN
		// THEN
		mockMvc.perform(get("/fire?address=15 Backer St")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AddressNotFoundException))
				.andExpect(result -> assertEquals("Address not found", result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
