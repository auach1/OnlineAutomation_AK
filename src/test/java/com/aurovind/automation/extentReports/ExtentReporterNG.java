package com.aurovind.automation.extentReports;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.aurovind.automation.PageObjects.BaseClass;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.GridType;

public class ExtentReporterNG extends BaseClass {

	public ExtentReporterNG(WebDriver driver) throws IOException {
		super(driver);
	}
	
	//Variables Initialization
	
	public static ExtentReports extent = ExtentReports.get(ExtentReporterNG.class);
	public static String reportLocation = "./target/ExtentReports/Reports/";
	public static String imageLocation = "./target/ExtentReports/Reports/";
	public static String path = null;
	public static SoftAssert softAssertion = new SoftAssert();
	
	public void initExtentReporterNG(String testName)
	{
		File directoryLocation = new File(reportLocation);
		File directoryImage = new File(imageLocation);
		
		if(!directoryLocation.exists())
		{
			directoryLocation.mkdirs();
		}
		
		if(!directoryImage.exists()) 
		{
			directoryImage.mkdirs();
		}
		
		extent.init(reportLocation + "ExtentReport.html", false, DisplayOrder.BY_OLDEST_TO_LATEST, GridType.STANDARD);
		extent.config().documentTitle("Facebook Automation");
		extent.config().reportHeadline("Facebook Automation");
		extent.startTest(testName);
	}
	 
	public void endExtentReporterNG(String testName) {
		extent.endTest();
	}
}
