package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.exceptions.AddressNotFoundException;
import com.safetynet.alerts.model.ChildAlertDTO;
import com.safetynet.alerts.model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the url /childAlert?address=<address>
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlertDTOService {
	
	/**
	 * An instance of  {@link personDAO}
	 */
	@Autowired
	private IPersonDAO personDAO;
	
	/**
	 * An instance of  {@link MedicalRecordDAO}
	 */
	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;
	/**
	 * Method which get the list of childs and adults that living same address
	 * 
	 * @return An arrayList with childs and other arrayList containing adults living in same address
	 */
	public ChildAlertDTO getChildAlertList(String address) {
		List<Person> listPersons = personDAO.getPersons();
		List<Person> ListPersonByAddess = new ArrayList<>();
		for(Person person : listPersons) {
			Person personGet = personDAO.getPersonByAddress(person.getAddress());
			ListPersonByAddess.add(personGet);
		}
		if(ListPersonByAddess.isEmpty()){
			throw new AddressNotFoundException("Address not found");
		}
		System.out.println(ListPersonByAddess.toString());
		return null;
		
	}

}
