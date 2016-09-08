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

import com.epam.java.khokholko_tatiana.framework.businessobject.Letter;

/**
 * Class works with DraftsPage page
 * 
 * @author Tatiana Khokholko
 */
public class DraftsPage {

	WebDriver driver;
	Actions actions = null;
	private static final Logger LOG = Logger.getLogger(MainPage.class);

	@FindBy(name = "subj")
	private WebElement subject;

	public WebElement getXpathSubj() {
		return subject;
	}

	@FindBy(xpath = "//div[@role='textbox']")
	private WebElement bodyField;

	public WebElement getXpathBodyField() {
		return bodyField;
	}

	@FindBy(xpath = "//span[@class ='checkbox_view']")
	private WebElement selectDrafts;

	@FindBy(xpath = "//div[@title ='Удалить (Delete)']")
	private WebElement deleteDrafts;

	public DraftsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.actions = new Actions(this.driver);
	}

	public void checkDrafts(Letter letter) throws InterruptedException {
		LOG.info("start 'checkDrafts'");
		Thread.sleep(1000);
		WebElement lastDraft = driver.findElement(By.xpath("//span[@title='" + letter.getSubj() + "']"));
		lastDraft.click();
		LOG.info("finish 'checkDrafts'");
	}

	public void discardDrafts() {
		LOG.info("start 'discardDrafts'");
		selectDrafts.click();
		deleteDrafts.click();
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		        .withTimeout(15, TimeUnit.SECONDS)
		        .pollingEvery(3, TimeUnit.SECONDS)
		        .ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class ='b-messages__placeholder']")));
		LOG.info("finish 'discardDrafts'");
	}

}
