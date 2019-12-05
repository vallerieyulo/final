package com.accenture.loginclient.circuitbreaker;

import java.util.Base64;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.loginclient.domain.Account;
import com.accenture.loginclient.domain.LoginSession;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class LoginCircuitBreaker {

	@HystrixCommand(fallbackMethod = "reliable")
	public ModelAndView login(String username, String password) {
		ModelAndView mv = new ModelAndView();
		String url = "http://localhost:1997/loginservice/login";

		String authString = username + ":" + password;
		String authStr = Base64.getEncoder().encodeToString(authString.getBytes());

		Client client = new Client();
		WebResource wr = client.resource(url);
		ClientResponse cr = wr.header("Authorization", "Basic " + authStr).type(MediaType.APPLICATION_JSON)
				.get(ClientResponse.class);
		Account account = null;
		if (200 == cr.getStatus()) {
			try {
				account = new Account(cr.getEntity(String.class));
			} catch (JSONException e) {
				Logger logger = Logger.getLogger(this.getClass());
				logger.info(e);
			}
			LoginSession.getInstance().setAccount(account);
			account.addModelAndView(mv);
			if ("admin".equals(account.getRights())) {
				mv.setViewName("adminpage");
			} else {
				mv.setViewName("nonadminpage");
			}
		} else {
			mv.addObject("ERROR", "Incorrect username or password");
			mv.setViewName("login");
		}

		return mv;
	}

	public ModelAndView reliable(String username, String password) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("ERROR", "Server not responding...");
		mv.setViewName("login");
		return mv;
	}

}
