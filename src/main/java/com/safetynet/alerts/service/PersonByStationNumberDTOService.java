package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.DAO.FireStationDAO;
import com.safetynet.alerts.DAO.IFireStationDAO;
import com.safetynet.alerts.DAO.IMedicalRecordDAO;
import com.safetynet.alerts.DAO.IPersonDAO;
import com.safetynet.alerts.DAO.MedicalRecordDAO;
import com.safetynet.alerts.exceptions.FireStationNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonDTO;
import com.safetynet.alerts.model.PersonResultEndPointByStationNumberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * Class that manage the url firestation?staionNumber
 * 
 * @author Christine Duarte
 *
 */
@Service
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonByStationNumberDTOService implements IPersonDTOByStationService {
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
	 * Method that get the list of persons covered by station number and displaying
	 * a counter for adults and for childs
	 * 
	 * @param station - the number of station
	 * @return A list with persons covered by station ant the counter of child and
	 *         adult
	 */
	@Override
	public PersonResultEndPointByStationNumberDTO getAddressCoveredByFireStation(String station) {
		int adultCouter = 0;
		int childCounter = 0;
		// get addresses covered by fireStation
		List<String> listAddressCoveredByFireStation = fireStationDAO.getAddressesCoveredByStationNumber(station);
		if(listAddressCoveredByFireStation == null) {
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
				.getListMedicalRecordByListOfPerson(listPersonCoveredByStation);
		for (MedicalRecord medicalRecord : listMedicalRecordCoveredByStation) {
			if (isAdult(personDAO.getAge(medicalRecord.getBirthDate()))) {
				adultCouter++;
			} else {
				childCounter++;
			}
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
		
		return displayingListPersonsCoveredByStation;
		
	}
	/**
	 * Method private that determines if person is adult or is child
	 * @param age - A int with age of person
	 * @return true if is adult and false if is child
	 */
	private boolean isAdult(int age) {
		if (age > 18) {
			return true;
		}
		return false;
	}
}
