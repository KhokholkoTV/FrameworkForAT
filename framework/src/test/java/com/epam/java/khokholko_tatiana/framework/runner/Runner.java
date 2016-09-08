package com.epam.java.khokholko_tatiana.framework.runner;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import com.epam.java.khokholko_tatiana.framework.exceptions.YandexException;
import com.epam.java.khokholko_tatiana.framework.settings.Setting;

/**
 * @author Tatiana Khokholko
 */
public class Runner {

	protected TestNG testNG = new TestNG();
	private String testNgConfig;

	public static void main(String[] args) {
		new Runner(args).run();
	}

	/**
	 * Method сreates a new command line owner that parses arguments/options
	 * 
	 * @author Tatiana Khokholko
	 */
	public Runner(String[] args) {
		Setting settings = new Setting();
		CmdLineParser parser = new CmdLineParser(settings);

		try {
			parser.parseArgument(args);
			testNgConfig = settings.testng;
			System.out.println("Setting: " + settings);
		}
		catch (CmdLineException e) {
			System.err.println(e.toString());
			parser.printUsage(System.out);
		}
	}

	public void run() {
		try {
			XmlSuite xmlSuite = new Parser(testNgConfig).parseToList().get(0);
			this.testNG.setCommandLineSuite(xmlSuite);
			this.testNG.run();
		}
		catch (Exception e) {
			throw new YandexException("Error running Test NG suite" + e.getMessage());
		}
	}

}