package com.pms.bean;

public class WeatherForecastCondition {
	private String dayofWeek = null;
	private String tempMin = null;
	private String tempMax = null;
	private String iconURL = null;
	private String condition = null;

	public WeatherForecastCondition() {

	}

	public String getDayofWeek() {

		return dayofWeek;
	}

	public void setDayofWeek(String dayofWeek) {

		this.dayofWeek = dayofWeek;
	}

	public String getTempMinCelsius() {

		return tempMin;
	}

	public void setTempMinCelsius(String tempMin) {

		this.tempMin = tempMin;
	}

	public String getTempMaxCelsius() {

		return tempMax;
	}

	public void setTempMaxCelsius(String tempMax) {

		this.tempMax = tempMax;
	}

	public String getIconURL() {

		return iconURL;
	}

	public void setIconURL(String iconURL) {

		this.iconURL = iconURL;
	}

	public String getCondition() {

		return condition;
	}

	public void setCondition(String condition) {

		this.condition = condition;
	}
}
