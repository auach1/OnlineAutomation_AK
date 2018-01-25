package com.aurovind.automation.PageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	public static WebDriver driver;
	
	public BaseClass(WebDriver driver) throws IOException
	{
		BaseClass.driver = driver;
	}
	
}
