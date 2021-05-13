package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

@Repository
public interface IPersonDAO {
	public List<Person> getPersons();
	public Person getPerson(String firstName, String lastName);
	public Person save(int index, Person person);
	public String delete(Person person);
}

