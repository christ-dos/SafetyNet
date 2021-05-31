package com.safetynet.alerts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonDTO;
import com.safetynet.alerts.model.PersonResultEndPointByStationNumberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonByStationNumberDTOService implements IPersonDTOByStationService {

	@Autowired
	private IPersonDAO personDAO;

	@Autowired
	private IMedicalRecordDAO medicalRecordDAO;

	@Autowired
	private IFireStationDAO fireStationDAO;

	/**
	 * Method that get the list of persons covered by station number and displaying
	 * a counter for adults and for childs
	 * 
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the counter of child and
	 *         adult
	 */
	@Override
	public PersonResultEndPointByStationNumberDTO getAddressCoveredByFireStation(String station) {
		Integer adultCouter = 0;
		Integer childCounter = 0;
		// get addresses covered by fireStation
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if (listAddressCoveredByFireStation.size() == 0) {
			log.error("Service - FireStation not found with station number : " + station);
			throw new FireStationNotFoundException("The FireStation number not found");
		}
		// collect persons by the list of addresses covered by station
		List<Person> listPersonCoveredByStation = personDAO.getPersonsByListAdresses(listAddressCoveredByFireStation);

		// Parcourir la liste des personnes et faire recuperer le medicalrecord pour
		// chaque personne
		// Appeler la methode isAdult en passant la date de naissance pris du medical
		// record
		// Incrementer les compteurs enfants et aldutes.

		List<MedicalRecord> listMedicalRecordCoveredByStation = medicalRecordDAO
				.getListMedicalRecordForAListOfPerson(listPersonCoveredByStation);
		System.out.println("listMedicalRecordCoveredByStation:"  +  listMedicalRecordCoveredByStation);
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByStation) {
			if (isAdult(medicalRecord.getBirthDate())) {
				
				adultCouter++;
			} else {
				childCounter++;
			}
			System.out.println(medicalRecord.getBirthDate());
		}
		List<PersonDTO> listPersonDTO = new ArrayList<>();
		for (Person person : listPersonCoveredByStation) {
			PersonDTO personDTO = new PersonDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
					person.getPhone());
			listPersonDTO.add(personDTO);
		}
		PersonResultEndPointByStationNumberDTO displayingListPersonsCoveredByStation = new PersonResultEndPointByStationNumberDTO(
				listPersonDTO, adultCouter, childCounter);
		log.info("Service - List of persons covered by station number: " + station);
		System.out.println("displayingListPersonsCoveredByStation: " + displayingListPersonsCoveredByStation);
		return displayingListPersonsCoveredByStation;
	}

	private boolean isAdult(String birthDate) {
		long age = 0;
		Date bithDateParse = null;
		try {
			bithDateParse = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
			Date dateNow = new Date();
			age = dateNow.getYear() - bithDateParse.getYear();
		} catch (ParseException e) {
			log.error("Error during parsing", e);
			e.printStackTrace();
		}
		if (age > 18) {
			return true;
		}
		return false;
	}
}
