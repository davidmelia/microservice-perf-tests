package com.example.demo;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveDataRepository implements DataRepository<Mono<Data>, Flux<Data>> {

	@Override
	public Mono<Data> findOne(long delayMillis) {
		return Mono.just(HardcodedData.DATA).delayElement(Duration.ofMillis(delayMillis));
	}

	@Override
	public Flux<Data> findAll(long delay) {
		log.warn("reactive()");

		Flux<Data> datas = Flux.fromIterable(HardcodedData.DATAS)
				.delaySubscription(Duration.ofMillis(Long.valueOf(delay)));

		log.warn("reactive returned {}", datas);
		return datas;
	}
}
