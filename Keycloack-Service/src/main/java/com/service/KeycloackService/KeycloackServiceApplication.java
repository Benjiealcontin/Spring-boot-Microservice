package com.service.KeycloackService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KeycloackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloackServiceApplication.class, args);
	}

}
