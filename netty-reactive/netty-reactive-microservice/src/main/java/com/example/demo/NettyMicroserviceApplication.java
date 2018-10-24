package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyMicroserviceApplication.class, args);
	}

//	@Bean
//	public ReactiveWebServerFactory reactiveWebServerFactory() {
//		NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
//		factory.addServerCustomizers(builder -> builder.loopResources(LoopResources.create("my-http", 32, true)));
//		return factory;
//	}
//
//	@Bean
//	public WebClientCustomizer webClientCustomizer() {
//		return new WebClientCustomizer() {
//
//			@Override
//			public void customize(Builder webClientBuilder) {
//				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
//						options -> options.poolResources(PoolResources.fixed("dave", 5000))
//								.loopResources(LoopResources.create("my-http", 32, true)));
//				webClientBuilder.clientConnector(connector);
//			}
//		};
//	}
}
