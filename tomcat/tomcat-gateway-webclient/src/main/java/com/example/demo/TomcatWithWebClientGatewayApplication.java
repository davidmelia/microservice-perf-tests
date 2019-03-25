package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.HttpResources;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

@SpringBootApplication
@EnableConfigurationProperties(value = GatewayConfig.class)
public class TomcatWithWebClientGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomcatWithWebClientGatewayApplication.class, args);
	}
	
	//@Bean
	public WebClientCustomizer webClientCustomizer() {
		return new WebClientCustomizer() {

			@Override
			public void customize(Builder webClientBuilder) {
				//ReactorResourceFactory
				//HttpClient hc = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave",10000))).tcpConfiguration(tcpClient -> tcpClient.runOn(LoopResources.create("my-http", 32, true)));
//				TcpClient tcpClient = TcpClient.create()
//		                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // Connection Timeout
//		                .doOnConnected(connection ->
//		                        connection.addHandlerLast(new ReadTimeoutHandler(10)) // Read Timeout
//		                                  .addHandlerLast(new WriteTimeoutHandler(10))); // Write Timeout
//				HttpClient httpClient =HttpClient.from(tcpClient);
				
				
				HttpClient hc = HttpClient.create(HttpResources.set(ConnectionProvider.fixed("dave", 10000)));
				ReactorClientHttpConnector connector = new ReactorClientHttpConnector(
						hc);
				webClientBuilder.clientConnector(connector);
			}
		};
	}
	
	
}
