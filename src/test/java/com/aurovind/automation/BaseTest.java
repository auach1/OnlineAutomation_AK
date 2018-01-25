package com.aurovind.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import com.aurovind.automation.PageObjects.Dbconnection;
import com.aurovind.automation.PageObjects.Practice;
import com.aurovind.automation.extentReports.ExtentReporterNG;
import com.aurovind.automation.utilities.Constant;
import com.aurovind.automation.utilities.TestData;
import com.aurovind.automation.utilities.ZipDirectory;

public class BaseTest extends TestData {

	//Variables Initialization
	
	public WebDriver browser;
	public static Properties CONFIG = null;
	public static boolean isInitialized = false;
	public static Actions act = null;
	public static WebDriverWait wait30 = null;
	public static WebDriverWait wait60 = null;
	public static WebDriverWait wait90 = null;
	public ExtentReporterNG report;
	public static DesiredCapabilities cap = null;
	
	
	public WebDriver getBrowser() {
		return this.browser;
	}
	
	
	/**
	 * Executes every time before execution
	 * @author Aurovind Acharya
	 * @param result
	 * @throws Exception
	 **/
	
	@BeforeClass
	public void initialization() throws Exception
	{
	initialize();
	/*Dbconnection.dbConnection();
	startTestcase("start");*/
	}

	/**
	 * Executes every time after class execution
	 * @author Aurovind Acharya
	 * @param result
	 * @throws Exception
	 **/
	
	@AfterClass
	public void afterClass()
	{
		
	}
	
	/**
	 * Executes every time before method execution
	 * @author Aurovind Acharya
	 * @param result
	 * @throws Exception
	 **/
	
	@BeforeMethod
	public void beforeMethod() throws Exception
	{
	}
	
	/**
	 * Executes every time after method execution
	 * @author Aurovind Acharya
	 * @param result
	 * @throws Exception
	 **/
	
	@AfterMethod
	public void afterMethod() throws Exception
	{
	}
	
	
	/**
	 * Executes every time after suite execution
	 * @author Aurovind Acharya
	 * @param result
	 * @throws Exception
	 **/
	
	@AfterSuite
	public void afterSuite() throws Exception
	{
		
	}
	
	
	public void copyFileToDir(String src, String dest) throws IOException
	{
		File srcFile = new File(src);
		File destDir = new File(dest);
		FileUtils.copyFileToDirectory(srcFile, destDir);
		
	}

	public void createDirectory(String directoryNameWithPath)
	{
		File file = new File(directoryNameWithPath);
		if(!file.exists())
		{
			if(file.mkdir()) 
			{
				ReportLog("[BaseTest] Directory AFEReports created!");
			}
			else
			{
				ReportLog("[BaseTest] Failed to create AFEReports Directory!");
			}
			
			}
		}
		

	/**
	 * Prints the time and executing test case name when execution starts
	 * @author Aurovind Acharya
	 * @throws Exception
	 **/
	public void startTestcase(String start_End) 
	{
		ReportLog("[BaseTest] "+start_End+" of test case Execution :: "
				+ this.getClass().getSimpleName());
		Date sDate = new Date();
		SimpleDateFormat dStartDate = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		String sTestCaseStartTime = dStartDate.format(sDate);
		ReportLog("[BaseTest] Testcase Execution "+start_End+" time :: "
				+ sTestCaseStartTime);
		
	}
	
	@BeforeTest
	public void beforeTest() throws Exception {
		//initialize test data
	}


	/**
	 * Loads CONFIG.properties file and initializes test data
	 * @author Aurovind Acharya
	 * @throws IOException
	 **/
	
	public void initialize() throws IOException 
	{
		if(!isInitialized) 
		{
			ReportLog("[BaseTest] Starting of configuration for Test Execution");
			ReportLog("[BaseTest]Loading Property Files");
			CONFIG = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
					+ "/src/test/java/com/aurovind/automation/config/CONFIG.properties");
			CONFIG.load(ip);
			ReportLog("[BaseTest]Loaded Property Files");
			ReportLog("[BaseTest]Ending of Configuration");
			isInitialized = true;
		}	
		
	}
	
	
	public void initializeExtentReport() throws IOException{
		report  = new ExtentReporterNG(browser);
		report.initExtentReporterNG(this.getClass().getSimpleName());
	}
	
	
	/*public void endExtentReport() throws IOException{
		report.endExtentReporterNG();
	}*/
	
	public void loginToApplication() throws IOException, InterruptedException {
		try {
			openBrowser();
			
			String loginURL = CONFIG.getProperty("application_url");
			browser.get(loginURL);
			browser.manage().deleteAllCookies();
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void ReportLog(String sMessage) {
		Date sDate = new Date();
		SimpleDateFormat dStartDate = new SimpleDateFormat("HH:mm:ss");
		String time = dStartDate.format(sDate);
		Reporter.log("["+time+"]" + " " + sMessage + "\n" , true);
		
	}
	
	public void openBrowser() throws IOException
	{
		if(CONFIG.getProperty("browserType").equals("Firefox"))
		{
			ProfilesIni allProfiles = new ProfilesIni();
			FirefoxProfile profile = allProfiles.getProfile("selenium");
			browser = new FirefoxDriver(profile);
			act  = new Actions(browser);
		//Initialize the Extent Report
			report = new ExtentReporterNG(browser);
			report.initExtentReporterNG("Login Check");
		}
		else if(CONFIG.getProperty("browserType").equals("Chrome234"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver234.exe");
			System.setProperty("org.apache.commons.logging.Log", 
					"org.apache.commons.logging.impl.NoOpLog");
			//define preferences
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			//define options
			
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("download.default_directory", Constant.Downloads);
			
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			
			cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			
			//options.addArguments("--disable-web-security");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("test-type");
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("start-maximized");
			options.addArguments("disable-infobars");
			
			
			browser = new ChromeDriver(options);
			browser.manage().deleteAllCookies();
		}
			wait30 = new WebDriverWait(browser, 30);
			//wait60 = new WebDriverWait(browser, 60);
			//wait90 = new WebDriverWait(browser, 90);
			
			
		}
	
	
	
	public void datasetCombination(int count)
	{
		ReportLog("[BaseTest] Execution of " + this.getClass().getSimpleName()
				+ " for Data Set :: " + count);
	}
	
}
