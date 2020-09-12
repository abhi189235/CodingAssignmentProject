package com.assignment.actions.commonActions;

import org.openqa.selenium.WebDriver;

import com.assignment.utils.CommonUtilities;
import com.assignment.utils.DataIO;

public class BaseActions extends CommonUtilities {

	public WebDriver driver;

	// Constructor Call
	public BaseActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// This is used to navigate to the Given Url in Properties File
	public void navigateToGivenUrl(String fileName, String property) {
		DataIO dIO = new DataIO();
		String url = dIO.getValuePropertiesFile(fileName, property);
		navigateToUrl(url);
	}
	
	
}
