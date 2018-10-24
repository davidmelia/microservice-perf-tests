package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import reactor.ipc.netty.resources.LoopResources;
import reactor.ipc.netty.resources.PoolResources;

@SpringBootApplication
@EnableConfigurationProperties(value = GatewayConfig.class)
public class GatewayNettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayNettyApplication.class, args);
	}

//	@Bean
//	public ReactiveWebServerFactory reactiveWebServerFactory() {
//		NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
//		factory.addServerCustomizers(builder -> builder.loopResources(LoopResources.create("my-http", 32, true)));
//		return factory;
//	}
//
	@Bean
	public WebClientCustomizer webClientCustomizer() {
		return new WebClientCustomizer() {

			@Override
			public void customize(Builder webClientBuilder) {
				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
						options -> options.poolResources(PoolResources.fixed("dave", 10000))
								.loopResources(LoopResources.create("my-http", 32, true)));
				webClientBuilder.clientConnector(connector);
			}
		};
	}
}
