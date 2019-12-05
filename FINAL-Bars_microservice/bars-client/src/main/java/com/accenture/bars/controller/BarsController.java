package com.accenture.bars.controller;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.bars.circuitbreaker.BarsHystrix;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * BarsController Class
 *
 * @author christian.p.c.mirano
 * @since 17.10.18
 */
/**
 * BarsController - controller of the Bars Web App
 *
 */

@EnableCircuitBreaker
@Controller
public class BarsController {

	private static final Logger log = LoggerFactory.getLogger(BarsController.class);

	@Autowired
	BarsHystrix barsHystrix;
	String role;
	String user;

	@RequestMapping(value = "/bars")
	public String roleFromAccount(@RequestParam(value = "rights", required = false) String role,
								  @RequestParam(value = "user", required = false) String user, Model model) {
		String page = "";

		if ("admin".equals(role)) {
			page = "barsadmin";
		} else if ("user".equals(role)) {
			page = "barsnonadmin";
		}
		this.role = role;
		model.addAttribute("rights", role);

		this.user = user;
		model.addAttribute("user", user);

		return page;
	}

	@RequestMapping(value = "/")
	public String goToBarsHome() {
		String page = "";
		if ("admin".equals(role)) {
			page = "barsadmin";
		} else if ("user".equals(role)) {
			page = "barsnonadmin";
		}
		return page;
	}

	@RequestMapping(value = "/process")
	public ModelAndView processRequest(@RequestParam("files") File file) throws JSONException {

		File filepath = new File("C:\\BARS\\" + file.getName());
		return barsHystrix.execute(filepath, role);
	}

	/* CUSTOMER METHODS */

	@RequestMapping(value = "/customer")
	public String customer() {
		log.info(user);
		return "customer";
	}
	
	@RequestMapping(value = "getcustomer")
	@ResponseBody
	public String custPageGet() {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/customer/retrieve");
		ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String output = cr.getEntity(String.class);
		return output;
	}
	
