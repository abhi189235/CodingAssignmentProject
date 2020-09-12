package com.assignment.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.assignment.utils.DataIO;

public class WebDriverSetup {

	public WebDriver driver;

	public WebDriver setBrowser() {
		DataIO dIO = new DataIO();
		String browserName = dIO.getValuePropertiesFile("Config", "Browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			driver = new InternetExplorerDriver();
		}

		return driver;
	}

}
