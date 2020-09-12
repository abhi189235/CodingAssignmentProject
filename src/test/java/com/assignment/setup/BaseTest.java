package com.assignment.setup;

import org.testng.annotations.BeforeSuite;

public class BaseTest {
	
	public static TestSessionInitiator tsi;
	
	@BeforeSuite
	public void tearUp() {
		tsi = new TestSessionInitiator();
	}
	
	
}
