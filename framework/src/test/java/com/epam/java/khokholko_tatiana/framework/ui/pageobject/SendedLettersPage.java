package com.epam.java.khokholko_tatiana.framework.ui.pageobject;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.epam.java.khokholko_tatiana.framework.BaseTest;
import com.epam.java.khokholko_tatiana.framework.businessobject.Letter;

/**
 * Class works with SendedLettersPage page
 * 
 * @author Tatiana Khokholko
 */
public class SendedLettersPage {

	WebDriver driver;
	Actions actions = null;
	BaseTest base = new BaseTest();
	private static final Logger LOG = Logger.getLogger(MainPage.class);

	@FindBy(className = "mail-Compose-Field-Input")
	private WebElement toRecipient;

	@FindBy(xpath = "//a[@title='Написать (w, c)']")
	private WebElement buttonWrite;

	@FindBy(name = "subj")
	private WebElement subject;

	public WebElement getXpathSubject() {
		return subject;
	}

	@FindBy(xpath = "//div[@role='textbox']")
	private WebElement bodyField;

	public WebElement getXpathBodyField() {
		return bodyField;
	}

	@FindBy(xpath = "//button[@title = 'Отправить письмо (Ctrl + Enter)']")
	private WebElement buttonSentMessage;

	@FindBy(xpath = "//a[@href ='#sent']")
	private WebElement sentLetters;

	public WebElement getXpathSentLetters() {
		return sentLetters;
	}

	@FindBy(xpath = "//label[@class ='mail-Toolbar-Item-Checkbox']")
	private WebElement selectLetters;

	@FindBy(xpath = "//div[@title ='Удалить (Delete)']")
	private WebElement deleteLetters;

	@FindBy(className = "mail-Compose-Field-Input")
	private WebElement sendRecipient;

	public SendedLettersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.actions = new Actions(this.driver);
	}

	public void writeLetterAndSend(Letter letter) throws InterruptedException {
		LOG.info("start 'writeLetterAndSend'");
		buttonWrite.click();
		actions.click(sendRecipient).build().perform();
		actions.sendKeys(letter.getRecipient()).build().perform();
		subject.sendKeys(letter.getSubj());
		bodyField.sendKeys(letter.getText());
		buttonSentMessage.click();
		Thread.sleep(1000);
		LOG.info("finish 'writeLetterAndSend'");
	}

	public void checkSentLetters(Letter letter) throws InterruptedException {
		LOG.info("start 'checkSentLetters'");
		new Actions(driver).click(sentLetters).build().perform();
		Thread.sleep(1000);
		WebElement lastSentLetter = driver.findElement(By.xpath("//span[@title='" + letter.getSubj() + "']"));
		lastSentLetter.click();
		LOG.info("finish 'checkSentLetters'");
	}

	public void discardSentLetters() {
		LOG.info("start 'discardSentLetters'");
		selectLetters.click();
		deleteLetters.click();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		        .withTimeout(15, TimeUnit.SECONDS)
		        .pollingEvery(3, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class ='b-messages__placeholder'] ")));
		LOG.info("finish 'discardSentLetters'");
	}

}
