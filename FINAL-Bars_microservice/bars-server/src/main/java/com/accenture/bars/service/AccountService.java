package com.accenture.bars.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.bars.domain.Account;
import com.accenture.bars.repository.AccountRepository;

@Path("account")
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(Account account) {

		JSONObject json = new JSONObject();

		account.setDateCreated(new Timestamp(new Date().getTime()));

		accountRepository.save(account);
		json.put("INFO", "CREATED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOne() {
		List<Account> accountList = accountRepository.findAllByIsActive("Y");

		return Response.status(200).entity(accountList).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("retrieve")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccountRecord() {
	
		List<Object[]> accountlist = accountRepository.findAccounts();
		//List<Account> accountList = accountRepository.findAllByIsActive("Y");

		return Response.status(200).entity(accountlist).type(MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("retrieveinactiveacct")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAcctInactive() {
		List<Account> accountList = accountRepository.findAllByIsActive("N");

		return Response.status(200).entity(accountList).type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(@PathParam("id") long id, Account account) {

		if (accountRepository.findOne(id) != null) {
		Account acct = accountRepository.findOne(id);
		acct.setAccountName(account.getAccountName());
		acct.setIsActive(account.getIsActive());
		acct.setLastEdited(account.getLastEdited());		
		accountRepository.save(acct);
		}
		JSONObject json = new JSONObject();
		json.put("INFO:", "Updated!");

		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAccount(@PathParam("id") long id) {

		Account account = accountRepository.findOne(id);
		account.getCustomer().getAccounts().remove(account);
	
		accountRepository.delete(id);
	
		JSONObject json = new JSONObject();
		json.put("INFO", "DELETED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	}
}
