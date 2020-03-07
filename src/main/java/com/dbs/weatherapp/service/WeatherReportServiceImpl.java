package com.dbs.weatherapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dbs.weatherapp.model.WeatherReport;
import com.dbs.weatherapp.repository.WeatherReportRepository;

public class WeatherReportServiceImpl implements WeatherReportService{

	@Autowired
	WeatherReportRepository weatherReportRepository;
	
	
	@Override
	public List<WeatherReport> getWeatherReportForToday() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(WeatherReport forecast) {
		// TODO Auto-generated method stub
		
	}

}
