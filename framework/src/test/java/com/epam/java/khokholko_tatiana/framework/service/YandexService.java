package com.epam.java.khokholko_tatiana.framework.service;

import org.openqa.selenium.WebDriver;

import com.epam.java.khokholko_tatiana.framework.businessobject.Account;
import com.epam.java.khokholko_tatiana.framework.businessobject.Letter;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.DraftsPage;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.LoginPage;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.MainPage;
import com.epam.java.khokholko_tatiana.framework.ui.pageobject.SendedLettersPage;

/**
 * Class for service
 * 
 * @author Tatiana Khokholko
 */
public class YandexService {

	WebDriver driver;

	public YandexService(WebDriver driver) {
		this.driver = driver;
	}

	public void loginYandex(Account account) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setLogin(account).setPassword(account).clickSubmitButton();
	}

	public void doLetters(Letter letter) {
		SendedLettersPage sendedLetters = new SendedLettersPage(driver);
		try {
			sendedLetters.writeLetterAndSend(letter);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkSendLetters(Letter letter) {
		SendedLettersPage sendedLetters = new SendedLettersPage(driver);
		try {
			sendedLetters.checkSentLetters(letter);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkDrafts(Letter letter) {
		DraftsPage drafts = new DraftsPage(driver);
		try {
			drafts.checkDrafts(letter);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doDrafts(Letter letter) {
		MainPage main = new MainPage(driver);
		main.writeLetter(letter);

	}

	public void logOut() {
		MainPage main = new MainPage(driver);
		main.logOut();
	}

}
