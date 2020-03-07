package com.dbs.weatherapp.service;

import java.util.List;

import com.dbs.weatherapp.model.WeatherReport;

public interface WeatherReportService {

	public List<WeatherReport> getWeatherReportForToday();
	public void save(WeatherReport forecast);
}
