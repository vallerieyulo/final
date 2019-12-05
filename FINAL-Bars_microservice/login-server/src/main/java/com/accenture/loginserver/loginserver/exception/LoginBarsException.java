package com.accenture.loginserver.loginserver.exception;

@SuppressWarnings("serial")
public class LoginBarsException extends Exception {

	public static final String INVALID_EMAIL = "Invalid format for email.";
	public static final String EMPTY_EMAIL = "Email field is empty.";
	public static final String EMPTY_USERNAME = "Username field is empty.";
	public static final String EMPTY_PASSWORD = "Password field is empty.";
	public static final String EMPTY_FIRSTNAME = "Firstname field is empty.";
	public static final String EMPTY_LASTNAME = "Lastname field is empty.";

	public LoginBarsException(String message) {
		super(message);
	}

	public LoginBarsException(String message, Throwable cause) {
		super(message, cause);
	}

}
