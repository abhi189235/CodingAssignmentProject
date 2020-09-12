package com.assignment.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CommonUtilities {

	public WebDriver driver;
	public WebDriverWait wait;
	private ArrayList<String> locatorValues = null;

	DataIO dio = new DataIO();

	public CommonUtilities(WebDriver driver) {
		this.driver = driver;
	}

	// This is used to get the By Object to track element location on webPage
	public By getObject(String objectName, String fileName, String replacement) throws Exception {
		locatorValues = dio.getPageObjectFromExcel(fileName, objectName);

		if (locatorValues.get(1).trim().equalsIgnoreCase("xpath"))
			return By.xpath(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("css"))
			return By.cssSelector(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("id"))
			return By.id(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("name"))
			return By.name(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("linktext"))
			return By.linkText(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("partiallinktext"))
			return By.partialLinkText(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else if (locatorValues.get(1).trim().equalsIgnoreCase("classname"))
			return By.className(locatorValues.get(2).trim().replaceAll("textToReplace", replacement));
		else
			throw new Exception("Wrong Object Type");
	}

	// To get the detail of the specific location
	public WebElement getElement(String elementName, String fileName, String replacement) throws Exception {
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		By obj = getObject(elementName, fileName, replacement);
		WebElement ele = null;
		try {
			ele = wait.until(ExpectedConditions.visibilityOfElementLocated(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	// Perform click on some element
	public void clickElement(WebElement ele) throws Exception {
		ele.click();
	}

	// Scroll Down to Specified element
	public void scrollDownToElement(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	public void writeTextInto(WebElement ele, String data) throws Exception {
		ele.sendKeys(data);
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void launchApplication(String url) {
		driver.get(url);
	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public void pageLoadingWait() {
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	public String getCompletePageSource() {
		return driver.getPageSource();
	}

	public String getParentWindowHandle() {
		return driver.getWindowHandle();
	}

	public void switchWindow(String parentWindowHandle) {
		try {
			Set<String> allHandles = driver.getWindowHandles();
			Iterator<String> iter = allHandles.iterator();
			while (iter.hasNext()) {
				String childWindow = iter.next();
				if (!childWindow.contains(parentWindowHandle)) {
					driver.switchTo().window(childWindow);
					hardWait(3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchToFrameNameOrId(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	public void comeOutOfFrame() {
		driver.switchTo().defaultContent();
	}

	public void switchToFrameWebElement(WebElement element) {
		driver.switchTo().frame(element);
	}

	public void hoverOverElement(WebElement target) {
		Actions action = new Actions(driver);
		action.moveToElement(target).build().perform();
	}

	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public void dismissAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public void hoverClick(WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().build().perform();
	}

	public String getVisibleText(WebElement ele) {
		String text = null;
		text = ele.getText();
		return text;
	}

	public String getAttributeValue(WebElement ele, String attributeName) {
		String attributeVal = null;
		attributeVal = ele.getAttribute(attributeName);
		return attributeVal;
	}

	public void selectOptionByIndex(WebElement ele, int indexVal) {
		Select dropDown = new Select(ele);
		dropDown.selectByIndex(indexVal);
	}

	public void selectOptionByValue(WebElement ele, String value) {
		Select dropDown = new Select(ele);
		dropDown.selectByValue(value);
	}

	public void selectOptionByVisibleText(WebElement ele, String text) {
		Select dropDown = new Select(ele);
		dropDown.selectByVisibleText(text);
	}

	public void navigateToUrl(String url) {
		driver.navigate().to(url);
	}

	public void refreshCurrentPage() {
		driver.navigate().refresh();
	}

	public void goToBackPage() {
		driver.navigate().back();
	}

	public void goForwardPage() {
		driver.navigate().forward();
	}

	public void captureFullPageScreenshot(String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File sourceFile = ts.getScreenshotAs(OutputType.FILE);
			String screenshotLocation = System.getProperty("user.dir") + "\\Screenshots\\" + screenshotName + ".png";
			FileUtils.copyFile(sourceFile, new File(screenshotLocation));
			System.out.println("Screenshot Taken");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getScreenshotSpecificWebElement(WebElement ele, String screenshotName) {
		try {
			File sourceFile = ele.getScreenshotAs(OutputType.FILE);
			String screenshotLocation = System.getProperty("user.dir") + "\\Screenshots\\" + screenshotName + ".png";
			FileUtils.copyFile(sourceFile, new File(screenshotLocation));
			System.out.println("Screenshot Taken");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isCheckboxSelected(WebElement ele) {
		return ele.isSelected();
	}

	public boolean isElementDisplayed(WebElement ele) {
		return ele.isDisplayed();
	}

	public boolean isElementEnabled(WebElement ele) {
		return ele.isEnabled();
	}

	public void moveScreenToDownUsingClickHold(WebElement ele) {
		Actions action = new Actions(driver);
		action.dragAndDropBy(ele, 200, 300).build().perform();

	}

	public void pressEnterKey(WebElement ele) {
		ele.sendKeys(Keys.ENTER);
	}

	/*
	 * ================================================================
	 * ================UTILITIES FOR API TESTING=======================
	 * ================================================================
	 */

	public static RequestSpecification reqSpec;
	public static ResponseSpecification respSpec;

	public String getKeyValueJsonParsed(String response, String key) {
		JsonPath jpath = new JsonPath(response);
		return jpath.get(key).toString();
	}

	public RequestSpecification requestSpecificationGenrator(String baseURL, HashMap<String, String> headersMap,
			HashMap<String, String> queryParametersMap, HashMap<String, String> pathParametersMap) throws Exception {

		RequestSpecBuilder requestSpec = new RequestSpecBuilder();
		PrintStream streamObj = new PrintStream(new FileOutputStream("LoggingAPIFile.text"));
		requestSpec.setContentType(ContentType.JSON);
		requestSpec.addFilter(RequestLoggingFilter.logRequestTo(streamObj));
		requestSpec.addFilter(ResponseLoggingFilter.logResponseTo(streamObj));
		requestSpec.setBaseUri(baseURL);
		requestSpec.addQueryParams(queryParametersMap);
		requestSpec.addHeaders(headersMap);
		requestSpec.addPathParams(pathParametersMap);
		reqSpec = requestSpec.build();
		return reqSpec;
	}

	public ResponseSpecification responseSpecificationGenerator(int expectedCode) {
		
		ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();
		responseSpec.expectContentType(ContentType.JSON);
		responseSpec.expectStatusCode(expectedCode);
		respSpec = responseSpec.build();
		return respSpec;
	}

}
