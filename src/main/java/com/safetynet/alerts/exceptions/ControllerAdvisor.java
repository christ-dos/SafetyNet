package com.safetynet.alerts.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class that manage the response at the user when an exception is handle the
 * class extend ResponseEntityExceptionHandler
 * 
 * @author christine Duarte
 *
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	/**
	 * Method that return a message when an argument of the request is not valid
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message : containing the error, and the
	 *         code HttpStatus 400
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method that return a message when a PersonNotFoundException is thrown when a
	 * person is not found in the arrayList
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"Person not found", and the code
	 *         HttpStatus 404
	 */
	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<Object> handlePersonNotFoundException(PersonNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Person not found, please try again");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	/**
	 * Method that return a message when a EmptyFieldsException is thrown
	 * 
	 * when the field firstName or LastName is empty
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"The fields firstName and
	 *         lastName can not be empty", and the code HttpStatus 400
	 */
	@ExceptionHandler(EmptyFieldsException.class)
	public ResponseEntity<Object> handleEmptyFieldsException(EmptyFieldsException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Field cannot be empty");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method that return a message when a PersonAlreadyExistException is thrown
	 * 
	 * when the person we want saved already exist
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"Person already exist", and the
	 *         code HttpStatus 400
	 * 
	 */
	@ExceptionHandler(PersonAlreadyExistException.class)
	public ResponseEntity<Object> handlePersonAlreadyExistException(PersonAlreadyExistException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "The Person that we try to save already exist, please proceed to an update");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method that return a message when a FireStationAlreadyExistException is
	 * thrown
	 * 
	 * when the fireStation we want saved already exist
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"The FireStation that we try to
	 *         save already Exist", and the code HttpStatus 400
	 * 
	 */
	@ExceptionHandler(FireStationAlreadyExistException.class)
	public ResponseEntity<Object> handleFireStationAlreadyExistException(FireStationAlreadyExistException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "The FireStation that we try to save already exist, please proceed to an update");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method that return a message when a FireStationNotFoundException is thrown
	 * when a fireStation is not found in the arrayList
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"Person not found", and the code
	 *         HttpStatus 404
	 */
	@ExceptionHandler(FireStationNotFoundException.class)
	public ResponseEntity<Object> handleFireStationNotFoundException(FireStationNotFoundException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "The FireStation not found, please try again");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method that return a message when a MedicalRecordNotFoundException is thrown when a
	 * medicalRecord is not found in the arrayList
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"MedicalRecord not found, please try again!", and the code
	 *         HttpStatus 404
	 */
	@ExceptionHandler(MedicalRecordNotFoundException.class)
	public ResponseEntity<Object> handleMedicalRecordNotFoundException(MedicalRecordNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "MedicalRecord not found, please try again");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method that return a message when a MedicalRecordAlreadyExistException is
	 * thrown
	 * 
	 * when the medicalRecord we want saved already exist
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"The medicalRecord that we try to save already Exist",
	 *  and the code HttpStatus 400
	 * 
	 */
	@ExceptionHandler(MedicalRecordAlreadyExistException.class)
	public ResponseEntity<Object> handleMedicalRecordAlreadyExistException(MedicalRecordAlreadyExistException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "The medicalRecord that we try to save already exist, please proceed to an update");

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Method that return a message when a CityNotFoundException is thrown when a
	 * city is not found in the arrayList
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"The city not found, please try again!", and the code
	 *         HttpStatus 404
	 */
	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "The city not found, please try again");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Method that return a message when a AddressNotFoundException is thrown when a
	 * city is not found in the arrayList
	 * 
	 * @param ex      - the exception handle
	 * @param request - a web request
	 * @return a response entity with the message :"The city not found, please try again!", and the code
	 *         HttpStatus 404
	 */
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<Object> handleAddressNotFoundException(AddressNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Address not found, please try again");

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
}
