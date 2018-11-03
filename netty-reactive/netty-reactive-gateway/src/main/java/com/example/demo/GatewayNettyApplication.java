package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = GatewayConfig.class)
public class GatewayNettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayNettyApplication.class, args);
	}

//	@Bean
//	public WebClientCustomizer webClientCustomizer() {
//		return new WebClientCustomizer() {
//
//			@Override
//			public void customize(Builder webClientBuilder) {
//
//				HttpResources httpResources = HttpResources.set(ConnectionProvider.fixed("dave", 10000));
//
//				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
//						HttpClient.create(httpResources).keepAlive(false));
//				webClientBuilder.clientConnector(connector);
//			}
//		};
//	}

//	@Bean
//	public WebClientCustomizer webClientCustomizer() {
//		return new WebClientCustomizer() {
//
//			@Override
//			public void customize(Builder webClientBuilder) {
//				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
//						options -> options.poolResources(PoolResources.fixed("dave", 10000))
//								.loopResources(LoopResources.create("my-http", 32, true)));
//				webClientBuilder.clientConnector(connector);
//			}
//		};
//	}
}
