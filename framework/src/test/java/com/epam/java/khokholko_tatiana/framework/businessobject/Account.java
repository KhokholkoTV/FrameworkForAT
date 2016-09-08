package com.epam.java.khokholko_tatiana.framework.businessobject;

/**
 * Class binary
 * 
 * @author Tatiana Khokholko
 */
public class Account {

	private String login;
	private String password;

	public Account(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}