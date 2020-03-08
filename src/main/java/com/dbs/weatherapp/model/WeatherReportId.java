package com.dbs.weatherapp.model;

import java.time.LocalDate;

import lombok.Data;
/**
 * 
 * @author ShashinduSamarasingh
 *
 */
@Data
public class WeatherReportId {

	private String location;
	private LocalDate date;
	
	public WeatherReportId(String location, LocalDate date) {
		super();
		this.location = location;
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
