package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.Person;

public interface IPersonService {

	List<Person> getListPersons();

	Person getPerson(String firstName, String lastName) throws EmptyFieldsException;

	Person addPerson(Person person) throws EmptyFieldsException;

	String deletePerson(String firstName, String lastName);

	Person updatePerson(Person person) throws EmptyFieldsException;

}