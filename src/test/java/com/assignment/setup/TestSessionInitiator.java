package com.assignment.setup;

import org.openqa.selenium.WebDriver;

import com.assignment.actions.commonActions.BaseActions;
import com.assignment.actions.temperatureValidate.MainLandingPageActions;
import com.assignment.actions.temperatureValidate.WeatherPageActions;
import com.assignment.utils.CommonUtilities;

public class TestSessionInitiator {
	
	public WebDriver driver = null;
	WebDriverSetup wds = new WebDriverSetup();
	
	public TestSessionInitiator() {
		if(driver==null) {
			launchBrowserSession();
		}
	}
	
	public BaseActions baseActions = null;
	public MainLandingPageActions mainLandingPageActions = null;
	public WeatherPageActions weatherPageActions = null;
	
	private void browserConfigurations() {
		driver = wds.setBrowser();
		CommonUtilities selUtil = new CommonUtilities(driver);
		driver.manage().deleteAllCookies();
		selUtil.maximizeBrowser();		
	}
	
	
	
	private void initializePageObjects() {
		baseActions = new BaseActions(driver);
		mainLandingPageActions = new MainLandingPageActions(driver);
		weatherPageActions = new WeatherPageActions(driver);
		
	}
	
	private void launchBrowserSession() {
		browserConfigurations();
		initializePageObjects();
	}
}
