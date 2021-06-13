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
import com.safetynet.alerts.DTO.PersonChildAlert;
import com.safetynet.alerts.DTO.PersonChildAlertDisplaying;
import com.safetynet.alerts.DTO.PersonCoveredByStation;
import com.safetynet.alerts.DTO.PersonCoveredByStationDisplaying;
import com.safetynet.alerts.DTO.PersonFireDisplaying;
import com.safetynet.alerts.DTO.PersonFlood;
import com.safetynet.alerts.DTO.PersonFloodDisplaying;
import com.safetynet.alerts.DTO.PersonInfoDisplaying;
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
	public PersonCoveredByStationDisplaying getPersonCoveredByFireStation(String station) {
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
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByStation) {
			if (DateUtils.isAdult(DateUtils.getAge(medicalRecord.getBirthDate()))) {
				adultCouter++;
			} else {
				childCounter++;
			}
		}

		List<PersonCoveredByStation> listOfPartialPerson = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			PersonCoveredByStation personDTO = new PersonCoveredByStation(person.getFirstName(), person.getLastName(),
					person.getAddress(), person.getPhone());
			listOfPartialPerson.add(personDTO);
		}
		PersonCoveredByStationDisplaying displayingListPersonsCoveredByStation = new PersonCoveredByStationDisplaying(listOfPartialPerson,
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
		Integer agePerson = DateUtils.getAge(medicalRecordPerson.getBirthDate());

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
	public PersonChildAlertDisplaying getChildAlertList(String address) {
		List<Person> listPersons = personDAO.getListPersonByAddress(address);
		if (listPersons == null) {
			log.error("Service - Address not found: " + address);
			throw new AddressNotFoundException("Address not found exception");
		}

		List<MedicalRecord> listMedicalRecordCoveredByAddress = medicalRecordDAO
				.getListMedicalRecordByListOfPerson(listPersons);

		List<PersonChildAlert> listAdultsByAddress = new ArrayList<>();
		List<PersonChildAlert> listChildsByAddress = new ArrayList<>();
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByAddress) {
			Integer age = DateUtils.getAge(medicalRecord.getBirthDate());
			PersonChildAlert personChildAlert = new PersonChildAlert(medicalRecord.getFirstName(),
					medicalRecord.getLastName(), age);
			if (DateUtils.isAdult(age)) {
				listAdultsByAddress.add(personChildAlert);
			} else {
				listChildsByAddress.add(personChildAlert);
			}
		}

		PersonChildAlertDisplaying ChildAlertDisplaying = PersonChildAlertDisplaying.builder().
				listChild(listChildsByAddress).
				listOtherPersonInHouse(listAdultsByAddress).build();
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
	public List<PersonFloodDisplaying> getHouseHoldsCoveredByFireStation(List<String> stations) {
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
		List<List<Person>> listPersonsGroupedByAddress= new ArrayList<>();
		for(String address : stationsAddress) {
			List<Person> listPersonByAddress = personDAO.getListPersonByAddress(address);
			listPersonsGroupedByAddress.add(listPersonByAddress) ;
		}
		System.out.println("listPersonsGroupedByAddress" + listPersonsGroupedByAddress);
		List<PersonFlood> listPersonsFlood = new ArrayList<>();
		List<PersonFloodDisplaying> listPersonFlooDisplaying = new ArrayList<>();
		List<List<PersonFlood>> listPersonsFloodGroupedByAddress = new ArrayList<>();
		
		
		List<Person> listPersonsCoveredByStations = personDAO.getPersonsByListAdresses(stationsAddress);

		
		for (Person person : listPersonsCoveredByStations) {
			//List<Person> listPersonsByAddress = personDAO.getListPersonByAddress(stationsAddress.get(0));
			MedicalRecord medicalRecord = medicalRecordDAO.get(person.getFirstName(), person.getLastName());
			Integer age = DateUtils.getAge(medicalRecord.getBirthDate());
			/**PersonFlood personFlood = PersonFlood.builder().firstName(medicalRecord.getFirstName()).lastName(medicalRecord.getLastName())
					.medication(medicalRecord.getMedications()).allergies(medicalRecord.getAllergies())
					.phone(person.getPhone()).age(age).build();*/
			
			//listPersonsFlood.add(personFlood);
			
			//System.out.println("listPersonsByAddress" + listPersonsByAddress);
			//PersonFloodDisplaying personFloodDisplaying = PersonFloodDisplaying.builder().address(listPersonsByAddress.get(0).getAddress())
			//		.listPersonsFlood(listPersonsFlood).build();
			//listPersonFlooDisplaying.add(personFloodDisplaying);
		}
		//System.out.println(listPersonsCoveredByStations);
		/**List<Person> ListPersonByAddress = new ArrayList<>();
		for(Person person: listPersonsCoveredByStations) {
			
			MedicalRecord medicalRecord = medicalRecordDAO.get(person.getFirstName(), person.getFirstName());
			Integer age = DateUtils.getAge(medicalRecord.getBirthDate());
			Person personByAddress = personDAO.getPersonByAddress(person.getAddress());
			if(personByAddress != null) {
				ListPersonByAddress.add(personByAddress);
			}
			PersonFlood personFloodByAddress = PersonFlood.builder().firstName(medicalRecord.getFirstName()).lastName(medicalRecord.getLastName())
					.medication(medicalRecord.getMedications()).allergies(medicalRecord.getAllergies())
					.phone(person.getPhone()).age(age).build();	
			System.out.println(personFloodByAddress);
			
			
		}*/
		//System.out.println(ListPersonByAddress);
		log.info("Service - Flood list of persons grouping by address displaying for station number: " + stations);
		return listPersonFlooDisplaying;
	}
	
	/**
	 * Method that get a list of persons living in same address and given firstName, lastName, phone, age 
	 * and medical history
	 * @param address - A String containing address of person
	 * @return A list of persons and the station number that covers the households
	 */
	public PersonFireDisplaying getPersonsFireByAddress(String address) {
		List<Person> listPersonsInSameAddress = personDAO.getListPersonByAddress(address);
		if(listPersonsInSameAddress == null) {
			log.error("Service - Address not found: " + address);
			throw new AddressNotFoundException("Address not found");
		}
		
		List<PersonFlood> personsFlood = new ArrayList<>();
		for(Person person : listPersonsInSameAddress) {
			MedicalRecord medicalRecord = medicalRecordDAO.get(person.getFirstName(), person.getLastName());
			Integer age = DateUtils.getAge(medicalRecord.getBirthDate());
			PersonFlood personFlood = PersonFlood.builder().firstName(medicalRecord.getFirstName()).lastName(medicalRecord.getLastName())
			.medication(medicalRecord.getMedications()).allergies(medicalRecord.getAllergies())
			.phone(person.getPhone()).age(age).build();
			//PersonFire personFire = new PersonFire(medicalRecord.getFirstName(), medicalRecord.getLastName(), person.getPhone(), age, medicalRecord.getMedications(), medicalRecord.getAllergies());
			personsFlood.add(personFlood);
		}

		FireStation fireStationThatCoversAddress = fireStationDAO.get(address);
		PersonFireDisplaying personFireDisplaying = new PersonFireDisplaying(personsFlood, fireStationThatCoversAddress.getStation());
		log.debug("Service - list of person fire living in: " + address);
		return personFireDisplaying;
	}
}
