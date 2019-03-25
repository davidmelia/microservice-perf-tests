package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.netty.http.HttpResources;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

@SpringBootApplication
@EnableConfigurationProperties(value = GatewayConfig.class)
public class GatewayNettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayNettyApplication.class, args);
	}

	@Bean
	public WebClientCustomizer webClientCustomizer2() {
		return new WebClientCustomizer() {

			@Override
			public void customize(Builder webClientBuilder) {
				//ReactorResourceFactory
				//HttpClient hc = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave", 10000))).tcpConfiguration(tcpClient -> tcpClient.runOn(LoopResources.create("my-http", 32, true)));
				
				HttpClient hc = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave", 10000)));
				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
						hc);
				webClientBuilder.clientConnector(connector);
			}
		};
	}


}
