package com.safetynet.alerts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.safetynet.alerts.DAO.ReadFileJson;
import com.safetynet.alerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Configuration
@Data
@Slf4j
public class RunnerData implements CommandLineRunner {

	
	@Autowired
	private ReadFileJson reader;
	
	
	private List<Person> persons;
	
	@Override
	public void run(String... args) throws Exception {

		persons = reader.readJsonFile("persons");
		log.info("The RunnerData is init");
		System.out.println("test du runner" + persons);
	}
	
	/**@Bean
	public List<Person> listPersons(){
		System.out.println("affichage dans listPersons" + persons);
		return getPersons();
		
	}*/

}
