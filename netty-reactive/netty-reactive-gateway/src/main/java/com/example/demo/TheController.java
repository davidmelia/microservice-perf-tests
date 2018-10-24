package com.example.demo;

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
		this.webClient1 = builder.baseUrl(config.getMicroserviceUrl1()).build();
	}

	@GetMapping("/find-one")
	public Mono<Data> findOne(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
		log.info("sync()");
		Mono<Data> data = webClient1.get().uri("/find-one?microserviceDelay=" + delay).retrieve()
				.bodyToMono(Data.class);
//		Mono<String> data = webClient1.get().uri("?microserviceDelay=" + delay)
//				.accept(MediaType.APPLICATION_STREAM_JSON).retrieve().bodyToMono(String.class);
		log.info("sync returned {}", data);
		return data;
	}

	@GetMapping("/find-all")
	public Flux<Data> findAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
		log.info("reactive()");

		Flux<Data> data = webClient1.get().uri("/find-all?microserviceDelay=" + delay).retrieve()
				.bodyToFlux(Data.class);
//		Flux<Data> data = webClient1.get().uri("?microserviceDelay=" + delay).accept(MediaType.APPLICATION_STREAM_JSON)
//				.retrieve().bodyToFlux(Data.class);
		log.info("reactive returned {}", data);
		return data;
	}
}