package com.assignment.setup;

import org.openqa.selenium.WebDriver;

import com.assignment.utils.SeleniumUtilities;

public class TestSessionInitiator {
	
	public WebDriver driver = null;
	WebDriverSetup wds = new WebDriverSetup();
	
	public TestSessionInitiator() {
		if(driver==null) {
			launchBrowserSession();
		}
	}
	
	private void browserConfigurations() {
		driver = wds.setBrowser();
		SeleniumUtilities selUtil = new SeleniumUtilities(driver);
		driver.manage().deleteAllCookies();
		selUtil.maximizeBrowser();		
	}
	
	private void initializeActionsAndPageObjects() {
		
	}
	
	private void launchBrowserSession() {
		browserConfigurations();
		initializeActionsAndPageObjects();
	}
}
