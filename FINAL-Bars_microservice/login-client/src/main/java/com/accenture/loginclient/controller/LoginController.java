package com.accenture.loginclient.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.loginclient.circuitbreaker.LoginCircuitBreaker;

@EnableCircuitBreaker
@Controller
public class LoginController {

	@Autowired
	LoginCircuitBreaker logincb;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView enterCredentials(
			@RequestParam("username") String username,
			@RequestParam("password") String password)
			throws JSONException {

		ModelAndView mv = logincb.login(username, password);

		return mv;
	}

}
