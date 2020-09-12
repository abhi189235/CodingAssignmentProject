package com.assignment.actions.temperatureValidate;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.assignment.actions.commonActions.BaseActions;

public class MainLandingPageActions extends BaseActions {

	public WebDriver driver;

	// Constructor Call
	public MainLandingPageActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Navigation to the Weather Page from the Main page
	public void navigateToWeatherPage() throws Exception {
		WebElement subMenu = getElement("subMenu", "TemperatureAutomationModule", "");
		clickElement(subMenu);

		WebElement weatherTab = getElement("weatherTab", "TemperatureAutomationModule", "");
		clickElement(weatherTab);
	}

}
