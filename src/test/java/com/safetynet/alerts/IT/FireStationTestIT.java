package com.safetynet.alerts.IT;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.safetynet.alerts.controller.FireStationControllerTest;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;

/**
 * Class integration tests of FireStation entity  which verify that all classes works correctly together
 * to test the entity {@link FireStation}
 * 
 * @author Christine Duarte
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FireStationTestIT {
	/**
	 * An instance of {@link MockMvc} that permit simulate a request HTTP
	 */
	@Autowired
	private MockMvc mockMvcFireStation;
	
	/**
	 * Method that test request Get when fireStation exist then the status of the request isOk
	 * and the address of the fireStation :"951 LoneTree Rd"
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetFireStationExist_whenInputAddress951LoneTreeRd_thenReturnStatusIsOK() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvcFireStation.perform(get("/firestation/address?address=951 LoneTree Rd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.address", is("951 LoneTree Rd")))
		.andExpect(jsonPath("$.station", is("2")))
		.andDo(print());
	}
	
	/**
	 * Method that test requestGet when fireStation not exist 
	 * then throw a {@link FireStationNotFoundException} and the status of the request isNotFound
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRequestGetFireStationNotExist_whenInputaddress95FlowerRd_thenThrowFireStationNotFoundException() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvcFireStation.perform(get("/firestation/address?address=95 Flower Rd"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message", is("The FireStation not found, please try again")))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
	    .andExpect(result -> assertEquals("The FireStation not found", result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	/**
	 * Method that test requestGet when input address is empty 
	 * then throw a {@link EmptyFieldsException}
	 * and the status isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestGetFireStation_whenInputAddressIsEmpty_thenReturnAnEmptyFieldsException() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvcFireStation.perform(get("/firestation/address?address="))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message", is("Field cannot be empty")))
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyFieldsException))
	    .andExpect(result -> assertEquals("Field cannot be empty", result.getResolvedException().getMessage()))
		.andDo(print());
	}
	
	/**
	 * Method that test requestPost when fireStation to save already exist then throw
	 * {@link FireStationAlreadyExistException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenFireStationAlreadyExist_thenThrowFireStationAlreadyExistException() throws Exception {
		//GIVEN
		FireStationControllerTest fireStationControllerTest = new FireStationControllerTest();
		FireStation fireStaionToAddExist = new FireStation("2", "29 15th St");
		//WHEN
		//THEN
		 mockMvcFireStation.perform(MockMvcRequestBuilders
				.post("/firestation")
				.content(fireStationControllerTest.asJsonString(fireStaionToAddExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("The FireStation that we try to save already exist, please proceed to an update")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationAlreadyExistException))
			    .andExpect(result -> assertEquals("The FireStation that we try to save already Exist", result.getResolvedException().getMessage()))
				.andDo(print());
	}
	
	/**
	 * Method that test requestPost when fireStation to save not exist then return
	 * fireStation saved
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestPost_whenFireStationNotExist_shouldSaveFireStation() throws Exception {
		//GIVEN
		FireStationControllerTest fireStationControllerTest = new FireStationControllerTest();
		FireStation fireStaionToAddNotExist = new FireStation("5", "25 Blood St");
		//WHEN
		//THEN
		 mockMvcFireStation.perform(MockMvcRequestBuilders
				.post("/firestation")
				.content(fireStationControllerTest.asJsonString(fireStaionToAddNotExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("25 Blood St")))
				.andExpect(jsonPath("$.station", is("5")))
				.andDo(print());
	}
	
	/**
	 * Method that test requestDelete when fireStation to delete exist 
	 * then return a String "SUCCESS" to confirm the deletion
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenFireStationExistWithAddress947ERoseDr_thenReturnMessageSUCCESS() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvcFireStation.perform(delete("/firestation?address=947 E. Rose Dr"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("SUCCESS")))
		.andDo(print());
		
		mockMvcFireStation.perform(get("/firestationAddress?address=947 E. Rose Dr"))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.address").doesNotExist())
		.andExpect(jsonPath("$.station").doesNotExist())
		.andDo(print());
	}
	
	/**
	 * Method that test requestDelete when fireStation to delete not exist 
	 * then return a String "FireStation cannot be deleted" 
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequestDelete_whenFireStationNotExist__thenReturnMessageFireStationCannotBeDeleted() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvcFireStation.perform(delete("/firestation?address=5 Portugal Rd"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("FireStation cannot be deleted")))
		.andDo(print());
	}
	
	/**
	 * Method that test requestPut when fireStation to update exist 
	 * then return fireStation with the field station updated with "5"
	 * 
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenTheFireStationExistAndfieldStationIsUpdated_thenVerifyThatFieldStationUpadtedWithFive() throws Exception {
		//GIVEN
		FireStationControllerTest fireStationControllerTest = new FireStationControllerTest();
		FireStation fireStationToUpdateExist = new FireStation("15","748 Townings Dr");
		//WHEN
		//THEN
		mockMvcFireStation.perform(MockMvcRequestBuilders
				.put("/firestation")
				.content(fireStationControllerTest.asJsonString(fireStationToUpdateExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address", is("748 Townings Dr")))
				.andExpect(jsonPath("$.station", is("15")))
				.andDo(print());
	}
	
	/**
	 * Method that test requestPut when fireStation to update not exist 
	 * then throw a {@link FireStationNotFoundException}
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenFireStationToUpdateNotExist_thenThrowFireStationNotFoundException() throws Exception {
		//GIVEN
		FireStationControllerTest fireStationControllerTest = new FireStationControllerTest();
		FireStation fireStationToUpdateNotExist = new FireStation("5","89 AltoVallee St");
		//WHEN
		//THEN
		mockMvcFireStation.perform(MockMvcRequestBuilders
				.put("/firestation")
				.content(fireStationControllerTest.asJsonString(fireStationToUpdateNotExist))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("The FireStation not found, please try again")))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof FireStationNotFoundException))
			    .andExpect(result -> assertEquals("FireStation not found" , result.getResolvedException().getMessage()))
				.andDo(print());
	}
	/**
	 * Method that test requestPut when input field is not valid then throw
	 * {@link MethodArgumentNotValidException} must not be blank and the status
	 * isBadRequest
	 *
	 * @throws Exception
	 */
	@Test
	public void testRequetePut_whenInputFieldIsInvalid_shouldReturnMethodArgumentNotValidExceptionMustNotBeBlank() throws Exception {
		//GIVEN
		FireStationControllerTest fireStationControllerTest = new FireStationControllerTest();
		FireStation fireStaionToUpdateInvalidField = new FireStation("2", "");
		//WHEN
		//THEN
		mockMvcFireStation.perform(MockMvcRequestBuilders.put("/firestation")
				.content(fireStationControllerTest.asJsonString(fireStaionToUpdateInvalidField))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors", hasItem("must not be blank")))
				.andDo(print());
	}
}
