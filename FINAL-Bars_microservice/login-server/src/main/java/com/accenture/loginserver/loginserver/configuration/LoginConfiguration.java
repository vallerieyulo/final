package com.accenture.loginserver.loginserver.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.accenture.loginserver.loginserver.service.AccountCrudService;
import com.accenture.loginserver.loginserver.service.LoginService;

@Configuration
public class LoginConfiguration extends ResourceConfig{

	public LoginConfiguration() {
		register(AccountCrudService.class);
		register(LoginService.class);
	}

}
