package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import io.netty.channel.ChannelOption;
import reactor.core.publisher.Mono;
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
				
//				HttpClient hc = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave", 10000)));
//				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
//						hc);
				HttpClient httpClient = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave", 10000)))
				        .tcpConfiguration(client ->
				                client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000));
				webClientBuilder.filter(new ExchangeFilterFunction() {
					
					@Override
					public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
						 return next.exchange(request);
					}
				});
				webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
			}
		};
	}


}
