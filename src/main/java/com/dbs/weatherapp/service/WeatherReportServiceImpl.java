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
		Observable<List<WeatherReport>> apiData;
		
		List<WeatherReport> weatherReportData = weatherReportRepository.findByWeatherReportDate(LocalDate.now());
		
		Arrays.asList(LocationEnum.values()).forEach(loc ->{
			if(!isDataExists(weatherReportData, loc.name())) {
				responseList.add(getApiData(loc));
			}
			
		});
				
		if(!weatherReportData.isEmpty()) {
			apiData = Observable.zip(responseList, obj ->{
				for(Object o: obj) {
					save((WeatherReport)o);
					weatherReportData.add((WeatherReport)o);
				}
				return weatherReportData;
			}).onErrorReturn(throwable ->{
				return weatherReportData;
			});
		    
		}else {
			apiData = Observable.just(weatherReportData);
		}
		return apiData.toBlocking().first();
	}

	private boolean isDataExists(List<WeatherReport> weatherReportData, String location) {
		return weatherReportData.stream().filter(obj -> obj.getId().getLocation().equals(location)).findFirst().isPresent();
	}
	
	@Override
	public void save(WeatherReport report) {
		weatherReportRepository.save(report);
		
	}
	
	private WeatherResponse fetchWeatherData(LocationEnum location) {
		final String url = ApplicationConstants.DARK_SKY_ENPOINT + conf.getDarkSkyToken() + "/" + location.getLat()
				+ "," + location.getLon() + "/?exclude=currently,flags,minutely,hourly";

		RestTemplate template = new RestTemplate();
		WeatherResponse result = template.getForObject(url, WeatherResponse.class);

		return result;
	}
	
	private rx.Observable<WeatherReport> getApiData(LocationEnum loc){
		return rx.Observable.fromCallable(() ->fetchWeatherData(loc))
			   .map(new Func1<WeatherResponse, WeatherReport>(){
				   
				  @Override
				  public WeatherReport call (WeatherResponse r) {
					  WeatherReport report = new WeatherReport(
							  new WeatherReportId(loc.name(), LocalDate.now()),
							  loc.getLocationCode(),
								r.getDaily().getData().get(0).getSummary(),
								r.getDaily().getData().get(0).getIcon(),
								r.getDaily().getData().get(0).getHumidity(),
								r.getDaily().getData().get(0).getTemperatureMin(),
								r.getDaily().getData().get(0).getTemperatureMax()
							  
							  );
					  return report;
					  
				  }
				   
			   }).subscribeOn(Schedulers.computation());
		
	}

}
