package com.dbs.weatherapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("WeatherReport")
public class WeatherReport {

	@Id
	private WeatherReportId id;
	
	private String timezone;
	private String summary;
	private String icon;
	private Double humidity;
	private Double temperatureMin;
	private Double temperatureMax;
	
	public WeatherReport(WeatherReportId id, String timezone, String summary, String icon, Double humidity, Double temperatureMin,
			Double temperatureMax) {
		super();
		this.id = id;
		this.timezone = timezone;
		this.summary = summary;
		this.icon = icon;
		this.humidity = humidity;
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
	}

	public WeatherReportId getId() {
		return id;
	}

	public void setId(WeatherReportId id) {
		this.id = id;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(Double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public Double getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(Double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	
	
}
