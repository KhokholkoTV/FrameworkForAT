package com.epam.java.khokholko_tatiana.framework.settings;

import org.kohsuke.args4j.Option;

/**
 * Class used parameters command line
 * 
 * @author Tatiana Khokholko
 */
public class Setting {

	@Option(name = "--testng", usage = "set path to TestNg xml", required = true)
	public String testng;

	@Option(name = "--login", usage = "set connection user login", required = true)
	public String login;

	@Option(name = "--password", usage = "set connection password", required = true)
	public String password;

	@Option(name = "--url", usage = "set connection url", required = true)
	public String url;

	@Override
	public String toString() {
		return "\ntestng: " + testng + "\nlogin: " + login + "\npassword: " + password + "\nurl: " + url;
	}

}