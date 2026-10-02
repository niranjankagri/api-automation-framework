package com.typicode.utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import net.datafaker.Faker;

/**
 * Shared helpers: logging (console, applog.txt and the Extent report), the email format check,
 * random test data and file cleanup.
 */
public class Util {

	// Valid email: name@domain.tld with a 2-6 letter top-level domain (matched case-insensitively)
	public static String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	// Log4j2 logger (configured in log4j2.properties)
	final static Logger logger = LogManager.getLogger(Util.class);
	// Extent test node of the running test; set by BaseTest before each test method
	private static ExtentTest extendedLogger;
	// Random data generator
	private static Faker faker = new Faker();

	/**
     * Check String is in particular format (ignoring upper/lower case)
     * @param  regex    the regular expression to look for
     * @param  str      the text to check
     * @return boolean  true if the text contains a match.
     */
	public static boolean isStringPresentWithoutCase(String regex, String str) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
        return matcher.find();
	}
	
	/**
     * Logs a info message to the Extent report (as a passed step), the console and applog.txt
     * @param  message  the message to log
     */
	public static void logInfoMessage(String message) {
		getLogger().log(Status.PASS, message);
		logger.info(message);
	}


	/**
     * Get the Extent test node of the running test
     * @return ExtentTest  the test node.
     */
	public static ExtentTest getLogger() {
		return extendedLogger;
	}

	/**
     * Set the Extent test node that log messages are written to
     * @param  extendedLogger  the test node of the running test.
     */
	public static void setExtendedLogger(ExtentTest extendedLogger) {
		Util.extendedLogger = extendedLogger;
	}
	
	/**
     * Generates a random word
     *
     * @return String  a random lorem ipsum word.
     */
    public static String getRandomWord() {
    	return String.valueOf(faker.lorem().word());
    }
	
	/**
     * Clean files of directory (the directory itself is kept)
     * @param  directory  the path of the directory to empty
     */
    public static void cleanFilesOfDirectory(String directory) { 
        try {
        	FileUtils.cleanDirectory(new File(directory)); 
            logger.info("Files deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
