package com.epam.java.khokholko_tatiana.framework.ui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Class initializes WebDriver
 * 
 * @author Tatiana Khokholko
 */
public class Driver {

	WebDriver driver;

	public WebDriver getWebDriverInstance() {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;

	}
}
