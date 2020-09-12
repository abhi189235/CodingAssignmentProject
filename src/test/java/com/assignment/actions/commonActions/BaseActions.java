package com.assignment.actions.commonActions;

import org.openqa.selenium.WebDriver;

import com.assignment.utils.CommonUtilities;
import com.assignment.utils.DataIO;

public class BaseActions extends CommonUtilities{
	
	public WebDriver driver;
	
	public BaseActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public void navigateToGivenUrl(String fileName, String property) {
		DataIO dIO = new DataIO();
		String url = dIO.getValuePropertiesFile(fileName, property);
		navigateToUrl(url);
	}
	
	
}
