package com.assignment.test.endToEndScenarios;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.assignment.setup.BaseTest;

public class EndToEndTestCase extends BaseTest{
	
	@BeforeTest
	public void launchBrowserWithUrl() {
		tsi.baseActions.navigateToGivenUrl("Config", "url");
	}
	
	@Test
	public void testPhase1() throws Exception {
		tsi.mainLandingPageActions.navigateToWeatherPage();
		tsi.weatherPageActions.enterCityInPinCityBox();
		tsi.weatherPageActions.checkCityCheckboxIfNeeded();
		tsi.weatherPageActions.clickOnWeatherDetailsOfCity();
		tsi.weatherPageActions.getScreenshotWeatherDetailsPopup();
		tsi.weatherPageActions.getTemperatureValueInCelsiusOfCity();
		
	}
}
