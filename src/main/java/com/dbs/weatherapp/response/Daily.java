package com.dbs.weatherapp.response;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author ShashinduSamarasingh
 *
 */
@Data
public class Daily {

	String summary;
	String icon;
	
	List<DailyDetail> data;

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

	public List<DailyDetail> getData() {
		return data;
	}

	public void setData(List<DailyDetail> data) {
		this.data = data;
	}

}
