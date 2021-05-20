package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Class that start the application
 * 
 * @author Christine Duarte
 *
 */
@SpringBootApplication
public class AlertsApplication {
	/**
	 * Method that run the application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AlertsApplication.class, args);
	}

	/**@Bean
	public DataJson getData() {
		return new DataJson();
	}*/
	
}
