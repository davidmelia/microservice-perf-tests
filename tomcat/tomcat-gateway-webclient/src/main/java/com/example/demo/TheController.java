package com.example.demo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@CircuitBreaker(name = "my-microservice")
@RateLimiter(name = "my-microservice")
public class TheController {

  private final WebClient webClient1;

  public TheController(WebClient.Builder builder, GatewayConfig config) {
    this.webClient1 = builder.baseUrl(config.getMicroserviceUrl1()).build();
  }

  @GetMapping("/find-one")
  public Mono<Data> findOne(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("sync()");
    Mono<Data> data = webClient1.get().uri("/find-one?microserviceDelay=" + delay).retrieve().bodyToMono(Data.class);
    log.info("sync returned {}", data);
    return data;
  }

  @GetMapping("/find-all")
  public Flux<Data> findAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("reactive()");

    Flux<Data> data = webClient1.get().uri("/find-all?microserviceDelay=" + delay).retrieve().bodyToFlux(Data.class);
    log.info("reactive returned {}", data);
    return data;
  }

  @GetMapping("/find-one-with-exception")
  public Mono<Data> findOneWithException() throws BindException {
    log.info("sync()");
    Mono<Data> data = webClient1.get().uri("/find-on-with-exceptione").retrieve().onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse -> Mono.empty()).bodyToMono(Data.class);
    log.info("sync returned {}", data);
    return data;
  }


  @GetMapping("/find-one-and-all")
  public Mono<DataComposite> findOneAndAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("reactive()");

    Mono<Data[]> datas = webClient1.get().uri("/find-all?microserviceDelay=" + delay).retrieve().bodyToMono(Data[].class);

    Mono<DataComposite> data = this.findOne(0).zipWith(datas).map(t -> new DataComposite(t.getT1(), Arrays.asList(t.getT2()))).cast(DataComposite.class);

    log.info("reactive returned {}", data);
    return data;
  }

}
