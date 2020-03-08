package com.dbs.weatherapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {

	@Autowired
	private Environment environment;
	
	public String getDarkSkyToken() {
		return environment.getProperty(ApplicationConstants.TOKEN);
	}
}
