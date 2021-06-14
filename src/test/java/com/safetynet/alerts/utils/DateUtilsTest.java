package com.safetynet.alerts.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Class that test {@link DateUtils}
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class DateUtilsTest {

	/**
	 * A mock of {@link DateUtils}
	 */
	private DateUtils dateUtils;

	/**
	 * Method which initialize dateUtils per all tests
	 * 
	 */
	@BeforeEach
	public void setUpPerTest() {
		dateUtils = new DateUtils();
	}

	/**
	 * Method that test getAge for a birthDate 09/17/1974 then return 46 years
	 */
	@Test
	public void testGetAge_whenbithDateIsAString_thenReturnAge() {
		// GIVEN
		DateUtils dateUtils = new DateUtils();
		String birthDate = "09/17/1974";
		// WHEN
		int resultAge = dateUtils.getAge(birthDate);
		// THEN
		assertEquals(46, resultAge);
	}

	/**
	 * Method that test getAge when the birthDate is empty then throw an
	 * IllegalArgumentException
	 */
	@Test
	public void testGetAge_whenbithDateIsEmpty_thenThrowAnIllegalArgumentException() {
		// GIVEN
		String birthDate = "";
		// WHEN
		// THEN
		assertThrows(IllegalArgumentException.class, () -> dateUtils.getAge(birthDate));
	}

	/**
	 * Method that test getAge when the birthDate is after date actual then return
	 * -1
	 */
	@Test
	public void testGetAge_whenBirthDateIsAfterDateActual_thenReturnMoin1() {
		// GIVEN
		String birthDate = "01/25/2050";
		// WHEN
		int resultAge = dateUtils.getAge(birthDate);
		// THEN
		assertEquals(-1, resultAge);
	}
}
