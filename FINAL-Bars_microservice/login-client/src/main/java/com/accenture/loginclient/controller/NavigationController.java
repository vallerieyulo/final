package com.accenture.loginclient.controller;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.loginclient.domain.Account;
import com.accenture.loginclient.domain.LoginSession;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@EnableCircuitBreaker
@Controller
public class NavigationController {

	@RequestMapping(value = "/")
	public String enterCredentials() {
		LoginSession.getInstance().setAccount(null);
		return "login";
	}

	@RequestMapping(value = "/addaccount")
	public ModelAndView registerAccount() throws JSONException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addaccount");
		LoginSession.getInstance().getCurrentAccount().addModelAndView(mv);
		return mv;
	}

	@RequestMapping(value = "/update")
	public ModelAndView updatePage(@RequestParam("username") String username) throws JSONException {
		ModelAndView mv = new ModelAndView();

		if (LoginSession.getInstance().getCurrentAccount().getRights().equals("admin")) {
			String url = "http://localhost:1997/crud/getaccount/" + username;
			Client client = new Client();
			WebResource wr = client.resource(url);
			ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			Account account = new Account(cr.getEntity(String.class));
			account.addModelAndView(mv);
			mv.setViewName("updateaccount");
		} else {
			mv.setViewName("login");
		}

		return mv;
	}

	@RequestMapping(value = "/listaccounts")
	public ModelAndView listAccounts() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("listaccounts");
		LoginSession.getInstance().getCurrentAccount().addModelAndView(mv);
		return mv;
	}

	@RequestMapping(value = "/bars")
	public String goToBars() {
		String barsUrl = "http://localhost:2019/bars?rights="
				+ LoginSession.getInstance().getCurrentAccount().getRights() + "&user="
				+ LoginSession.getInstance().getCurrentAccount().getUsername();
		barsUrl = "redirect:" + barsUrl;

		return barsUrl;
	}

	@RequestMapping(value = "/home")
	public ModelAndView goToAccount() {
		ModelAndView mv = new ModelAndView();

		if ("admin".equals(LoginSession.getInstance().getCurrentAccount().getRights())) {
			mv.setViewName("adminpage");
			LoginSession.getInstance().getCurrentAccount().addModelAndView(mv);
		} else if ("user".equals(LoginSession.getInstance().getCurrentAccount().getRights())) {
			mv.setViewName("nonadminpage");
			LoginSession.getInstance().getCurrentAccount().addModelAndView(mv);
		} else {
			LoginSession.getInstance().setAccount(null);
			mv.setViewName("login");
		}

		return mv;
	}
}
