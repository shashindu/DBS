package com.dbs.weatherapp.response;

import lombok.Data;

@Data
public class DailyDetail {

	String summary;
	String icon;
	Double humidity;
	Double temperatureMin;
	Double temperatureMax;
	
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
