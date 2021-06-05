package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.DisplayPartialPerson;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;
import com.safetynet.alerts.exceptions.AddressNotFoundException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that manage the urls to display informations of persons
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonInformationService implements IPersonInformationService {
	/**
	 * An instance of  {@link PersonService}
	 */
	@Autowired
	private IPersonService personService;
	
	/**
	 * An instance of  {@link MedicalRecordService}
	 */
	@Autowired
	private IMedicalRecordService medicalRecordService;
	
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
	 * An instance of {@link FireStationDAO}
	 */
	@Autowired
	private IFireStationDAO fireStationDAO;
	
	
	/**
	 * Method that filters the list of persons to get phone number covered by a station number
	 * 
	 * @param station - The station for which we want obtained the phones
	 * @return the list filtered containing the phones
	 */
	@Override
	public List<String> getPhoneAlertResidentsCoveredByStation(String station) {
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if(listAddressCoveredByFireStation == null) {
			log.error("PersonInformationService - FireStation not found with station number : " + station);
			throw new FireStationNotFoundException("The FireStation number not found");
		}
		// collect persons by the list of addresses covered by station
		List<Person> listPersonCoveredByStation = personDAO.getPersonsByListAdresses(listAddressCoveredByFireStation);
		List<String> listPhoneResidents = listPersonCoveredByStation.stream()
				.map(person -> person.getPhone()).collect(Collectors.toList());
		log.info("PersonInformationService - The list of phone of residents by the station number : " + station + " has been requested");
		return listPhoneResidents;
	}
	
	/**
	 * Method that get the list of persons covered by station number and displaying
	 * a counter for adults and for childs
	 * 
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the counter of child and
	 *         adult
	 */
	@Override
	public PersonsCoveredByStation getPersonCoveredByFireStation(String station){
		// get addresses covered by fireStation
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if(listAddressCoveredByFireStation == null) {
			log.error("PersonInformationService - FireStation not found with station number : " + station);
			throw new FireStationNotFoundException("The FireStation number not found");
		}
		
		// collect persons by the list of addresses covered by station
		List<Person> listPersonCoveredByStation = personDAO.getPersonsByListAdresses(listAddressCoveredByFireStation);
		List<MedicalRecord> listMedicalRecordCoveredByStation = medicalRecordDAO
				.getListMedicalRecordByListOfPerson(listPersonCoveredByStation);
		
		int adultCouter = 0;
		int childCounter = 0;
		DateUtils dateUtils = new DateUtils();
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByStation) {
			if (DateUtils.isAdult(dateUtils.getAge(medicalRecord.getBirthDate()))) {
				adultCouter++;
			} else {
				childCounter++;
			}
		}
		
		List<DisplayPartialPerson> listPersonDTO = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			DisplayPartialPerson personDTO = new DisplayPartialPerson(person.getFirstName(), person.getLastName(), person.getAddress(),
					person.getPhone());
			listPersonDTO.add(personDTO);
		}
		PersonsCoveredByStation displayingListPersonsCoveredByStation = new PersonsCoveredByStation(
				listPersonDTO, adultCouter, childCounter);
		log.info("PersonInformationService - List of persons covered by station number: " + station);
		
		return displayingListPersonsCoveredByStation;
	}
	

	/**
	 * Method that get person informations and medicalRecord with the firstName and lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and the medical history of person
	 */
	@Override
	public PersonInfoDisplaying getPersonInformation(String firstName, String lastName) {
		Person personInfo = personService.getPerson(firstName, lastName);
		MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecord(firstName, lastName);
		DateUtils dateUils = new DateUtils();
		Integer agePerson = dateUils.getAge(medicalRecordPerson.getBirthDate());
		
		PersonInfoDisplaying personInfoDTO = 
				new PersonInfoDisplaying(personInfo.getFirstName(), personInfo.getLastName(), personInfo.getAddress(), agePerson, personInfo.getEmail(), new ArrayList<>(medicalRecordPerson.getMedications()), new ArrayList<>(medicalRecordPerson.getAllergies()));
		log.info("PersonInformationService - displaying person informations for: " + firstName + " " + lastName);
		return personInfoDTO;
		
	}
	
	/**
	 * Method which get the list of childs and adults that living same address
	 * 
	 * @param address - A String containing the address of person
	 * @return An arrayList with childs and other arrayList containing adults living in same address
	 */
	@Override
	public ChildAlertDisplaying getChildAlertList(String address){
		List<Person> listPersons = personDAO.getPersons();
		List<Person> ListPersonByAddess = new ArrayList<>();
		for(Person person : listPersons) {
			if(person.getAddress().equals(address)){
				ListPersonByAddess.add(person);
			}
		}
		if(ListPersonByAddess.isEmpty()){
			log.error("PersonInformationService - Address not found: " + address);
			throw new AddressNotFoundException("Address not found");
		}
		
		List<MedicalRecord> listMedicalRecordCoveredByAddress = medicalRecordDAO
				.getListMedicalRecordByListOfPerson(ListPersonByAddess);
		List<DisplayPartialPerson> listAdultsByAddress = new ArrayList<>();
		List<DisplayPartialPerson> listChildsByAddress = new ArrayList<>();
		DateUtils dateUtils = new DateUtils();
		for(MedicalRecord medicalRecord : listMedicalRecordCoveredByAddress) {
			Integer age = dateUtils.getAge(medicalRecord.getBirthDate());
			
		}
		System.out.println(ListPersonByAddess.toString());
		return null;
		
	}

	
	
}
