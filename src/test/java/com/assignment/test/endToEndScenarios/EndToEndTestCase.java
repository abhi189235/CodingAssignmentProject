package com.assignment.test.endToEndScenarios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.assignment.setup.BaseTest;

public class EndToEndTestCase extends BaseTest {

	public double temperatureValUI;
	public double temperatureValAPI;

	// Launch browser with NDTV Website
	@BeforeTest
	public void launchBrowserWithUrl() {
		tsi.baseActions.navigateToGivenUrl("Config", "url");
	}

	// Extract the Temperature in Celsius from NDTV Website
	@Test(priority = 1)
	public void temperatureFromUIScenario() throws Exception {

		tsi.mainLandingPageActions.navigateToWeatherPage();
		tsi.weatherPageActions.enterCityInPinCityBox();
		tsi.weatherPageActions.checkCityCheckboxIfNeeded();
		tsi.weatherPageActions.clickOnWeatherDetailsOfCity();
		tsi.weatherPageActions.getScreenshotWeatherDetailsPopup();
		temperatureValUI = tsi.weatherPageActions.getTemperatureValueInCelsiusOfCity();
		System.out.println("UI TEMPERATURE = " + temperatureValUI);
	}

	// Extract Temperature in Celsius from Open Weather API
	@Test(priority = 2)
	public void temperatureFromAPIScenarios() throws Exception {
		tsi.weatherAPIHandlingActions.setUpMetadataForWeatherAPIGet();
		tsi.weatherAPIHandlingActions.hitGetRequestForTempDetails();
		tsi.weatherAPIHandlingActions.recordResponseGeneratedAfterGetHit();
		tsi.weatherAPIHandlingActions.getTempValKelvinFromResponseOutput();
		temperatureValAPI = tsi.weatherAPIHandlingActions.convertKelvinTempToCelsius();
		System.out.println("API TEMPERATURE = " + temperatureValAPI);
	}

	// Comparator Logic of both UI and API valuess
	@Test(priority = 3)
	public void temperatureComparisionUiAndApi() {

		tsi.weatherPageActions.temperatureComparatorLogic(temperatureValUI, temperatureValAPI);

	}

	@AfterClass
	public void closeBrowser() {
		tsi.baseActions.closeCurrentInstanceBrowser();
	}
}
