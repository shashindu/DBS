package com.dbs.weatherapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {

	private Environment environment;
	
	public String getDarkSkyToken() {
		return environment.getProperty(ApplicationConstants.TOKEN);
	}
}
