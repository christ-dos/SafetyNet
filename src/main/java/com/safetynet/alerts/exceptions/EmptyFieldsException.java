package com.safetynet.alerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyFieldsException extends Exception {

	
	/**
	 * Attribute that give an IDat the exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the exceptionS
	 * 
	 * @param message - The message that is send when an exception is throw
	 */
	public EmptyFieldsException(String message) {
		super(message);
	}
	
	

}
