package com.epam.java.khokholko_tatiana.framework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import com.epam.java.khokholko_tatiana.framework.businessobject.Account;
import com.epam.java.khokholko_tatiana.framework.businessobject.Letter;
import com.epam.java.khokholko_tatiana.framework.service.YandexService;
import com.epam.java.khokholko_tatiana.framework.ui.Driver;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.DraftsPage;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.MainPage;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.SendedLettersPage;
import com.epam.java.khokholko_tatiana.framework.utility.ScreenShot;

/**
 * @author Tatiana Khokholko
 */
@Listeners({ HTMLReporter.class, ScreenShot.class })
public class YandexTest extends BaseTest {

	private static final Logger LOG = Logger.getLogger(YandexTest.class);
	private static final String TITLE_MY_WORK_YANDEX = "//div[contains(@title, 'MySeleniumWork@yandex.ru')]";
	private static final String ERROR_YANDEX = "Yandex does not exist";
	private static final String FIELD_RECIPIENT_DOES_NOT_EXISTS = "Field recipient does not exists!";
	private static final String VALUE = "value";
	private YandexService yandexService;
	private Account account;
	private MainPage main;
	private DraftsPage draft;
	private SendedLettersPage sendedLetters;
	private Object[][] parameter = new Object[2][2];
	Driver yandex = new Driver();
	Locale locale = new Locale("en", "US");
	ResourceBundle resource = ResourceBundle.getBundle("config", locale);

	@DataProvider(name = "parametersForLetter")
	public Object[][] parametersForLetter() {
		List<Letter[]> parameterListLetter = new ArrayList<>();

		try (BufferedReader input = new BufferedReader(new FileReader("ParametersProvider.txt"))) {
			String line;
			while ((line = input.readLine()) != null) {
				String[] letterArr = line.split(",");
				Letter letter = new Letter(letterArr[0], letterArr[1], letterArr[2]);
				Letter[] lt = { letter };
				parameterListLetter.add(lt);
			}
			parameter = parameterListLetter.toArray(new Letter[][] {});
		}
		catch (FileNotFoundException e1) {
			System.err.println(e1);
		}
		catch (IOException e) {
			System.err.println(e);
		}
		return parameter;
	}

	@Test
	public void loginTest() {
		LOG.info("start 'loginTest'");
		yandexService = new YandexService(driver);
		System.out.println("mainurl" + resource.getString("mainURL"));
		driver.get(resource.getString("mainURL"));
		account = new Account(resource.getString("login"), resource.getString("password"));
		yandexService.loginYandex(account);

		FluentWait<WebDriver> wait = waitTime();
		WebElement yandex = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(TITLE_MY_WORK_YANDEX)));
		Assert.assertTrue(yandex.isDisplayed(), ERROR_YANDEX);

		ScreenShot.make(driver);
		LOG.info("finish 'loginTest'");

	}

	@Test(dependsOnMethods = "loginTest", dataProvider = "parametersForLetter")
	public void writeLetterDrafts(Letter letter) {
		LOG.info("start 'writeLetterDrafts'");
		main = new MainPage(driver);
		yandexService.doDrafts(letter);

		waitTime().until(new ExpectedCondition<WebElement>() {

			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.className("mail-Compose-Field-Input"));
			}
		});

		boolean buttonSent = isElementPresented(By.className("mail-Compose-Field-Input"));
		Assert.assertTrue(buttonSent, FIELD_RECIPIENT_DOES_NOT_EXISTS);
		main.fillLetter(letter);
		LOG.info("finish 'writeLetterDrafts'");
	}

	@Test(dependsOnMethods = "writeLetterDrafts", dataProvider = "parametersForLetter")
	public void letterDraftsCheck(Letter letter) throws InterruptedException {
		LOG.info("start 'letterDraftsCheck'");
		draft = new DraftsPage(driver);
		yandexService.checkDrafts(letter);
		Assert.assertEquals(letter.getSubj(), draft.getXpathSubj().getAttribute(VALUE));
		Assert.assertEquals(letter.getText(), draft.getXpathBodyField().getText());
		driver.navigate().back();
		LOG.info("finish 'letterDraftsCheck'");
	}

	@Test(dependsOnMethods = "letterDraftsCheck")
	public void discardDrafts() {
		LOG.info("start 'discardDrafts'");
		DraftsPage drafts = new DraftsPage(driver);
		drafts.discardDrafts();
		LOG.info("finish 'discardDrafts'");
	}

	@Test(dependsOnMethods = "discardDrafts", dataProvider = "parametersForLetter")
	public void yandexSendLetter(Letter letter) throws InterruptedException {
		LOG.info("start 'yandexSendLetter'");
		yandexService.doLetters(letter);

		FluentWait<WebDriver> wait = waitTime();

		WebElement messageSent = wait.until(ExpectedConditions.presenceOfElementLocated(
		        By.xpath("//span[@class ='mail-NestedList-Item-Name js-folders-item-name']")));
		Assert.assertEquals(messageSent.getText(), "Входящие");
		LOG.info("finish 'yandexSendLetter'");
	}

	@Test(dependsOnMethods = "yandexSendLetter", dataProvider = "parametersForLetter") // не
	                                                                                   // работает
	public void checkSentMails(Letter letter) throws InterruptedException {
		LOG.info("start 'checkSentMails'");
		sendedLetters = new SendedLettersPage(driver);
		yandexService.checkSendLetters(letter);

		WebElement sentSubj = driver.findElement(By.xpath(
		        "//div[contains(text(), '" + letter.getSubj() + "')]"));
		WebElement sentText = driver.findElement(By.xpath("//div[contains(text(), '" +
		        letter.getText() + "')]"));
		Assert.assertEquals(letter.getSubj(), sentSubj.getText());
		Assert.assertEquals(letter.getText(), sentText.getText());

		sendedLetters.getXpathSentLetters().click();
		LOG.info("finish 'checkSentMails'");
	}

	@Test(dependsOnMethods = "checkSentMails")
	public void discardSentLetters() {
		LOG.info("start 'discardSentLetters'");
		sendedLetters.discardSentLetters();
		LOG.info("finish 'discardSentLetters'");
	}

	@Test(dependsOnMethods = "discardSentLetters")
	public void exit() {
		LOG.info("start 'logOut'");
		yandexService.logOut();
		LOG.info("finish 'logOut'");
	}
}
