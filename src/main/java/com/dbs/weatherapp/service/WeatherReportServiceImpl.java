package com.dbs.weatherapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dbs.weatherapp.config.ApplicationConfiguration;
import com.dbs.weatherapp.config.ApplicationConstants;
import com.dbs.weatherapp.masterData.LocationEnum;
import com.dbs.weatherapp.model.WeatherReport;
import com.dbs.weatherapp.model.WeatherReportId;
import com.dbs.weatherapp.repository.WeatherReportRepository;
import com.dbs.weatherapp.response.WeatherResponse;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * 
 * @author ShashinduSamarasingh
 *
 */
@Service
@Transactional
public class WeatherReportServiceImpl implements WeatherReportService{

	@Autowired
	WeatherReportRepository weatherReportRepository;
	
	@Autowired
	ApplicationConfiguration conf;
	@Override
	public List<WeatherReport> getWeatherReportForToday() {

		List<Observable<WeatherReport>> responseList = new ArrayList<Observable<WeatherReport>>();
		Observable<List<WeatherReport>> fetchedData;

		// get Data from Database for the Day
		List<WeatherReport> forecastData = weatherReportRepository.findByWeatherReportDate(LocalDate.now());

		// check weather all the data present
		Arrays.asList(LocationEnum.values()).forEach(l -> {
			if (!checkDataIsPresent(forecastData, l.name())) {
				responseList.add(getWeatherReportApiData(l));
			}
		});

		// call back external API to fetch missing data
		if (!responseList.isEmpty()) {
			fetchedData = Observable.zip(responseList, objects -> {
				for (Object obj : objects) {
					//persist data to the DB
					save((WeatherReport) obj);
					forecastData.add((WeatherReport) obj);
				}
				return forecastData;
			}).onErrorReturn(throwable -> {
				return forecastData;
			});
		} else {
			fetchedData = Observable.just(forecastData);
		}

		return fetchedData.toBlocking().first();
	}

	/**
	 * Check if forecast data is present in the given collection
	 * @param list
	 * @param location
	 * @return
	 */
	private boolean checkDataIsPresent(List<WeatherReport> list, String location) {
		return list.stream().filter(o -> o.getId().getLocation().equals(location)).findFirst().isPresent();
	}	

	/**
	 * get observable WeatherReport Data and map
	 * @param l
	 * @return
	 */
	private Observable<WeatherReport> getWeatherReportApiData(LocationEnum l) {
		return Observable.fromCallable(() -> fetchWeatherData(l))
				.map(new Func1<WeatherResponse, WeatherReport> () {
					@Override
					public WeatherReport call(WeatherResponse t) {
						WeatherReport temp = new WeatherReport(
								new WeatherReportId(l.name(), LocalDate.now()),
								l.getLocationCode(),
								t.getDaily().getData().get(0).getSummary(),
								t.getDaily().getData().get(0).getIcon(),
								t.getDaily().getData().get(0).getHumidity(),
								t.getDaily().getData().get(0).getTemperatureMin(),
								t.getDaily().getData().get(0).getTemperatureMax()
								);
						return temp;
					}
				})
				.subscribeOn(Schedulers.computation());
	}
     
	/**
	 * External API call
	 * @param location
	 * @return [WeatherResponse]
	 */
	private WeatherResponse fetchWeatherData(LocationEnum location) {
		final String url = ApplicationConstants.DARK_SKY_ENPOINT + conf.getDarkSkyToken() + "/" + location.getLat()
				+ "," + location.getLon() + "/?exclude=currently,flags,minutely,hourly";

		RestTemplate template = new RestTemplate();
		WeatherResponse result = template.getForObject(url, WeatherResponse.class);

		return result;
	}

	/**
	 * Save WeatherReport
	 */
	@Override
	public void save(WeatherReport forecast) {
		weatherReportRepository.save(forecast);
	}

	/**
	 * Housekeeping records that are more than 3 days old
	 */
//	@Override
//	public void housekeepData() {
//		weatherReportRepository.housekeepData(LocalDate.now().minusDays(3));
//	}
}
