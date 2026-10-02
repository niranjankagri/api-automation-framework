package com.typicode.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads the test data file src/resources/data.properties.
 * The path is relative to the working directory, so tests must run from the project root.
 */
public class PropertiesReader {

	// The loaded key/value pairs (empty if the file could not be read)
	private Properties prop = new Properties();

	/**
	 * Constructor: Read property file from resources folder
	 */
	public  PropertiesReader() {
		try {
			InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/resources/data.properties");
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Get the loaded properties
     * @return Properties  the test data.
     */
	public Properties getProp() {
		return prop;
	}

	/**
     * Replace the loaded properties
     * @param  prop  the new test data.
     */
	public void setProp(Properties prop) {
		this.prop = prop;
	}

}
