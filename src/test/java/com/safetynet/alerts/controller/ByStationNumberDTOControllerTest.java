package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DAO.PersonDAO;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.ListPersonByStationNumberDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonDTO;
import com.safetynet.alerts.model.PhoneAlertDTO;
import com.safetynet.alerts.service.ByStationNumberDTOService;

/**
 * A class which test {@link ByStationNumberDTOController}
 * 
 * @author Christine Duarte
 *
 */
@WebMvcTest(ByStationNumberDTOController.class)
@ExtendWith(MockitoExtension.class)
public class ByStationNumberDTOControllerTest {
	
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcByStationNumberDTO;
	
	/**
	 * An instance of {@link ByStationNumberDTOService}
	 * 
	 */
	@MockBean
	private ByStationNumberDTOService byStationDTOServiceMock;
	
	/**
	 * An instance of {@link medicalRecordDAOMock}
	 * 
	 */
	@MockBean
	private MedicalRecordDAO medicalRecordDAOMock;
	
	/**
	 * An instance of {@link fireStationDAOMock}
	 * 
	 */
	@MockBean
	private FireStationDAO fireStationDAOMock;
	
	/**
	 * An instance of {@link PersonDAOMock}
	 * 
	 */
	@MockBean
	private PersonDAO personDAOMock;
	
	/**
	 * A mock of the arraysList of {@link FireStation}
	 */
	@Mock
	private List<FireStation> mockListFireStation;

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
	 * A mock of the arraysList of String containing Addresses of person covered by Station number
	 */
	@Mock
	private List<String> mockListAddress;
	
	/**
	 * An instance of {@link ListPersonByStationNumberDTO}
	 */
	private ListPersonByStationNumberDTO mockPersonResultByStationNumberDTO;
	
