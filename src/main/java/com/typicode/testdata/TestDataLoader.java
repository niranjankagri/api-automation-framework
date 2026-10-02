package com.typicode.testdata;

import com.typicode.utils.PropertiesReader;

/**
 * Holds the test data read from data.properties as typed fields.
 */
public class TestDataLoader extends PropertiesReader {

	// Username of the user the tests search for
	private String username;

	/**
	 * Constructor: Read the properties file and fill the test data fields
	 */
	public TestDataLoader() {
		applyProperties();
	}

	/**
     * Get the username from the test data
     * @return String  the username.
     */
	public String getUsername() {
		return username;
	}

	/**
     * Set the username used by the tests
     * @param  username  the username.
     */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Copy the values of the properties file into the test data fields
     */
	public void applyProperties() {
		setUsername(getProp().getProperty("username"));
	}

}
