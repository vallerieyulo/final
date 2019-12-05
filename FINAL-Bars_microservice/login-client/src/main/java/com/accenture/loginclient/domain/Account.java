package com.accenture.loginclient.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;


public class Account {

	private String username;
	private String pw;
	private String email;
	private String rights;
	private String firstName;
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

	public Account(String jsonObject) throws JSONException {
		JSONObject accountJson = new JSONObject(jsonObject);
		this.username = (String) accountJson.getString("username");
		this.pw = (String) accountJson.getString("pw");
		this.email = (String) accountJson.getString("email");
		this.rights = (String) accountJson.getString("rights");
		this.firstName = (String) accountJson.getString("firstName");
		this.lastName = (String) accountJson.getString("lastName");
	}

	public void addModelAndView(ModelAndView mv) {
		mv.addObject("firstname", this.firstName);
		mv.addObject("lastname", this.lastName);
		mv.addObject("rights", this.rights);
		mv.addObject("email", this.email);
		mv.addObject("username", this.username);
		mv.addObject("password", this.pw);
	}

	public String toJson() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", this.username);
		jsonObject.put("pw", this.pw);
		jsonObject.put("email", this.email);
		jsonObject.put("rights", this.rights);
		jsonObject.put("firstName", this.firstName);
		jsonObject.put("lastName", this.lastName);
		return jsonObject.toString();
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
