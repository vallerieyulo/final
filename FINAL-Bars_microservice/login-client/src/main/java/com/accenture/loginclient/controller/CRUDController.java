package com.accenture.loginclient.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.loginclient.circuitbreaker.CRUDCircuitBreaker;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
@EnableCircuitBreaker
@Controller
public class CRUDController {

	@Autowired
	CRUDCircuitBreaker crudcb;

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(
			@RequestParam("username_input") String username,
			@RequestParam("password_input") String password,
			@RequestParam("firstname_input") String firstname,
			@RequestParam("lastname_input") String lastname,
			@RequestParam("email_input") String email,
			@RequestParam("rights_input") String rights) throws JSONException {
		ModelAndView mv = crudcb.register(username, password, firstname, lastname, email, rights);
		return mv;
	}

	@RequestMapping(value = "/updateaccount")
	public ModelAndView updateAccount(
			@RequestParam("username_input") String username,
			@RequestParam("password_input") String password,
			@RequestParam("firstname_input") String firstname,
			@RequestParam("lastname_input") String lastname,
			@RequestParam("email_input") String email,
			@RequestParam("rights_input") String rights) throws UniformInterfaceException, ClientHandlerException, JSONException {
		ModelAndView mv = crudcb.updateAccount(username, password, firstname, lastname, email, rights);
		return mv;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView deleteAccount(@RequestParam("username") String username) throws JSONException {
		ModelAndView mv = crudcb.deleteAccount(username);
		return mv;
	}

}
