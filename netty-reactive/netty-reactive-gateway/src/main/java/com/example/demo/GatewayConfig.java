package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.example.gateway")
@lombok.Data
public class GatewayConfig {
	private String microserviceUrl1;
	private String microserviceUrl2;
}
