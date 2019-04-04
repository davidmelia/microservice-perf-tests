package com.example.demo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class TheController {

  private final RestTemplate restTemplate;
  private final GatewayConfig config;

  public TheController(GatewayConfig config, RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
    this.config = config;
  }

  @CircuitBreaker(name = "my-microservice")
  @RateLimiter(name = "my-microservice")
  @GetMapping("/find-one")
  public Data findOne(@RequestParam(value = "microserviceDelay") long microserviceDelay) {
    log.info("findOne()");
    Data data = restTemplate.getForObject(config.getMicroserviceUrl1() + "/find-one?microserviceDelay=" + microserviceDelay, Data.class);
    log.info("get2 returned {}", data);
    return data;
  }

  @CircuitBreaker(name = "my-microservice")
  @RateLimiter(name = "my-microservice")
  @GetMapping("/find-all")
  public Data[] findAll(@RequestParam(value = "microserviceDelay") long microserviceDelay) {
    log.info("findAll()");
    Data[] data = restTemplate.getForObject(config.getMicroserviceUrl1() + "/find-all?microserviceDelay=" + microserviceDelay, Data[].class);
    log.info("get1 returned {}", data);
    return data;
  }

  @GetMapping("/find-one-and-all")
  public DataComposite findOneAndAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("reactive()");

    Data[] datas = findAll(delay);

    DataComposite data = new DataComposite(findOne(0), Arrays.asList(datas));

    log.info("reactive returned {}", data);
    return data;
  }
}
