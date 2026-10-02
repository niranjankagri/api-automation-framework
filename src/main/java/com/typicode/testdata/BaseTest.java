package com.typicode.testdata;

import java.io.File;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
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
		logger = extent.startTest(method.getName());
		Util.setExtendedLogger(logger);
		Util.logInfoMessage("################################ " + method.getName() + " ################################");
	}
	
	@BeforeTest
	public void startReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/STMExtentReport.html", true);
		extent.addSystemInfo("Host Name", "https://jsonplaceholder.typicode.com")
			  .addSystemInfo("Environment", "API Testing")
		      .addSystemInfo("User Name", "Niranjan Kumar Agri");
		extent.loadConfig(new File(System.getProperty("user.dir") + "/src/resources/extent-config.xml"));
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, result.getName() + "test case is failed");
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, result.getName() + "test case is skipped");
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, result.getName() + "test case is passed");
		}
		extent.endTest(logger);
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
}
