package com.typicode.testdata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.typicode.utils.Util;

/**
 * Base class for all test classes: gives access to the test data and creates the Extent report
 * (test-output/STMExtentReport.html) with one entry per test method.
 */
public abstract class BaseTest extends TestDataLoader {

	// The Extent report of the current <test> in testng.xml
	ExtentReports extent;
	// Extent entry of the running test method
	ExtentTest logger;

	/**
     * Get the Extent entry of the running test method
     * @return ExtentTest  the test entry.
     */
	public ExtentTest getLogger() {
		return logger;
	}

	/**
     * Set the Extent entry of the running test method
     * @param  logger  the test entry.
     */
	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}

	/**
     * Delete the Allure results of the previous run, so the Allure report shows only this run
     */
	@BeforeSuite
	public void cleanUp() {
		Util.cleanFilesOfDirectory(System.getProperty("user.dir") + "/allure-results");
	}

	/**
     * Create the Extent entry for the test method that is about to run, and log its name
     * @param  method  the test method (injected by TestNG).
     */
	@BeforeMethod
	public void beforeMethod(Method method) {
		logger = extent.createTest(method.getName());
		Util.setExtendedLogger(logger);
		Util.logInfoMessage("################################ " + method.getName() + " ################################");
	}
	
	/**
     * Create the Extent report, apply src/resources/extent-config.xml and add the environment details
     * @throws IOException  if the config file cannot be read.
     */
	@BeforeTest
	public void startReport() throws IOException {
		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
		spark.loadXMLConfig(new File(System.getProperty("user.dir") + "/src/resources/extent-config.xml"));
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "https://jsonplaceholder.typicode.com");
		extent.setSystemInfo("Environment", "API Testing");
		extent.setSystemInfo("User Name", "Niranjan Kumar Agri");
	}

	/**
     * Write the result of the finished test method (pass, fail with the error, or skip) to its Extent entry
     * @param  result  the result of the test method (injected by TestNG).
     */
	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, result.getName() + "test case is failed");
			logger.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, result.getName() + "test case is skipped");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, result.getName() + "test case is passed");
		}
	}

	/**
     * Write the Extent report to disk
     */
	@AfterTest
	public void endReport() {
		extent.flush();
	}
}
