package com.accenture.loginserver.loginserver.service;

import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.loginserver.loginserver.domain.Account;
import com.accenture.loginserver.loginserver.domain.AccountRepository;

@Path("/loginservice")
public class LoginService {

	@Autowired
	AccountRepository accountRepository;

	// http://localhost:1997/loginservice/login
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginAccount(@HeaderParam("Authorization") String authString)
			throws JSONException {

		Account account;
		if ( (account = isAuthenticatedUser(authString)) != null) {
			return Response.status(200).type(MediaType.APPLICATION_JSON)
					.entity(account).build();
		} else {
			JSONObject response = new JSONObject();
			response.put("Error", "Invalid Username or Password");
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(response.toString()).build();
		}

	}

	private Account isAuthenticatedUser(String authString) {
		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[1];

		byte[] bytes = Base64.getDecoder().decode(authInfo);
		String decodedAuth = new String(bytes);
		String[] credentials = decodedAuth.split(":");
		String usernameFromInput = credentials[0];
		String passwordFromInput = credentials[1];

		Account account = accountRepository.findByUsername(usernameFromInput);

		if (account != null && passwordFromInput.equals(account.getPw())) {
			return account;
		} else {
			return null;
		}

	}

}
