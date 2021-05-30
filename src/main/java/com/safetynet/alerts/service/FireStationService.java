package com.safetynet.alerts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.exceptions.EmptyFieldsException;
import com.safetynet.alerts.exceptions.FireStationAlreadyExistException;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * class service that manage the methods CRUD for FireStation entity
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FireStationService implements IFireStationService {
	/**
	 * An instance of {@link FireStationDAO}
	 * 
	 * @see FireStationDAO
	 */
	@Autowired
	private IFireStationDAO fireStationDAO;
	
	@Autowired
	private IPersonDAO personDAO;
	
	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;
	
	/**
	 * Method private that get a list of fireStations
	 * 
	 * @return An ArrayList of FireStation
	 */
	private List<FireStation> getListFireStations() {
		return fireStationDAO.getFireStations();
	}

	/**
	 * Method that get a fireStation by address
	 * 
	 * @param address - A String containing the address of fireStation
	 * @return an instance of fireStation getted
	 * @throws EmptyFieldsException         when the field address is empty
	 * @throws FireStationNotFoundException when fireStation is not found
	 */
	@Override
	public FireStation getFireStation(String address){
		if (address.isEmpty()) {
			log.error("Service - field can not be empty");
			throw new EmptyFieldsException("Field cannot be empty");
		}
		FireStation fireStationgetted = fireStationDAO.get(address);
		if (fireStationgetted == null) {
			log.error("Service - FireStation not found : address: " + address);
			throw new FireStationNotFoundException("The FireStation not found");
		}
		log.debug("Service - FireStation found :address: " + fireStationgetted.getAddress() + ", Station: "
				+ fireStationgetted.getStation());
		return fireStationgetted;
	}
	
	/**
	 * Method that get the list of persons covered by station number
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the number of child and adult
	 */
	@Override
	public List<Object> getAddressCoveredByFireStation(String stationNumber) {
		//List<FireStation> listFireStations = getListFireStations();
		List<Person> listPersons = personDAO.getPersons();
		List<MedicalRecord> listMedicalRecord = medicalRecordDAO.getMedicalRecords();
		// get addresses covered by fireStation
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(stationNumber);
				/**listFireStations.stream()
				.filter(fireStation -> fireStation.getStation().equalsIgnoreCase(stationNumber))
				.map(fireStation -> fireStation.getAddress()).collect(Collectors.toList());*/
		
		if(listAddressCoveredByFireStation.size() == 0) {
			log.error("Service - FireStation not found : station : " + stationNumber);
			throw new FireStationNotFoundException("The FireStation number not found");
		}
		// collect persons with address covered by station
		List<Person> listPersonCoveredByStation = listPersons.stream()
				.filter(person -> listAddressCoveredByFireStation.contains(person.getAddress()))
				.collect(Collectors.toList());
		
		// Parcourir la liste des personnes et faire recuperer le medicalrecord pour chaque personne
		// Appeler la methode isAdult en passant la date de naissance pris du medical record
		// Incrementer les compteurs enfants et aldutes.

		// compare firstName and lastName of medicalRecords and persons to get birthDates
		List<String> listMedicalRecordBirthDate = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			for (MedicalRecord medicalRecord : listMedicalRecord) {
				if (person.getFirstName().equals(medicalRecord.getFirstName())
						&& person.getLastName().equals(medicalRecord.getLastName())) {
					listMedicalRecordBirthDate.add(medicalRecord.getBirthDate());
				}
			}
		}
		Integer adultCouter = 0;
		Integer childCounter = 0;
		// calculation age with birthDate and verify if is a child or adults
		for (String bithDate : listMedicalRecordBirthDate) {
			Date bithDateParse = null;
			try {
				bithDateParse = new SimpleDateFormat("dd/MM/yyyy").parse(bithDate);
				Date dateNow = new Date();
				long age = dateNow.getYear() - bithDateParse.getYear();
				if (age <= 18) {
					childCounter++;
				}
			} catch (ParseException e) {
				log.error("Error during parsing", e);
				e.printStackTrace();
			}
		}
		Integer NumberPersons = listPersonCoveredByStation.size();
		adultCouter = NumberPersons - childCounter;
		//displaying list result
		List<Object> finalListPersonsCoveredByStation = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			finalListPersonsCoveredByStation
					.add(new Person(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));
		}
		finalListPersonsCoveredByStation.add("Adults = " + adultCouter);
		finalListPersonsCoveredByStation.add("Childs = " + childCounter);
		log.info("Service - List of persons covered by station number: " + stationNumber);
		System.out.println(finalListPersonsCoveredByStation);
		return finalListPersonsCoveredByStation;
	}

	/**
	 * Method that add a fireStation
	 * 
	 * @param fireStation - An instance of {@link FireStation}
	 * @return the fireStation added
	 * @throws FireStationAlreadyExistException when the fireStation that we want
	 *                                          added already exist
	 */
	@Override
	public FireStation addFireStation(FireStation fireStation) {
		List<FireStation> listFireStations = getListFireStations();
		int indexPosition = listFireStations.indexOf(fireStation);
		//fireStation already exist
		if (indexPosition >= 0) {
			log.error("Service - FireStation cannot be saved :address: " + fireStation.getAddress() + ", Station: "
					+ fireStation.getStation() + " already exist");
			throw new FireStationAlreadyExistException("The FireStation that we try to save already Exist");
		}
		log.debug("Service - FireStation saved :address: " + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStationDAO.save(fireStation, indexPosition);
	}

	/**
	 * Method that delete a fireStation
	 * 
	 * @param address - A String that containing the address of fireStation
	 * @return a String to confirm or deny the deletion
	 */
	@Override
	public String deleteFireStation(String address) {
		FireStation fireStationToDelete = fireStationDAO.get(address);
		if (fireStationToDelete == null) {
			log.info("Service - FireStation cannot be deleted");
			return "FireStation cannot be deleted";
		}
		log.debug("Service - FireStation : address:" + fireStationToDelete.getAddress() + " ,Station: "
				+ fireStationToDelete.getStation() + " was deleted");
		return fireStationDAO.delete(fireStationToDelete);
	}

	/**
	 * Method that update a fireStation
	 * 
	 * @param fireStation - an instance of {@link FireStation}
	 * @return the fireStation updated
	 * @throws FireStationNotFoundException when the fireStation that we want update
	 *                                      not exist
	 */
	@Override
	public FireStation updateFireStation(FireStation fireStation) {
		List<FireStation> listFireStations = getListFireStations();
		FireStation fireStationInArray = fireStationDAO.get(fireStation.getAddress());
		if (fireStationInArray == null) {
			log.error("Service - FireStation not found: address: " + fireStation.getAddress() + ", Station: "
					+ fireStation.getStation() + " cannot be updated");
			throw new FireStationNotFoundException("FireStation not found");
		}
		int indexPosition = listFireStations.indexOf(fireStationInArray);
		fireStationInArray.setStation(fireStation.getStation());
		FireStation fireStationUpdated = fireStationDAO.save(fireStationInArray, indexPosition);
		log.debug("Service - The station number was updated: address: " + fireStation.getAddress() + ", Station: "
				+ fireStation.getStation());
		return fireStationUpdated;
	}
	
	private boolean isAdult(String birthDate) {
		int age = 0;
		// Faire le calcul de l'age 
		if(age > 18) {
			return true;
		}
		return false;
	}
}
