package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.PersonDataJsonDAO;
import com.safetynet.alerts.model.Person;
/**
 * A class that implemente the methods CRUD
 * 
 * @author Christine Duarte
 *
 */
@Service
public class PersonService {
	
	@Autowired
	private PersonDataJsonDAO personDataJsonsDao;
	
	
	public List<Person> getListPersons() {
		
		//Person person = new Person("Christine","Duarte","22 rue de Tourcoing","Wasquehal","59290", "0320265148","Christine@hoitmailk.fr");
		
		return personDataJsonsDao.getListPersons();
		
	}
	
	public List<Person> addPersonToFile(Person person) {
		
		List<Person> persons = personDataJsonsDao.save(person);
		return persons;
		
	}

}
