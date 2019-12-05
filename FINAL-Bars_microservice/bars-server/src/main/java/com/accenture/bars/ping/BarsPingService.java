package com.accenture.bars.ping;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

@Path(value = "/")
public class BarsPingService {

	Logger log = Logger.getLogger(this.getClass());

	@GET
	@RequestMapping(value = "/")
	public String home() {
		log.info("Access /");
		return "Hi!";
	}
}
