package com.dbs.weatherapp.response;

import lombok.Data;

@Data
public class WeatherResponse {

	String timeZone;
	Daily daily;
	
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public Daily getDaily() {
		return daily;
	}
	public void setDaily(Daily daily) {
		this.daily = daily;
	}
		
}
