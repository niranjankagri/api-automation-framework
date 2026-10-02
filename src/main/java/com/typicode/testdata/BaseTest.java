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

public abstract class BaseTest extends TestDataLoader {

	ExtentReports extent;
	ExtentTest logger;
	
	public ExtentTest getLogger() {
		return logger;
	}

	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}
	
	@BeforeSuite
	public void cleanUp() {
		Util.cleanFilesOfDirectory(System.getProperty("user.dir") + "/allure-results");
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		logger = extent.createTest(method.getName());
		Util.setExtendedLogger(logger);
		Util.logInfoMessage("################################ " + method.getName() + " ################################");
	}
	
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

	@AfterTest
	public void endReport() {
		extent.flush();
	}
}
