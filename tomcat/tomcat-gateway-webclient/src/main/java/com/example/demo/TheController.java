package com.example.demo;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TheController {

	private final WebClient webClient1;

	public TheController(WebClient.Builder builder, GatewayConfig config) {
//		TcpClient tcpClient = TcpClient.create(ConnectionProvider.elastic("dave"))
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // Connection Timeout
//                .doOnConnected(connection ->
//                        connection.addHandlerLast(new ReadTimeoutHandler(10)) // Read Timeout
//                                  .addHandlerLast(new WriteTimeoutHandler(10))); // Write Timeout
//		HttpClient httpClient =HttpClient.from(tcpClient);

		this.webClient1 = builder.baseUrl(config.getMicroserviceUrl1()).build();
	}

	@GetMapping("/find-one")
	public Mono<Data> findOne(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
		log.info("sync()");
		Mono<Data> data = webClient1.get().uri("/find-one?microserviceDelay=" + delay).retrieve()
				.bodyToMono(Data.class);
		log.info("sync returned {}", data);
		return data;
	}

	@GetMapping("/find-all")
	public Flux<Data> findAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
		log.info("reactive()");

		Flux<Data> data = webClient1.get().uri("/find-all?microserviceDelay=" + delay).retrieve()
				.bodyToFlux(Data.class);
		log.info("reactive returned {}", data);
		return data;
	}
	
	@GetMapping("/find-one-and-all")
	public Mono<DataComposite> findOneAndAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
		log.info("reactive()");
		
		Mono<Data[]> datas = webClient1.get().uri("/find-all?microserviceDelay=" + delay).retrieve()
				.bodyToMono(Data[].class);

		Mono<DataComposite> data = this.findOne(0).zipWith(datas).map(t -> new DataComposite(t.getT1(), Arrays.asList(t.getT2()))).cast(DataComposite.class);
		
		log.info("reactive returned {}", data);
		return data;
	}
	
}