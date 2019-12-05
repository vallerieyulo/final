package com.accenture.loginserver.loginserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Account entity in login_db
 *
 */
@Entity
public class Account {

	@Id
	@Column(name="username")
	private String username;

	@Column(name="password")
	private String pw;

	@Column(name="email")
	private String email;

	@Column(name="rights")
	private String rights;

	@Column(name="firstname")
	private String firstName;

	@Column(name="lastname")
	private String lastName;

	public Account () {}

	public Account(String username, String pw, String email, String rights,
			String firstName, String lastName) {

		this.username = username;
		this.pw = pw;
		this.email = email;
		this.rights = rights;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



}
