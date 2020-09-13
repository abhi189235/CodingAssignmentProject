package com.assignment.utils;

import java.io.File;
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
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CommonUtilities {

	public WebDriver driver;
	public WebDriverWait wait;
	private ArrayList<String> locatorValues = null;

	DataIO dio = new DataIO();

	// constructor call
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

	// To Write text into a box
	public void writeTextInto(WebElement ele, String data) throws Exception {
		ele.sendKeys(data);
	}

	// To put hard wait at some point
	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Launch application using URL
	public void launchApplication(String url) {
		driver.get(url);
	}

	// Maximize the browser instance
	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	// Wait till the page loads
	public void pageLoadingWait() {
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}

	// To get the Page Title
	public String getPageTitle() {
		return driver.getTitle();
	}

	// To get Current URL of Page
	public String getPageUrl() {
		return driver.getCurrentUrl();
	}

	// To get complete Page Source
	public String getCompletePageSource() {
		return driver.getPageSource();
	}

	// Get the main(Parent) window handle
	public String getParentWindowHandle() {
		return driver.getWindowHandle();
	}

	// Switch to Child Window
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

	// Switch to specific Frame using name or Id
	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
	}

	// Come out of frame
	public void comeOutOfFrame() {
		driver.switchTo().defaultContent();
	}

	// Switch to Frame using element location
	public void switchToFrame(WebElement element) {
		driver.switchTo().frame(element);
	}

	// Perform hover operation on some Element
	public void hoverOverElement(WebElement target) {
		Actions action = new Actions(driver);
		action.moveToElement(target).build().perform();
	}

	// Accept the Alert Popup window
	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	// Dismiss the Alert Popup Window
	public void dismissAlert() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	// Hover Click over some element
	public void hoverClick(WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().build().perform();
	}

	// Get the inner text
	public String getVisibleText(WebElement ele) {
		String text = null;
		text = ele.getText();
		return text;
	}

	// Get the Specified Attribute Value
	public String getAttributeValue(WebElement ele, String attributeName) {
		String attributeVal = null;
		attributeVal = ele.getAttribute(attributeName);
		return attributeVal;
	}

	// Select value from dropDown using index
	public void selectOptionByIndex(WebElement ele, int indexVal) {
		Select dropDown = new Select(ele);
		dropDown.selectByIndex(indexVal);
	}

	// Select value from dropDown using value
	public void selectOptionByValue(WebElement ele, String value) {
		Select dropDown = new Select(ele);
		dropDown.selectByValue(value);
	}

	// Select value from dropDown using visible text
	public void selectOptionByVisibleText(WebElement ele, String text) {
		Select dropDown = new Select(ele);
		dropDown.selectByVisibleText(text);
	}

	// Navigate to the URL
	public void navigateToUrl(String url) {
		driver.navigate().to(url);
	}

	// Refresh the Current Page
	public void refreshCurrentPage() {
		driver.navigate().refresh();
	}

	// Navigate back
	public void goToBackPage() {
		driver.navigate().back();
	}

	// Navigate Forward
	public void goForwardPage() {
		driver.navigate().forward();
	}

	// Capture Full Page Screenshot
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

	// Capture specific element screenshot
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

	// check whether checkbox is selected or not
	public boolean isCheckboxSelected(WebElement ele) {
		return ele.isSelected();
	}

	// check whether element is displayed or not
	public boolean isElementDisplayed(WebElement ele) {
		return ele.isDisplayed();
	}

	// check whether element is enabled or not
	public boolean isElementEnabled(WebElement ele) {
		return ele.isEnabled();
	}

	// Move the screen down using down key, element is random element on page
	public void moveScreenToDownUsingDownKey(WebElement ele) throws Exception {
		Actions action = new Actions(driver);
		clickElement(ele);
		hardWait(2);
		for (int i = 0; i < 4; i++) {
			action.sendKeys(Keys.DOWN);
			hardWait(2);
		}
	}

	// Press the Enter Key
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
	public static Response response;

	// Parse the JSON using JsonPath and get value of some key
	public String getKeyValueJsonParsed(String response, String key) {
		JsonPath jpath = new JsonPath(response);
		return jpath.get(key).toString();
	}

	// Common Request Specification
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

	// Common Response Specification
	public ResponseSpecification responseSpecificationGenerator(int expectedCode) {

		ResponseSpecBuilder responseSpec = new ResponseSpecBuilder();
		responseSpec.expectContentType(ContentType.JSON);
		responseSpec.expectStatusCode(expectedCode);
		respSpec = responseSpec.build();
		return respSpec;
	}

	// given() with String body
	public RequestSpecification givenRestAssuredContent(RequestSpecification reqSpecification, String body) {
		return RestAssured.given().spec(reqSpecification).body(body);
	}

	// given() with Object body (POJO main Class Object)
	public RequestSpecification givenRestAssuredContent(RequestSpecification reqSpecification, Object body) {
		return RestAssured.given().spec(reqSpecification).body(body);
	}

	// given() with no body
	public RequestSpecification givenRestAssuredContent(RequestSpecification reqSpecification) {
		return RestAssured.given().spec(reqSpecification);
	}

	// Give the specified Request to Hit and return response
	public Response httpRequestSelection(RequestSpecification reqSpecification, String httpRequestMade,
			String resourcePath) {
		TemperatureAPIConstantResources tacr = TemperatureAPIConstantResources.valueOf(resourcePath);
		String finalResourcePath = tacr.getResource();

		if (httpRequestMade.equalsIgnoreCase("post"))
			response = reqSpecification.when().post(finalResourcePath);
		else if (httpRequestMade.equalsIgnoreCase("put"))
			response = reqSpecification.when().put(finalResourcePath);
		else if (httpRequestMade.equalsIgnoreCase("get"))
			response = reqSpecification.when().get(finalResourcePath);
		else if (httpRequestMade.equalsIgnoreCase("delete"))
			response = reqSpecification.when().delete(finalResourcePath);

		return response;
	}

	// Record and return the response using then()
	public Response thenRestAssuredContent(Response resp, ResponseSpecification responseSpec) {
		return resp.then().spec(responseSpec).extract().response();
	}

	// Conversion of Kelvin Temperature to Celsius
	public double convertKelvinToCelsiusTemp(double tempInKelvin) {
		double convertedTempInCelsius = tempInKelvin - 273.15;
		convertedTempInCelsius = Math.round(convertedTempInCelsius * 100.0) / 100.0;
		return convertedTempInCelsius;
	}
}
