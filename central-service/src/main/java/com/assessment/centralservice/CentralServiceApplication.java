package com.assessment.centralservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Central Service APIs", version = "1.0", description = "Houses endpoints to post warehouse measurements"), servers = {
		@Server(url = "${server.servlet.context-path}", description = "Default Server URL") })
public class CentralServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralServiceApplication.class, args);
	}
}
