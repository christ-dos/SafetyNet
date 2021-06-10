package com.safetynet.alerts.configuration;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that configures the endpoint httpTrace for actuator
 * 
 * @author Christine Duarte
 *
 */
@Configuration
public class HttpTraceConfig {
	
	/**
	 * A method that permit access to the HttpTrace endpoint of actuator
	 * @return An In-memory implementation of {@link HttpTraceRepository}.
	 */
	@Bean
	public HttpTraceRepository htttpTraceRepository()
	{
	  return new InMemoryHttpTraceRepository();
	}
}
