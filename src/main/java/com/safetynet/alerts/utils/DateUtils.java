package com.safetynet.alerts.utils;

import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that get age of persons
 * 
 * @author Christine Duarte
 *
 */
@Slf4j
public class DateUtils {

	/**
	 * Method that get age by birthDate
	 * 
	 * @param birthDate - A string containing the birthDate
	 * @return The value of age
	 */
	public static int getAge(String birthDate) {
		if (birthDate != null) {
			LocalDateTime bithDateParse = LocalDateTime.parse(birthDate, DateTimeFormat.forPattern("MM/dd/yyyy"));
			LocalDateTime currentDate = LocalDateTime.now();
			if (currentDate.isAfter(bithDateParse)) {
				Years age = Years.yearsBetween(bithDateParse, currentDate);
				log.debug("DateUtils - Age calculated for bithDate: " + birthDate);
				return age.getYears();
			}
		}
		log.error("DateUtils - The bithdate is not valid");
		return -1;
	}

	/**
	 * Method private that determines if person is adult or is child
	 * 
	 * @param age - A int with age of person
	 * @return true if is adult and false if is child
	 */
	public static boolean isAdult(int age) {
		if (age > 18) {
			return true;
		}
		return false;
	}

}
