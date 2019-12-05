package com.accenture.bars.service;

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

import com.accenture.bars.domain.Billing;
import com.accenture.bars.repository.BillingRepository;

@Path("billing")
public class BillingService {

	
	@Autowired
	private BillingRepository billingRepository;

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBilling(Billing billing) {

		JSONObject json = new JSONObject();
		billingRepository.save(billing);
		json.put("INFO", "CREATED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Path("retrieve")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBillingRecord() {
		List<Object[]> billingList = billingRepository.findBillings();

		return Response.status(200).entity(billingList).type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBilling(@PathParam("id") long id, Billing billing) {

		Billing bill = billingRepository.findOne(id);

		bill.setAmount(billing.getAmount());
		bill.setBillingCycle(billing.getBillingCycle());
		bill.setBillingMonth(billing.getBillingMonth());
		bill.setEndDate(billing.getEndDate());
		bill.setLastEdited(billing.getLastEdited());
		bill.setStartDate(billing.getStartDate());
				
		billingRepository.save(bill);
		JSONObject json = new JSONObject();
		json.put("INFO:", "Updated!");

		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRecord(@PathParam("id") long id) {

		billingRepository.delete(id);

		JSONObject json = new JSONObject();
		json.put("INFO", "DELETED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	}
	
	
	
}
