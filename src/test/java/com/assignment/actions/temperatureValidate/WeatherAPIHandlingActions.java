package com.assignment.actions.temperatureValidate;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import com.assignment.actions.commonActions.BaseActions;
import com.assignment.utils.DataIO;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class WeatherAPIHandlingActions extends BaseActions {

	public WebDriver driver;

	// Constructor Call
	public WeatherAPIHandlingActions(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	DataIO dio = new DataIO();
	public static RequestSpecification reqSpec;
	public static ResponseSpecification respSpec;
	public static Response resp;
	public String temperatureKelvinFromAPICall;
	public double temperatureConversionToCelsiusAPI;

	// Set up the metadata using given() method
	public void setUpMetadataForWeatherAPIGet() throws Exception {
		// Extract common values from the configuration file
		String baseURL = dio.getValuePropertiesFile("Config", "baseUrlApi");
		String apiKey = dio.getValuePropertiesFile("Config", "apiKey");
		String cityName = dio.getValuePropertiesFile("testData", "cityName");

		// Initialize all the hash maps required
		HashMap<String, String> headersMap = new HashMap<>();
		HashMap<String, String> queryParameterMap = new HashMap<>();
		HashMap<String, String> pathParametersMap = new HashMap<>();

		// Add the required query parameters to query hash map
		queryParameterMap.put("q", cityName);
		queryParameterMap.put("appid", apiKey);

		// Pass all the metadata in the methods to generate Request Specification
		reqSpec = requestSpecificationGenrator(baseURL, headersMap, queryParameterMap, pathParametersMap);
		reqSpec = givenRestAssuredContent(reqSpec);

	}

	// Hitting the respective API resource to get temperature Details
	public void hitGetRequestForTempDetails() {

		resp = httpRequestSelection(reqSpec, "get", "getTemperatureResource");
	}

	// Recording the generated response after hitting API
	public void recordResponseGeneratedAfterGetHit() {

		respSpec = responseSpecificationGenerator(200);
		resp = thenRestAssuredContent(resp, respSpec);
	}

	// Get the value of temperature by parsing the Response JSON
	public void getTempValKelvinFromResponseOutput() {
		String response = resp.asString();
		temperatureKelvinFromAPICall = getKeyValueJsonParsed(response, "main.temp");
	}

	// Convert the output temperature from Kelvin to Celsius
	public double convertKelvinTempToCelsius() {
		double temperatureKelvinInFloat = Float.parseFloat(temperatureKelvinFromAPICall);
		double temperatureConversionToCelsiusAPI = convertKelvinToCelsiusTemp(temperatureKelvinInFloat);
		return temperatureConversionToCelsiusAPI;
	}

}
