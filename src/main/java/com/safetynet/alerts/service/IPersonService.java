package com.safetynet.alerts.service;

import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.model.Person;

public interface IPersonService {
	public Person getPerson(String firstName, String lastName) throws EmptyFieldsException;
	public Person addPerson(Person person) ;
	public String deletePerson(String firstName, String lastName);
	public Person updatePerson(Person person);

}