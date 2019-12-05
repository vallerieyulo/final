package com.accenture.bars.circuitbreaker;

import java.io.File;
import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.bars.controller.BarsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RibbonClient(name = "bars-server", configuration = BarsConfiguration.class)
@Service
public class BarsHystrix {

	@Autowired
	private LoadBalancerClient loadBalancer;

	@HystrixCommand(fallbackMethod = "reliable")
	public ModelAndView execute(File file, String role) throws JSONException {

        ServiceInstance instance = loadBalancer.choose("bars-server");
        URI uri = URI.create(String.format("http://%s:%s/bars/execute?file=%s", instance.getHost(), instance.getPort(), file.getPath().replace("\\", "/")));

		ModelAndView mv = new ModelAndView();
		Client client = new Client();
		WebResource wr = client.resource(uri);
		ClientResponse cr = wr.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		String response = cr.getEntity(String.class);
		JSONObject json;

		String page = "";

		if (200 == cr.getStatus()) {
			if("admin".equals(role)) {
				mv.addObject("urlbars", String.format("http://%s:%s/bars/getrecords", instance.getHost(), instance.getPort()));
				page = "successadmin";
			} else if ("user".equals(role)) {
				mv.addObject("urlbars", String.format("http://%s:%s/bars/getrecords", instance.getHost(), instance.getPort()));
				page = "successnonadmin";
			}
			mv.addObject("rights", role);
		} else {
			json = new JSONObject(response);
			mv.addObject("ERROR", json.get("ERROR").toString());
			if("admin".equals(role)) {
				page = "barsadmin";
			} else if ("user".equals(role)) {
				page = "barsnonadmin";
			}
		}

		mv.setViewName(page);
		return mv;
	}

	public ModelAndView reliable(File file, String role) {
		ModelAndView mv = new ModelAndView();
		if("admin".equals(role)) {
			mv.setViewName("barsadmin");
		} else if ("user".equals(role))  {
			mv.setViewName("barsnonadmin");
		} else {
			mv.setViewName("barsadmin");
		}
		mv.addObject("ERROR", "Server not responding...");
		mv.addObject("rights", role);
		return mv;
	}

}
