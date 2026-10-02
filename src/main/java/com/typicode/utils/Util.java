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

public class Util {

	public static String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	final static Logger logger = LogManager.getLogger(Util.class);
	private static ExtentTest extendedLogger;
	private static Faker faker = new Faker();
	
	/**
     * Check String is in particular format
     * @param  regex  
     * @param  str
     * @return boolean 
     */
	public static boolean isStringPresentWithoutCase(String regex, String str) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
        return matcher.find();
	}
	
	/**
     * Logs a info message
     * @param  message  
     */
	public static void logInfoMessage(String message) {
		getLogger().log(Status.PASS, message);
		logger.info(message);
	}
	
	
	public static ExtentTest getLogger() {
		return extendedLogger;
	}
	
	public static void setExtendedLogger(ExtentTest extendedLogger) {
		Util.extendedLogger = extendedLogger;
	}
	
	/**
     * Generates a random word
     *
     * @return the string
     */
    public static String getRandomWord() {
    	return String.valueOf(faker.lorem().word());
    }
	
	/**
     * Clean files of directory
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
