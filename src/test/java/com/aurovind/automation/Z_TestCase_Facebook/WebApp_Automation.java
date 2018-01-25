package com.aurovind.automation.Z_TestCase_Facebook;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.aurovind.AE.automation.utilities.UtilityFile;
import com.aurovind.automation.BaseTest;
import com.aurovind.automation.utilities.Constant;

public class WebApp_Automation extends BaseTest 
{
	@Test()
	public void LoginCheck() throws IOException, InterruptedException 
	{
		loginToApplication();
}
	
	/*@Test
	public void LaunchChrome_Method1() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Aurovind\\MYWORKSPACE\\OnlineAutomation\\chromedriver234.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.google.com");
	}*/
	
	
}