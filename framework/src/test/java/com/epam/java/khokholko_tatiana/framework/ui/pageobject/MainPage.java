package com.epam.java.khokholko_tatiana.framework.ui.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.java.khokholko_tatiana.framework.businessobject.Letter;

/**
 * Class works with MainPage page
 * 
 * @author Tatiana Khokholko
 */
public class MainPage {

	WebDriver driver;
	Actions actions = null;
	private static final Logger LOG = Logger.getLogger(MainPage.class);

	@FindBy(xpath = "//a[@title='Написать (w, c)']")
	private WebElement buttonWrite;

	@FindBy(xpath = "//button[@id= 'nb-21')]")
	private WebElement newSendButton;

	@FindBy(className = "mail-Compose-Field-Input")
	private WebElement toRecipient;

	@FindBy(name = "subj")
	private WebElement subject;

	@FindBy(xpath = "//div[@role='textbox']")
	private WebElement bodyField;

	@FindBy(xpath = "//a[@href='#draft']")
	private WebElement drafts;

	@FindBy(xpath = "//button[@data-action='save']")
	private WebElement buttonSaveDrafts;

	@FindBy(xpath = "//button[@title = 'Отправить письмо (Ctrl + Enter)']")
	private WebElement buttonSentMessage;

	@FindBy(xpath = "//a[@href ='#sent']")
	private WebElement sentLetters;

	@FindBy(xpath = "//div[contains(@title, 'MySeleniumWork@yandex.ru')]")
	private WebElement menuButton;

	@FindBy(xpath = "//div[@class ='b-mail-dropdown__item']")
	private WebElement logOut;

	public MainPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.actions = new Actions(this.driver);
	}

	public void writeLetter(Letter letter) {
		LOG.info("start 'writeLetter'");
		LOG.info(letter.toString());
		buttonWrite.click();
	}

	public void fillLetter(Letter letter) {
		toRecipient.sendKeys(letter.getRecipient());
		subject.sendKeys(letter.getSubj());
		bodyField.sendKeys(letter.getText());
		actions.click(drafts).build().perform();
		actions.click(buttonSaveDrafts).build().perform();
		driver.switchTo().defaultContent();
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.info("finish 'writeLetter'");
	}

	public void logOut() {
		LOG.info("start 'logOut'");
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", menuButton);
		logOut.click();
		LOG.info("finish 'logOut'");
	}

}