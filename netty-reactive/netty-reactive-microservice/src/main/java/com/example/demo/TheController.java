package com.example.demo;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class TheController {
  private final DataRepository<Mono<Data>, Flux<Data>> repository = new ReactiveDataRepository();

  @GetMapping("/find-one")
  public Mono<Data> findOne(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("sync()");
    Mono<Data> data = repository.findOne(delay);
    log.info("sync returned {}", data);
    return data;
  }

  @GetMapping("/find-all")
  public Flux<Data> findAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("reactive()");

    Flux<Data> data = repository.findAll(delay);

    log.info("reactive returned {}", data);
    return data;
  }

  @GetMapping("/find-one-with-exception")
  public Mono<Data> findOneWithException() throws BindException {
    BindException bindException = new BindException(Map.of("email", "dave"), "createProfile");
    bindException.addError(new FieldError("updateEmail", "email", null, false, new String[] {"Duplicate"}, null, null));
    log.info("findOneWithException returned {}", bindException);
    if (bindException != null) {
      throw bindException;
    }
    return Mono.empty();
  }
}
