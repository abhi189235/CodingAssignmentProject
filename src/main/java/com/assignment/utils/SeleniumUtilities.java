package com.assignment.utils;

import java.io.File;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtilities extends DataIO {

	public WebDriver driver;
	public WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	public SeleniumUtilities(WebDriver driver) {
		this.driver = driver;
	}

	// This is used to get the By Object to track element location on webPage
	public By getObject(String locatorType, String elementLocation, String replacement) throws Exception {

		if (locatorType.equalsIgnoreCase("xpath"))
			return By.xpath(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("css"))
			return By.cssSelector(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("id"))
			return By.id(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("name"))
			return By.name(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("linktext"))
			return By.linkText(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("partiallinktext"))
			return By.partialLinkText(elementLocation.trim().replaceAll("textToReplace", replacement));
		else if (locatorType.equalsIgnoreCase("classname"))
			return By.className(elementLocation.trim().replaceAll("textToReplace", replacement));
		else
			throw new Exception("Wrong Object Type");
	}

	// To get the detail of the specific location
	public WebElement getElement(String locatorType, String elementLocation, String replacement) throws Exception {
		By obj = getObject(locatorType, elementLocation, replacement);
		WebElement ele = null;
		try {
			ele = wait.until(ExpectedConditions.visibilityOfElementLocated(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	// Perform click on some element
	public void clickElement(String locatorType, String elementLocation, String replacement) throws Exception {
		getElement(locatorType, elementLocation, replacement).click();
	}

	// Scroll Down to Specified element
	public void scrollDownToElement(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	public void writeTextInto(String locatorType, String elementLocation, String replacement, String data)
			throws Exception {
		getElement(locatorType, elementLocation, replacement).sendKeys(data);
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
}