	@RequestMapping(value = "/createcustomer")
	@ResponseBody
	public String addcustomer(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("firstName", request.getParameter("firstname_input"));
		json.put("lastName", request.getParameter("lastname_input"));
		json.put("address", request.getParameter("address_input"));
		json.put("status", request.getParameter("status_input"));
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/customer/create");
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("CUSTOMER CREATED!", output);
		int status = resp.getStatus();

		if (status == 200) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/customer/delete/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		String output = resp.getEntity(String.class);
		log.info("CUSTOMER DELETED!", output);
		int status = resp.getStatus();
		if (status == 200) {
			return "redirect:/customer";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping("/update/{id}")
	@ResponseBody
	public String updateCustomer(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") Long id) {
		JSONObject json = new JSONObject();

		json.put("firstName", req.getParameter("firstname"));
		json.put("lastName", req.getParameter("lastname"));
		json.put("address", req.getParameter("address"));
		json.put("status", req.getParameter("status"));
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/customer/update/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("CUSTOMER UPDATED!", output);

		int stat = resp.getStatus();
		if (stat == 200) {
			return "success";
		} else {
			return "error";
		}
	}



	/* ACCOUNT METHODS */

	@RequestMapping(value = "/accountpage")
	public String account() {
		return "accountpage";
	}

	@RequestMapping(value = "getaccount")
	@ResponseBody
	public String accountPageGet() {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/account/retrieve");
		ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String output = cr.getEntity(String.class);
		return output;
	}

	@RequestMapping(value = "/createaccount")
	@ResponseBody
	public String addAccount(HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = new JSONObject();
		JSONObject customer = new JSONObject(request.getParameter("customer"));

		json.put("customer", customer);
		json.put("accountName", request.getParameter("accountname_input"));
		json.put("isActive", request.getParameter("isactive_input"));
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/account/create");
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("ACCOUNT CREATED!", output);
		int status = resp.getStatus();
		if (status == 200) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping("/deleteacct/{id}")
	public String deleteAccount(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/account/delete/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		String output = resp.getEntity(String.class);
		log.info("ACCOUNT DELETED!", output);
		int status = resp.getStatus();
		if (status == 200) {
			return "redirect:/accountpage";
		} else {
			return "redirect:/";
		}
	}
	@RequestMapping("/updateaccount/{id}")
	@ResponseBody
	public String updateAccount(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") long id) {
		JSONObject json = new JSONObject();

		json.put("accountName", req.getParameter("accountname"));
		json.put("isActive", req.getParameter("isactive"));
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/account/update/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("ACCOUNT UPDATED!", output);
		int stat = resp.getStatus();
		if (stat == 200) {
			return "success";
		} else {
			return "error";
		}
	}

	
	
	/* BILLING METHODS */
	
	@RequestMapping(value = "/billingpage")
	public String billing() {
		return "billingpage";
	}
	@RequestMapping(value = "/createbilling")
	@ResponseBody
	public String addbilling(HttpServletRequest request, HttpServletResponse response) {

		String date = request.getParameter("startdate_input");
		int billingCycle = Integer.parseInt(request.getParameter("billingcycle_input"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(date, formatter);
		ZoneId z = ZoneId.systemDefault();
		LocalDate now = LocalDate.now(z);

		if (startDate.getMonthValue() != billingCycle) {
			return "START_DATE_NOT_ON_RANGE";
		}
		if (startDate.isBefore(now)) {
			return "START_DATE_PASSED";
		}
		
		LocalDate endDate = startDate.plusDays(30);
		JSONObject json = new JSONObject();
		JSONObject account = new JSONObject(request.getParameter("account"));

		json.put("billingCycle", billingCycle);
		json.put("billingMonth", request.getParameter("billingmonth_input"));
		json.put("amount", request.getParameter("amount_input"));
		json.put("startDate", Date.valueOf(startDate));
		json.put("endDate", Date.valueOf(endDate));
		json.put("account", account);
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/billing/create");
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("BILLING CREATED!", output);
		int status = resp.getStatus();
		if (status == 200) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping("/updatebilling/{id}")
	@ResponseBody
	public String updateBill(HttpServletRequest req, HttpServletResponse res, @PathVariable("id") long id) {
		JSONObject json = new JSONObject();

		String date = req.getParameter("startdate_input");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(date, formatter);
		LocalDate endDate = startDate.plusDays(30);

		json.put("billingCycle", req.getParameter("billingcycle_input"));
		json.put("billingMonth", req.getParameter("billingmonth_input"));
		json.put("amount", req.getParameter("amount_input"));
		json.put("startDate", Date.valueOf(startDate));
		json.put("endDate", Date.valueOf(endDate));
		json.put("isActive", req.getParameter("isactive_input"));
		json.put("lastEdited", user);

		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/billing/update/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json.toString());
		String output = resp.getEntity(String.class);
		log.info("BILLING UPDATED!", output);
		int stat = resp.getStatus();
		if (stat == 200) {
			return "success";
		} else {
			return "error";
		}
	}

	@RequestMapping("/deletebill/{id}")
	public String deleteBilling(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/billing/delete/" + id);
		ClientResponse resp = wr.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		String output = resp.getEntity(String.class);
		log.info("BILLING DELETED!", output);
		int status = resp.getStatus();
		if (status == 200) {
			return "redirect:/billingpage";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/getbilling")
	@ResponseBody
	public String billingPageGet() {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/billing/retrieve");
		ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String output = cr.getEntity(String.class);
		return output;
	}

	@RequestMapping(value = "/getAccountList")
	@ResponseBody
	public String getAccountList() {
		Client client = new Client();
		WebResource wr = client.resource("http://localhost:1995/account/get");
		ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String output = cr.getEntity(String.class);
		return output;
	}

}
/*
 * @RequestMapping(value = "/customerupdate") public ModelAndView
 * customerupdate(@RequestParam("id") String id) throws JsonParseException,
 * JsonMappingException, IOException { ModelAndView mv = new ModelAndView();
 * 
 * Client client = new Client(); WebResource wr =
 * client.resource("http://localhost:1995/customer/get/" + id); ClientResponse
 * resp = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class); String
 * output = resp.getEntity(String.class); System.out.println(output); JSONObject
 * json = new JSONObject(output); mv.addObject("customerId",
 * json.get("customerId")); mv.addObject("firstName", json.get("firstName"));
 * mv.addObject("lastName", json.get("lastName")); mv.addObject("status",
 * json.get("status")); mv.addObject("address", json.get("address"));
 * mv.setViewName("customerupdate"); return mv; }
 */