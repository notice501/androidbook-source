package com.pms.bean;

public class WeatherCurrentCondition {
	private String dayofWeek = null;
	private String tempCelcius = null;
	private String tempFahrenheit = null;
	private String iconURL = null;
	private String condition = null;
	private String windCondition = null;
	private String humidity = null;
	public WeatherCurrentCondition() {
	}
	public String getDayofWeek() {

		return this.dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) {

		this.dayofWeek = dayofWeek;
	}

	public String getTempCelcius() {

		return this.tempCelcius;
	}

	public void setTempCelcius(String temp) {

		this.tempCelcius = temp;
	}

	public String getTempFahrenheit() {

		return this.tempFahrenheit;
	}

	public void setTempFahrenheit(String temp) {

		this.tempFahrenheit = temp;
	}

	public String getIconURL() {

		return this.iconURL;
	}

	public void setIconURL(String iconURL) {

		this.iconURL = iconURL;
	}

	public String getCondition() {

		return this.condition;
	}

	public void setCondition(String condition) {

		this.condition = condition;
	}

	public String getWindCondition() {

		return this.windCondition;
	}

	public void setWindCondition(String windCondition) {

		this.windCondition = windCondition;
	}

	public String getHumidity() {

		return this.humidity;
	}

	public void setHumidity(String humidity) {

		this.humidity = humidity;
	}
}
