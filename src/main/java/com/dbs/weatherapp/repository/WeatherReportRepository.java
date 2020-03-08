package com.dbs.weatherapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbs.weatherapp.model.WeatherReport;
import com.dbs.weatherapp.model.WeatherReportId;
/**
 * 
 * @author ShashinduSamarasingh
 *
 */
public interface WeatherReportRepository extends MongoRepository<WeatherReport, WeatherReportId> {

	@Query(value = "{'id.date': ?0}")
    List<WeatherReport> findByWeatherReportDate(LocalDate date);
	
	
	@Query(value="{'id.date': {$lte:?0}}", delete = true)
	void housekeepData(LocalDate date);

}
