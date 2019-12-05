package com.accenture.bars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * LoginNavigationController - controller for navigating the Login Page from
 * Bars
 *
 */
@Controller
public class LoginNavigationController {

	@RequestMapping(value = "/account")
	public String goToAccount() {
		String loginUrl = "http://localhost:2018/home";

		loginUrl = "redirect:" + loginUrl;

		return loginUrl;
	}

	@RequestMapping(value = "/logout")
	public String goToLogout() {
		String loginUrl = "http://localhost:2018/";

		loginUrl = "redirect:" + loginUrl;

		return loginUrl;
	}

	@RequestMapping(value = "/addaccount")
	public String goToAddAccount() {
		String loginUrl = "http://localhost:2018/addaccount";

		loginUrl = "redirect:" + loginUrl;

		return loginUrl;
	}

	@RequestMapping(value = "/listaccounts")
	public String goToListAccount() {
		String loginUrl = "http://localhost:2018/listaccounts";

		loginUrl = "redirect:" + loginUrl;

		return loginUrl;
	}

}
