package com.epam.java.khokholko_tatiana.framework.utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import com.epam.java.khokholko_tatiana.framework.YandexTest;

/**
 * Class event much handler
 * 
 * @author Tatiana Khokholko
 */
public class ScreenShot implements ITestListener {

	private static final String CANNOT_DELETE_FILE = "Cannot delete file ";
	private static final String ERROR = "Error - to make a screenshot ";
	private static final String AUTO_SCREEN_SHOT = "AutoScreenShot";
	private static final String WAY = "test-output/html/";

	private static final String SCREENSHOT_FOLDER = "screenShot";
	private static final Logger LOG = Logger.getLogger(ScreenShot.class);
	private static final String DEFAULT_MESSAGE = "ScreenShot =)";

	public static void deleteAllScreenShots() {
		File directory = new File(WAY + SCREENSHOT_FOLDER);
		File[] files = directory.listFiles();
		if (files != null && files.length > 0) {
			for (File file : files) {
				if (!file.delete()) {
					LOG.warn(CANNOT_DELETE_FILE + file);
				}
			}
		}
	}

	public static void make(WebDriver driver) {
		make(driver, DEFAULT_MESSAGE);
	}

	public static void make(WebDriver driver, String message) {
		if (driver == null) {
			return;
		}
		try {
			File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFileToDirectory(screenShot, new File(WAY + SCREENSHOT_FOLDER));
			String logMessage = "<a href='" + SCREENSHOT_FOLDER + "/" + screenShot.getName() + "'>" + message + "</a>";
			LOG.warn(logMessage);
		}
		catch (Exception e) {
			LOG.error(ERROR + e);
			throw new RuntimeException(e);
		}
	}

	private void screenMake(ITestResult result) {
		Object instance = result.getInstance();

		if (instance == null) {
			return;
		}
		if (!(instance instanceof YandexTest)) {
			return;
		}
		YandexTest yandexTest = (YandexTest) instance;
		WebDriver driver = yandexTest.getDriver();

		if (driver != null) {
			ScreenShot.make(driver, AUTO_SCREEN_SHOT);
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {
		screenMake(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		screenMake(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		screenMake(result);
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {

	}
}
