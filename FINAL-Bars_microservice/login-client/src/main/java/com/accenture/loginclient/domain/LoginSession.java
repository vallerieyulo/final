package com.accenture.loginclient.domain;

public class LoginSession {

	private static LoginSession instance;
	private Account account;

	private LoginSession() {

	}

	public static LoginSession getInstance() {
		if(instance == null) {
			instance = new LoginSession();
		}

		return instance;
	}

	public void setAccount(Account newAccount) {
		account = newAccount;
	}

	public Account getCurrentAccount() {
		return account;
	}

}
