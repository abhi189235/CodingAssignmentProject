package com.assignment.utils;

public enum TemperatureAPIConstantResources {

	/* ENUM Class to declare the constant Resources */

	getTemperatureResource("data/2.5/weather");

	private String resource;

	TemperatureAPIConstantResources(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

}
