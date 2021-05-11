package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.Person;

public interface IPersonService {
	Person getPerson(String firstName, String lastName) throws EmptyFieldsException;
	Person addPerson(Person person) throws EmptyFieldsException;
	String deletePerson(String firstName, String lastName);
	Person updatePerson(Person person) throws EmptyFieldsException;

}