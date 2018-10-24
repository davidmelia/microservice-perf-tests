package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = GatewayConfig.class)
public class TomcatGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomcatGatewayApplication.class, args);
	}
}
