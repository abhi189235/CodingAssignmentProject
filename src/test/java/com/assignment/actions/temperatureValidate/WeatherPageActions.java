package com.assignment.actions.temperatureValidate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.assignment.actions.commonActions.BaseActions;
import com.assignment.utils.DataIO;

public class WeatherPageActions extends BaseActions {

	DataIO dio = new DataIO();
	public WebDriver driver;
	public static String cityNameUI;
	String temperatureInCelsiusUI;

	// Constructor Call
	public WeatherPageActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Enter the City value in Pin Your City Check box
	public void enterCityInPinCityBox() throws Exception {
		WebElement pinCityTextBox = getElement("pinCityTextBox", "TemperatureAutomationModule", "");
		cityNameUI = dio.getValuePropertiesFile("testData", "cityName");
		writeTextInto(pinCityTextBox, cityNameUI);
		hardWait(2);
		pressEnterKey(pinCityTextBox);
	}

	/*
	 * Check the city check box if it is not checked, otherwise no action will be
	 * done
	 */
	public void checkCityCheckboxIfNeeded() throws Exception {
		WebElement locationSelectionCheckbox = getElement("locationSelectionCheckbox", "TemperatureAutomationModule",
				cityNameUI);
		if (!isCheckboxSelected(locationSelectionCheckbox)) {
			clickElement(locationSelectionCheckbox);
		}

	}

	public void clickOnWeatherDetailsOfCity() throws Exception {

		/*
		 * WebElement randomLocationForScrollUse =
		 * getElement("randomLocationForScrollUse", "TemperatureAutomationModule", "");
		 * moveScreenToDownUsingClickHold(randomLocationForScrollUse);
		 */

		WebElement locationOnMap = getElement("locationOnMap", "TemperatureAutomationModule", cityNameUI);
		clickElement(locationOnMap);

	}

	public void getScreenshotWeatherDetailsPopup() throws Exception {
		WebElement locationTempDetailsPopup = getElement("locationTempDetailsPopup", "TemperatureAutomationModule", "");
		getScreenshotSpecificWebElement(locationTempDetailsPopup, "Weather_Details_Screenshot");
	}

	
	public void getTemperatureValueInCelsiusOfCity() throws Exception {
		WebElement degreesTemperatureOnPopup = getElement("degreesTemperatureOnPopup", "TemperatureAutomationModule",
				"");
		String completeText = getVisibleText(degreesTemperatureOnPopup);
		String[] splitArrayTemp = completeText.split(":");
		temperatureInCelsiusUI = splitArrayTemp[1].trim();
	}
	
	

}
