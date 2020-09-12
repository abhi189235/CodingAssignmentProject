package com.assignment.setup;

import org.openqa.selenium.WebDriver;

import com.assignment.actions.commonActions.BaseActions;
import com.assignment.actions.temperatureValidate.MainLandingPageActions;
import com.assignment.actions.temperatureValidate.WeatherPageActions;
import com.assignment.utils.CommonUtilities;

public class TestSessionInitiator {

	public WebDriver driver = null;
	WebDriverSetup wds = new WebDriverSetup();

	// Constructor Call, will be called on object instantiation
	public TestSessionInitiator() {
		if (driver == null) {
			launchBrowserSession();
		}
	}

	public BaseActions baseActions = null;
	public MainLandingPageActions mainLandingPageActions = null;
	public WeatherPageActions weatherPageActions = null;

	// Configure Very basic properties of browser
	private void browserConfigurations() {
		driver = wds.setBrowser();
		CommonUtilities selUtil = new CommonUtilities(driver);
		driver.manage().deleteAllCookies();
		selUtil.maximizeBrowser();
	}

	// Initialize all the action classes
	private void initializePageObjects() {
		baseActions = new BaseActions(driver);
		mainLandingPageActions = new MainLandingPageActions(driver);
		weatherPageActions = new WeatherPageActions(driver);

	}

	/*
	 * Launch the Browser Session (Involves browser configuration and action class
	 * initialization)
	 */
	private void launchBrowserSession() {
		browserConfigurations();
		initializePageObjects();
	}
}
