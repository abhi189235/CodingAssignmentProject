package com.assignment.actions.temperatureValidate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.assignment.actions.commonActions.BaseActions;
import com.assignment.utils.DataIO;

public class WeatherPageActions extends BaseActions{
	
	DataIO dio = new DataIO();
	public WebDriver driver;
	public WeatherPageActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public void enterCityInPinCityBox() throws Exception {
		WebElement pinCityTextBox = getElement("pinCityTextBox", "TemperatureAutomationModule", "");
		String cityName = dio.getValuePropertiesFile("testData", "cityName");
		writeTextInto(pinCityTextBox, cityName);
	}
	
	public void checkCityCheckboxIfNeeded() {
		
	}
	
}
