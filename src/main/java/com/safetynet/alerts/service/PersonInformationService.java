package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.DTO.ChildAlertDisplaying;
import com.safetynet.alerts.DTO.PartialPerson;
import com.safetynet.alerts.DTO.PersonChildAlert;
import com.safetynet.alerts.DTO.PersonFire;
import com.safetynet.alerts.DTO.PersonFireDisplaying;
import com.safetynet.alerts.DTO.PersonFlood;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
import com.safetynet.alerts.DTO.PersonsCoveredByStation;
import com.safetynet.alerts.exceptions.AddressNotFoundException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
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
	 * An instance of {@link PersonService}
	 */
	@Autowired
	private IPersonService personService;

	/**
	 * An instance of {@link MedicalRecordService}
	 */
	@Autowired
	private IMedicalRecordService medicalRecordService;

	/**
	 * An instance of {@link personDAO}
	 */
	@Autowired
	private IPersonDAO personDAO;

	/**
	 * An instance of {@link MedicalRecordDAO}
	 */
	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;

	/**
	 * An instance of {@link FireStationDAO}
	 */
	@Autowired
	private IFireStationDAO fireStationDAO;

	/**
	 * Method that filters the list of persons to get phone number covered by a
	 * station number
	 * 
	 * @param station - The station for which we want obtained the phones
	 * @return the list filtered containing the phones
	 */
	@Override
	public List<String> getPhoneAlertResidentsCoveredByStation(String station) {
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if (listAddressCoveredByFireStation == null) {
			log.error("Service - FireStation not found with station number: " + station);
			throw new FireStationNotFoundException("The FireStation number not found");
		}
		// collect persons by the list of addresses covered by station
		List<Person> listPersonCoveredByStation = personDAO.getPersonsByListAdresses(listAddressCoveredByFireStation);
		List<String> listPhoneResidents = listPersonCoveredByStation.stream().map(person -> person.getPhone())
				.collect(Collectors.toList());
		log.info("PersonInformationService - The list of phone of residents by the station number: " + station
				+ " has been requested");
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
	public PersonsCoveredByStation getPersonCoveredByFireStation(String station) {
		// get addresses covered by fireStation
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if (listAddressCoveredByFireStation == null) {
			log.error("Service - FireStation not found with station number: " + station);
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

		List<PartialPerson> listOfPartialPerson = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			PartialPerson personDTO = new PartialPerson(person.getFirstName(), person.getLastName(),
					person.getAddress(), person.getPhone());
			listOfPartialPerson.add(personDTO);
		}
		PersonsCoveredByStation displayingListPersonsCoveredByStation = new PersonsCoveredByStation(listOfPartialPerson,
				adultCouter, childCounter);
		log.info("Service - List of persons covered by station number: " + station);

		return displayingListPersonsCoveredByStation;
	}

	/**
	 * Method that get person informations and medicalRecord with the firstName and
	 * lastName
	 * 
	 * @param firstName - A String containing the firstName of the person
	 * @param lastName  - A String containing the lastName of the person
	 * @return Informations of person fistName, lastName, address, age, email and
	 *         the medical history of person
	 */
	@Override
	public PersonInfoDisplaying getPersonInformation(String firstName, String lastName) {
		Person personInfo = personService.getPerson(firstName, lastName);
		MedicalRecord medicalRecordPerson = medicalRecordService.getMedicalRecord(firstName, lastName);
		DateUtils dateUils = new DateUtils();
		Integer agePerson = dateUils.getAge(medicalRecordPerson.getBirthDate());

		PersonInfoDisplaying personInformation = new PersonInfoDisplaying(personInfo.getFirstName(),
				personInfo.getLastName(), personInfo.getAddress(), agePerson, personInfo.getEmail(),
				new ArrayList<>(medicalRecordPerson.getMedications()),
				new ArrayList<>(medicalRecordPerson.getAllergies()));
		log.info("Service - displaying person informations for: " + firstName + " " + lastName);
		return personInformation;
	}

	/**
	 * Method which get the list of childs and adults that living same address
	 * 
	 * @param address - A String containing the address of person
	 * @return An arrayList with childs and other arrayList containing adults living
	 *         in same address
	 */
	@Override
	public ChildAlertDisplaying getChildAlertList(String address) {
		List<Person> listPersons = personDAO.getPersons();
		List<Person> ListPersonByAddess = new ArrayList<>();
		for (Person person : listPersons) {
			if (person.getAddress().equalsIgnoreCase(address)) {
				ListPersonByAddess.add(person);
			}
		}
		if (ListPersonByAddess.isEmpty()) {
			log.error("Service - Address not found: " + address);
			throw new AddressNotFoundException("Address not found exception");
		}

		List<MedicalRecord> listMedicalRecordCoveredByAddress = medicalRecordDAO
				.getListMedicalRecordByListOfPerson(ListPersonByAddess);

		List<PersonChildAlert> listAdultsByAddress = new ArrayList<>();
		List<PersonChildAlert> listChildsByAddress = new ArrayList<>();
		DateUtils dateUtils = new DateUtils();
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByAddress) {
			Integer age = dateUtils.getAge(medicalRecord.getBirthDate());
			PersonChildAlert personChildAlert = new PersonChildAlert(medicalRecord.getFirstName(),
					medicalRecord.getLastName(), age);
			if (DateUtils.isAdult(age)) {
				listAdultsByAddress.add(personChildAlert);
			} else {
				listChildsByAddress.add(personChildAlert);
			}
		}

		ChildAlertDisplaying ChildAlertDisplaying = new ChildAlertDisplaying(listChildsByAddress, listAdultsByAddress);
		log.info("Service -  In Address: " + address + ", living childs: " + listChildsByAddress.size() + ", adults: "
				+ listAdultsByAddress.size());

		return ChildAlertDisplaying;
	}

	/**
	 * Method that get a list of persons covered by a list of station number the
	 * list of person is grouping by address of households should return firstName,
	 * lastName, phone, age and medical history
	 * 
	 * @param stations - a list containing station number
	 * @return A map with the list of persons covered by the list of station number
	 *         and persons are grouping by address
	 */
	public Map<String, List<PersonFlood>> getHouseHoldsCoveredByFireStation(List<String> stations) {
		List<String> stationsAddress = new ArrayList<>();
		for (String station : stations) {
			List<String> addresses = fireStationDAO.getAddressesCoveredByStationNumber(station);
			if (addresses != null) {
				for (String address : addresses) {
					stationsAddress.add(address);
				} 
			}
		}
		if (stationsAddress.size() == 0) {
			log.error("Service - FireStations not found with stations number: " + stations);
			throw new FireStationNotFoundException("FireStations number not found");
		}
		List<Person> listPersonsCoveredByStations = personDAO.getPersonsByListAdresses(stationsAddress);
		List<MedicalRecord> medicalRecordsCoveredByStations = medicalRecordDAO
				.getListMedicalRecordByListOfPerson(listPersonsCoveredByStations);

		List<PersonFlood> listPersonsFlood = new ArrayList<>();
		for (Person person : listPersonsCoveredByStations) {
			PersonFlood PersonFlood = new PersonFlood(person.getFirstName(), person.getLastName(), null, null,
					person.getAddress(), person.getPhone(), null);
			listPersonsFlood.add(PersonFlood);
		}
		for (int i = 0; i < medicalRecordsCoveredByStations.size(); i++) {
			DateUtils dateUtils = new DateUtils();
			Integer age = dateUtils.getAge(medicalRecordsCoveredByStations.get(i).getBirthDate());
			listPersonsFlood.get(i).setAge(age);
			listPersonsFlood.get(i).setMedication(medicalRecordsCoveredByStations.get(i).getMedications());
			listPersonsFlood.get(i).setAllergies(medicalRecordsCoveredByStations.get(i).getAllergies());
		}

		Map<String, List<PersonFlood>> personGroupingByAddress = listPersonsFlood.stream()
				.collect(Collectors.groupingBy(PersonFlood::getAddress));
		log.info("Service - Flood list of persons grouping by address displaying for station number: " + stations);
		return personGroupingByAddress;
	}
	
	/**
	 * Method that get a list of persons living in same address and given firstName, lastName, phone, age 
	 * and medical history
	 * @param address - A String containing address of person
	 * @return A list of persons and the station number that covers the households
	 */
	public PersonFireDisplaying getPersonsFireByAddress(String address) {
		List<Person> listPersonsInSameAddress = personDAO.getListPersonByAddress(address);
		if(listPersonsInSameAddress.isEmpty()) {
			log.error("Service - Address not found: " + address);
			throw new AddressNotFoundException("Address not found");
		}
		
		FireStation fireStationThatCoversAddress = fireStationDAO.get(address);
		List<MedicalRecord> listMedicalRecordPersonInAddress = medicalRecordDAO.getListMedicalRecordByListOfPerson(listPersonsInSameAddress);
		List<PersonFire> personsFire = new ArrayList<>();
		for(Person person : listPersonsInSameAddress) {
			PersonFire personFire = new PersonFire(person.getFirstName(), person.getLastName(), person.getPhone(), null, null, null);
			personsFire.add(personFire);
		}
		for(int i = 0; i < listMedicalRecordPersonInAddress.size(); i++) {
			DateUtils dateUtils = new DateUtils();
			Integer age = dateUtils.getAge(listMedicalRecordPersonInAddress.get(i).getBirthDate());
			personsFire.get(i).setAge(age);
			personsFire.get(i).setMedication(listMedicalRecordPersonInAddress.get(i).getMedications());
			personsFire.get(i).setAllergies(listMedicalRecordPersonInAddress.get(i).getAllergies());
		}
		
		PersonFireDisplaying personFireDisplaying = new PersonFireDisplaying(personsFire, fireStationThatCoversAddress.getStation());
		System.out.println(personFireDisplaying);
		log.debug("Service - list of person fire living in: " + address);
		return personFireDisplaying;
	}
}
