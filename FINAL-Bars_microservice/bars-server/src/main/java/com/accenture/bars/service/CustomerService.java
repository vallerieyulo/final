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
import com.accenture.bars.domain.Customer;
import com.accenture.bars.repository.AccountRepository;
import com.accenture.bars.repository.CustomerRepository;

@Path("customer")
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer) {

		JSONObject json = new JSONObject();

		customer.setDateCreated(new Timestamp(new Date().getTime()));
		customer.setStatus("Y");
		
		customerRepository.save(customer);
		json.put("INFO", "CREATED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Path("retrieve")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerRecord() {
		List<Customer> customerList = customerRepository.findAllByStatus("Y");

		return Response.status(200).entity(customerList).type(MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("retrieveinactive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustInactive() {
		List<Customer> customerList = customerRepository.findAllByStatus("N");

		return Response.status(200).entity(customerList).type(MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOne(@PathParam("id") long id) {
		Customer customer = customerRepository.findOne(id);

		return Response.status(200).entity(customer).type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@PathParam("id") long id, Customer customer) {
		JSONObject json = new JSONObject();

		Customer cust = customerRepository.findOne(id);
		cust.setFirstName(customer.getFirstName());
		cust.setLastName(customer.getLastName());
		cust.setAddress(customer.getAddress());
		cust.setStatus(customer.getStatus());
		cust.setLastEdited(customer.getLastEdited());

		customerRepository.save(cust);
		if (cust.getStatus().equals("N")) {
			accountRepository.updateIsActive(id);
		}
		
		json.put("INFO:", "Updated!");

		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();

	}

	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRecord(@PathParam("id") long id) {

		customerRepository.delete(id);

		JSONObject json = new JSONObject();
		json.put("INFO", "DELETED!!");
		return Response.status(200).entity(json.toString()).type(MediaType.APPLICATION_JSON).build();
	}

}
