package com.assignment.setup;

import org.testng.annotations.BeforeSuite;

public class BaseTest {

	public static TestSessionInitiator tsi;

	/*
	 * Call the Test Session Initiator (Browser Configuration and All Action Object
	 * Initialization)
	 */
	@BeforeSuite
	public void tearUp() {
		tsi = new TestSessionInitiator();
	}

}