	/**
	 * Method that create a mocks of the ArrayLists mockListAddress, mockListFireStation, mockList,
	 * mockListMedicalRecord 
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		mockListAddress = new ArrayList<>();
		mockListAddress.add("1509 Culver St");
		mockListAddress.add("834 Binoc Ave");
		mockListAddress.add("748 Townings Dr");
		
		mockListFireStation = new ArrayList<>();
		FireStation fireStationIndex0 = new FireStation("3", "1509 Culver St");
		FireStation fireStationIndex1 = new FireStation("2", "29 15th St");
		FireStation fireStationIndex2 = new FireStation("3", "834 Binoc Ave");
		FireStation fireStationIndex3 = new FireStation("3", "748 Townings Dr");
		mockListFireStation.add(fireStationIndex0);
		mockListFireStation.add(fireStationIndex1);
		mockListFireStation.add(fireStationIndex2);
		mockListFireStation.add(fireStationIndex3);

		mockList = new ArrayList<>();
		Person index0 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		Person index1 = new Person("Tessa", "Carman","834 Binoc Ave","Culver","97451", "841-874-6512", "tenz@email.com");
		Person index3 = new Person("Foster", "Shepard", "748 Townings Dr", "Culver", "97451", "841-874-6544",
				"jaboyd@email.com");
		mockList.add(index0);
		mockList.add(index1);
		mockList.add(index3);

		mockListMedicalRecord = new ArrayList<>();
		MedicalRecord indexMRecord0 = new MedicalRecord("John", "Boyd", "03/06/1984",
				new ArrayList<>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg")),
				new ArrayList<>(Arrays.asList("nillacilan")));
		MedicalRecord indexMRecord1 = new MedicalRecord("Tessa", "Carman", "02/18/2012", new ArrayList<>(),
				new ArrayList<>());
		MedicalRecord indexMRecord2 = new MedicalRecord("Foster", "Shepard", "01/08/1980",
				new ArrayList<>(Arrays.asList()), new ArrayList<>());
		mockListMedicalRecord.add(indexMRecord0);
		mockListMedicalRecord.add(indexMRecord1);
		mockListMedicalRecord.add(indexMRecord2);
		
		mockPersonResultByStationNumberDTO = new ListPersonByStationNumberDTO(
				new ArrayList<>(Arrays.asList(new PersonDTO("John", "Boyd", "1509 Culver St", "841-874-6512"),
						new PersonDTO("Tessa", "Carman", "834 Binoc Ave", "841-874-6512"),
						new PersonDTO("Foster", "Shepard", "748 Townings Dr", "841-874-6544"))),
				2, 1);
	}
	
	/**
	 * Method that test getAddressCoveredByFireStation when station exist the status of the request is OK
	 * and the listPersonDTO in index 0 contain a personDTO firstName and lastName is John Boyd, address is "1509 Culver St"
	 * and a adultsCounter with 2 adults
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonDTOCoveredByFireStation() throws Exception{
		// GIVEN
		String station = "3";
		
		
		when(byStationDTOServiceMock.getAddressCoveredByFireStation(station)).thenReturn(mockPersonResultByStationNumberDTO);
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		when(medicalRecordDAOMock.getListMedicalRecordByListOfPerson(mockList)).thenReturn(mockListMedicalRecord);
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/firestation?station=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPersonDTO[0].firstName", is("John"))).andExpect(jsonPath("$.listPersonDTO[0].lastName", is("Boyd")))
				.andExpect(jsonPath("$.listPersonDTO[0].address", is("1509 Culver St"))).andExpect(jsonPath("$.listPersonDTO[0].phone", is("841-874-6512")))
				.andExpect(jsonPath("$.adultsCounter", is(2)))
				.andDo(print());
	}
	
	/**
	 * Method that test getAddressCoveredByFireStation when the station number is five then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetListPersonsCoveredByStation_whenListAddressCoveredByFireStationIsNull_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		String station = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(null);
		when(byStationDTOServiceMock.getAddressCoveredByFireStation(station)).thenThrow(new FireStationNotFoundException("The FireStation number not found"));
		// WHEN

		// THEN
		mockMvcByStationNumberDTO.perform(get("/firestation?station=5")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when station exist the status of the request is OK
	 * and the list with PhoneAlertDTO  in index 0 contain a phone number "841-874-6512" of John Boyd  and in index 2 contain phone number "841-874-6544" of foster Shepard
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenFireStationNumberExist_thenReturnAListPersonDTOCoveredByFireStation() throws Exception{
		// GIVEN
		String station = "3";
		PhoneAlertDTO listPhoneCovrededByStationThree = new PhoneAlertDTO(Arrays.asList("841-874-6512", "841-874-6512", "841-874-6544"));
		when(byStationDTOServiceMock.getPhoneAlertResidentsCoveredByStation(station)).thenReturn(listPhoneCovrededByStationThree);
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(mockListAddress);
		when(personDAOMock.getPersonsByListAdresses(mockListAddress)).thenReturn(mockList);
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/phoneAlert?station=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.listPhoneAlert[0]", is("841-874-6512")))
				.andExpect(jsonPath("$.listPhoneAlert[2]", is("841-874-6544")))
				.andDo(print());
	}
	
	/**
	 * Method that test getPhoneAlertResidentsCoveredByStation when the station number is five then throw
	 * {@link FireStationNotFoundException}
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPhoneAlertResidentsCoveredByStation_whenListAddressCoveredByFireStationIsNull_thenThrowFireStationNotFoundException() throws Exception {
		// GIVEN
		String station = "5";
		when(fireStationDAOMock.getAddressesCoveredByStationNumber(station)).thenReturn(null);
		when(byStationDTOServiceMock.getPhoneAlertResidentsCoveredByStation(station)).thenThrow(new FireStationNotFoundException("The FireStation number not found"));
		// WHEN
		// THEN
		mockMvcByStationNumberDTO.perform(get("/phoneAlert?station=5")).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
				.andExpect(result -> assertEquals("The FireStation number not found",
						result.getResolvedException().getMessage()))
				.andDo(print());
	}
}
