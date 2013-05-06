package com.pms.bean;

public class WeatherForecastInformation {
	String city;
	String postal_code;
	String latitude_e6;
	String longitude_e6;
	String forecast_date;
	String current_date_time;
	String unit_system;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postalCode) {
		postal_code = postalCode;
	}

	public String getLatitude_e6() {
		return latitude_e6;
	}

	public void setLatitude_e6(String latitudeE6) {
		latitude_e6 = latitudeE6;
	}

	public String getLongitude_e6() {
		return longitude_e6;
	}

	public void setLongitude_e6(String longitudeE6) {
		longitude_e6 = longitudeE6;
	}

	public String getForecast_date() {
		return forecast_date;
	}

	public void setForecast_date(String forecastDate) {
		forecast_date = forecastDate;
	}

	public String getCurrent_date_time() {
		return current_date_time;
	}

	public void setCurrent_date_time(String currentDateTime) {
		current_date_time = currentDateTime;
	}

	public String getUnit_system() {
		return unit_system;
	}

	public void setUnit_system(String unitSystem) {
		unit_system = unitSystem;
	}

}
