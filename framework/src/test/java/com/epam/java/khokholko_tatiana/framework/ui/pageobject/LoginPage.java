package com.epam.java.khokholko_tatiana.framework.ui.pageobject;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.epam.java.khokholko_tatiana.framework.businessobject.Account;

/**
 * Class works with Login page
 * 
 * @author Tatiana Khokholko
 */
public class LoginPage {

	private static final String ENTER = "//button[@type = 'submit']";
	private static final Logger LOG = Logger.getLogger(LoginPage.class);
	WebDriver driver;

	@FindBy(name = "login")
	private WebElement loginIn;

	@FindBy(name = "passwd")
	private WebElement passwordIn;

	@FindBy(how = How.XPATH, using = ENTER)
	private WebElement submitButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public LoginPage setLogin(Account account) {
		LOG.info("start 'setLogin'");
		LOG.info("Login:" + account.getLogin());
		new Actions(driver).sendKeys().sendKeys(loginIn, account.getLogin()).build().perform();
		LOG.info("finish 'setLogin'");
		return this;
	}

	public LoginPage setPassword(Account account) {
		LOG.info("start 'setPassword'");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		        .withTimeout(15, TimeUnit.SECONDS)
		        .pollingEvery(3, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);

		WebElement passwordIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("passwd")));
		new Actions(driver).sendKeys(passwordIn, account.getPassword()).build().perform();
		LOG.info("finish 'setPassword'");
		return this;
	}

	public MainPage clickSubmitButton() {
		LOG.info("start 'clickSubmitButton'");
		new Actions(driver).click(submitButton).build().perform();
		LOG.info("finish 'clickSubmitButton'");
		return new MainPage(this.driver);

	}

}