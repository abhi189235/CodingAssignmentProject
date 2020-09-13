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

	// Click the city on the map to open popup weather details
	public void clickOnWeatherDetailsOfCity() throws Exception {

		WebElement randomLocationForScrollUse = getElement("randomLocationForScrollUse", "TemperatureAutomationModule",
				"");
		moveScreenToDownUsingDownKey(randomLocationForScrollUse);

		WebElement locationOnMap = getElement("locationOnMap", "TemperatureAutomationModule", cityNameUI);
		clickElement(locationOnMap);

	}

	// Capture the screenshot of specific popup weather details and save in
	// screenshots folder
	public void getScreenshotWeatherDetailsPopup() throws Exception {
		WebElement locationTempDetailsPopup = getElement("locationTempDetailsPopup", "TemperatureAutomationModule", "");
		getScreenshotSpecificWebElement(locationTempDetailsPopup, "Weather_Details_Screenshot");
	}

	// Perform the operations on the output string to extract temperature value
	public double getTemperatureValueInCelsiusOfCity() throws Exception {
		WebElement degreesTemperatureOnPopup = getElement("degreesTemperatureOnPopup", "TemperatureAutomationModule",
				"");
		String completeText = getVisibleText(degreesTemperatureOnPopup);
		String[] splitArrayTemp = completeText.split(":");
		temperatureInCelsiusUI = splitArrayTemp[1].trim();
		double tempeInCelsiusUI = Float.parseFloat(temperatureInCelsiusUI);
		return tempeInCelsiusUI;
	}

	// Logic of Temperature Variance
	public void temperatureComparatorLogic() {
		dio.loadExcelFile("TemperatureCalculationFolder", "TemperatureCalculation");
		String UIVal = "30";
		String APIVal = "35.89";
		dio.setCellData("temperature", "CelsiusTemp", 2, UIVal);
		dio.setCellData("temperature", "CelsiusTemp", 3, APIVal);
		dio.reEvaluateExcelFormulas();
		hardWait(3);
		dio.loadExcelFile("TemperatureCalculationFolder", "TemperatureCalculation");
		String sum = dio.getCellDataExcel("temperature", "(x-u)^2", 5);
		String varianceVal = dio.getCellDataExcel("temperature", "(x-u)^2", 6);
		String varianceLogicVal = dio.getCellDataExcel("temperature", "(x-u)^2", 7);
		System.out.println(sum);
		System.out.println(varianceVal);
		System.out.println(varianceLogicVal);

	}
}
