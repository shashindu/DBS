package com.dbs.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dbs.weatherapp.service.WeatherReportService;
/**
 * 
 * @author ShashinduSamarasingh
 *
 */
@Controller
public class WeatherReportController {

	@Autowired
	WeatherReportService weatherReportService;
	
	@GetMapping("/")
	public ModelAndView getWeatherReport() {
		return new ModelAndView("index", "forecastList", weatherReportService.getWeatherReportForToday());
	}
}
