package com.accenture.loginserver.loginserver.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.loginserver.loginserver.domain.Account;
import com.accenture.loginserver.loginserver.domain.AccountRepository;
import com.accenture.loginserver.loginserver.exception.LoginBarsException;

/**
 * Services for the login server
 *
 */
@Path("/crud")
public class AccountCrudService {

	@Autowired
	private AccountRepository accountRepository;

	// http://localhost:1997/crud/registeraccount
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registeraccount")
	public Response registerAccount(Account account) throws JSONException {

		JSONObject response = new JSONObject();

		if(accountRepository.findByUsername(account.getUsername()) != null) {
			response.put("ERROR", "Username Already Existing!");
			return Response.status(405).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();
		}

		try {
			if(isValidParameters(account)) {
				accountRepository.save(account);
				response.put("INFO", "Account Created!");
			}
		} catch (LoginBarsException e) {
			response.put("ERROR", e.getMessage());
			return Response.status(405).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();
		}

		return Response.status(200).entity(response.toString())
				.type(MediaType.APPLICATION_JSON).build();

	}

	// http://localhost:1997/crud/getaccounts
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getaccounts")
	public List<Account> getAccounts() {

		List<Account> accounts = accountRepository.findAll();

		return accounts;
	}

	// http://localhost:1997/crud/getaccount/username
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getaccount/{username}")
	public Response getAccount(@PathParam("username") String username) throws JSONException {

		Account account = accountRepository.findByUsername(username);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstName", account.getFirstName());
		jsonObject.put("lastName", account.getLastName());
		jsonObject.put("rights", account.getRights());
		jsonObject.put("email", account.getEmail());
		jsonObject.put("username", account.getUsername());
		jsonObject.put("pw", account.getPw());

		return Response.status(200).entity(jsonObject.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	// http://localhost:1997/crud/deleteaccount/username
	@DELETE
	@Path("/deleteaccount/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAccount(@PathParam("username") String username) throws JSONException {

		accountRepository.delete(accountRepository.findByUsername(username));
		JSONObject response = new JSONObject();
		response.put("INFO", "Successfully Deleted Account");

		return Response.status(200).type(MediaType.APPLICATION_JSON)
				.entity(response.toString()).build();

	}

	// http://localhost:1997/crud/updateaccount
	@PUT
	@Path("/updateaccount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(Account account) throws JSONException {
		JSONObject response = new JSONObject();

		if(accountRepository.findByUsername(account.getUsername()) != null) {

			try {
				if(isValidParameters(account)) {
					accountRepository.save(account);
				}
			} catch (LoginBarsException e) {
				response.put("ERROR", e.getMessage());
				return Response.status(405).entity(response.toString())
						.type(MediaType.APPLICATION_JSON).build();
			}

			response.put("INFO", "Successfully Updated Account!");
			return Response.status(200).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();

		} else {

			response.put("ERROR", "Account to update is not existing!");
			return Response.status(405).entity(response.toString())
					.type(MediaType.APPLICATION_JSON).build();
		}

	}

	public static boolean isValidParameters(Account account)
			throws LoginBarsException {

		final Pattern EMAIL_REGEX = Pattern
				.compile(
						"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
						Pattern.CASE_INSENSITIVE);

		if (account.getUsername().trim().isEmpty()) {
			throw new LoginBarsException(LoginBarsException.EMPTY_USERNAME);
		}
		if (account.getPw().trim().isEmpty()) {
			throw new LoginBarsException(LoginBarsException.EMPTY_PASSWORD);
		}
		if (account.getFirstName().trim().isEmpty()) {
			throw new LoginBarsException(LoginBarsException.EMPTY_FIRSTNAME);
		}
		if (account.getLastName().trim().isEmpty()) {
			throw new LoginBarsException(LoginBarsException.EMPTY_LASTNAME);
		}
		if (account.getEmail().trim().isEmpty()) {
			throw new LoginBarsException(LoginBarsException.EMPTY_EMAIL);
		}
		if (!EMAIL_REGEX.matcher(account.getEmail()).matches()) {
			throw new LoginBarsException(LoginBarsException.INVALID_EMAIL);
		}

		return true;
	}
	
	

}
