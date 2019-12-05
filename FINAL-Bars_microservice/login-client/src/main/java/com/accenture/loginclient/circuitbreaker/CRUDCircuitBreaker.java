package com.accenture.loginclient.circuitbreaker;

import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.loginclient.domain.Account;
import com.accenture.loginclient.domain.LoginSession;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

@Service
public class CRUDCircuitBreaker {

	@HystrixCommand(fallbackMethod = "registerError")
	public ModelAndView register(String username, String password,
			String firstname, String lastname, String email, String rights)
			throws JSONException {
		ModelAndView mv = new ModelAndView();
		Account account = new Account(username, password, email, rights,
				firstname, lastname);

		if (LoginSession.getInstance().getCurrentAccount().getRights()
				.equals("admin")) {
			String url = "http://localhost:1997/crud/registeraccount";
			Client client = new Client();
			WebResource wr = client.resource(url);
			ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).post(
					ClientResponse.class, account.toJson());
			String response = cr.getEntity(String.class);
			JSONObject jsonObject;

			if (200 == cr.getStatus()) {
				mv.addObject("INFO", response);
				LoginSession.getInstance().getCurrentAccount()
						.addModelAndView(mv);
				mv.setViewName("adminpage");
			} else {
				jsonObject = new JSONObject(response);
				mv.addObject("FIRSTNAME", firstname);
				mv.addObject("LASTNAME", lastname);
				mv.addObject("USERNAME", username);
				mv.addObject("PASSWORD", password);
				mv.addObject("RIGHTS", rights);
				mv.addObject("EMAIL", email);
				mv.addObject("ERROR", jsonObject.getString("ERROR"));
				mv.setViewName("addaccount");
			}
		} else {
			mv.setViewName("login");
		}

		return mv;
	}

	public ModelAndView registerError(String username, String password,
			String firstname, String lastname, String email, String rights)
			throws JSONException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("FIRSTNAME", firstname);
		mv.addObject("LASTNAME", lastname);
		mv.addObject("USERNAME", username);
		mv.addObject("PASSWORD", password);
		mv.addObject("RIGHTS", rights);
		mv.addObject("EMAIL", email);
		mv.addObject("ERROR", "Server is not responding...");
		mv.setViewName("addaccount");
		return mv;
	}

	@HystrixCommand(fallbackMethod = "updateError")
	public ModelAndView updateAccount(
			@RequestParam("username_input") String username,
			@RequestParam("password_input") String password,
			@RequestParam("firstname_input") String firstname,
			@RequestParam("lastname_input") String lastname,
			@RequestParam("email_input") String email,
			@RequestParam("rights_input") String rights) throws UniformInterfaceException, ClientHandlerException, JSONException {
		ModelAndView mv = new ModelAndView();
		Account account = new Account(username, password, email, rights, firstname, lastname);

		if(LoginSession.getInstance().getCurrentAccount().getRights().equals("admin")) {
			if( username.equals(LoginSession.getInstance().getCurrentAccount().getUsername())
					&& !rights.equals(LoginSession.getInstance().getCurrentAccount().getRights())) {
				JSONObject json = new JSONObject();
				json.put("ERROR", "Cannot change own role");
				mv.addObject("ERROR", json.getString("ERROR"));
				mv.addObject("firstname", firstname);
				mv.addObject("lastname", lastname);
				mv.addObject("username", username);
				mv.addObject("password", password);
				mv.addObject("rights", LoginSession.getInstance().getCurrentAccount().getRights());
				mv.addObject("email", email);
				mv.setViewName("updateaccount");
			} else {
				String url = "http://localhost:1997/crud/updateaccount";
				Client client = new Client();
				WebResource wr = client.resource(url);
				ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, account.toJson());
				String response = cr.getEntity(String.class);
				JSONObject jsonObject;

				if(200 == cr.getStatus()) {
					if(username.equals(LoginSession.getInstance().getCurrentAccount().getUsername())) {
						LoginSession.getInstance().setAccount(account);
					}
					mv.addObject("INFO", response);
					mv.setViewName("listaccounts");
				} else {
					jsonObject = new JSONObject(response);
					mv.addObject("firstname", firstname);
					mv.addObject("lastname", lastname);
					mv.addObject("username", username);
					mv.addObject("password", password);
					mv.addObject("rights", rights);
					mv.addObject("email", email);
					mv.addObject("ERROR", jsonObject.getString("ERROR"));
					mv.setViewName("updateaccount");
				}
			}
		} else {
			mv.setViewName("login");
		}

		return mv;
	}

	public ModelAndView updateError(
			@RequestParam("username_input") String username,
			@RequestParam("password_input") String password,
			@RequestParam("firstname_input") String firstname,
			@RequestParam("lastname_input") String lastname,
			@RequestParam("email_input") String email,
			@RequestParam("rights_input") String rights) throws UniformInterfaceException, ClientHandlerException, JSONException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("firstname", firstname);
		mv.addObject("lastname", lastname);
		mv.addObject("username", username);
		mv.addObject("password", password);
		mv.addObject("rights", rights);
		mv.addObject("email", email);
		mv.addObject("ERROR", "Server is not responding...");
		mv.setViewName("updateaccount");
		return mv;
	}

	@HystrixCommand(fallbackMethod = "deleteError")
	public ModelAndView deleteAccount(@RequestParam("username") String username) throws JSONException {
		ModelAndView mv = new ModelAndView();

		if( LoginSession.getInstance().getCurrentAccount().getRights().equals("admin") ) {

			if( !username.equals(LoginSession.getInstance().getCurrentAccount().getUsername()) ) {
				String url = "http://localhost:1997/crud/deleteaccount/" + username;
				Client client = new Client();
				WebResource wr = client.resource(url);
				ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
				String response = cr.getEntity(String.class);
				JSONObject json = new JSONObject(response);

				if(200 == cr.getStatus()) {
					mv.addObject("INFO", json.getString("INFO"));
					mv.setViewName("listaccounts");
				} else {
					Logger logger = Logger.getLogger(this.getClass());
					logger.info("Invalid Session");
					mv.setViewName("listaccounts");
				}
			} else {
				JSONObject json = new JSONObject();
				json.put("ERROR", "Cannot delete own account");
				mv.addObject("ERROR", json.getString("ERROR"));
				mv.setViewName("listaccounts");
			}


		} else {
			mv.setViewName("login");
		}

		return mv;
	}

	public ModelAndView deleteError(@RequestParam("username") String username) throws JSONException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("ERROR", "Server is not responding...");
		mv.setViewName("listaccounts");

		return mv;
	}

}
