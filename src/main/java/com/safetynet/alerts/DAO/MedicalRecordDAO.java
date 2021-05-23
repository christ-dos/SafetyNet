package com.safetynet.alerts.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Christine Duarte
 *
 */
@Repository
@Slf4j
@Builder
public class MedicalRecordDAO implements IMedicalRecordDAO {
	
	/**
	 * attribute that contain the list of medicalRecords that provide from data.json
	 */
	@Autowired
	private List<MedicalRecord> listMedicalRecords;
	
	
	public MedicalRecordDAO(List<MedicalRecord> listMedicalRecords) {
		super();
		this.listMedicalRecords = listMedicalRecords;
	}
	

	@Override
	public List<MedicalRecord> getMedicalRecords() {
		return listMedicalRecords;
	}

	@Override
	public Person getPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person save(int index, Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
