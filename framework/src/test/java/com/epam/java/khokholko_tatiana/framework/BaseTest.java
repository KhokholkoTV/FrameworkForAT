package com.epam.java.khokholko_tatiana.framework;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.epam.java.khokholko_tatiana.framework.service.YandexService;
import com.epam.java.khokholko_tatiana.framework.ui.Driver;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.SendedLettersPage;

/**
 * Class before and after YandexTest class
 * 
 * @author Tatiana Khokholko
 */
public class BaseTest {

	private static final Logger LOG = Logger.getLogger(YandexTest.class);
	protected WebDriver driver;
	Driver yandex = new Driver();
	SendedLettersPage delete;

	public WebDriver getDriver() {
		return driver;
	}

	public boolean isElementPresented(By by) {
		try {
			WebElement webElement = driver.findElement(by);
			return webElement.isDisplayed();

		}
		catch (Exception e) {
			return false;
		}
	}

	public FluentWait<WebDriver> waitTime() {

		return new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
		        .pollingEvery(2, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);
	}

	@BeforeTest
	public WebDriver startBrowser() {

		LOG.warn("start 'startBrowser'");
		LOG.warn("startBrowser 'delete old screenshots'");
		driver = yandex.getWebDriverInstance();
		new YandexService(driver);
		LOG.warn("finish 'startBrowser'");
		return driver;
	}

	@AfterTest
	public void closeBrowser() {
		LOG.warn("start 'closeBrowser'");

		if (this.driver != null) {
			this.driver.manage().deleteAllCookies();
			this.driver.quit();
		}
		LOG.warn("finish 'closeBrowser'");
	}
}