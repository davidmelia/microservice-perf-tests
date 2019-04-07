package com.example.demo;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class TheController {
  private final DataRepository<Data, Collection<Data>> repository = new HardcodedDataRepository();

  @GetMapping("/find-one")
  public Data findOne(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("findOne()");
    Data data = repository.findOne(delay);
    log.info("findOne returned {}", data);
    return data;
  }

  @GetMapping("/find-all")
  public Collection<Data> findAll(@RequestParam(value = "microserviceDelay", defaultValue = "0") long delay) {
    log.info("findAll()");
    Collection<Data> data = repository.findAll(delay);
    log.info("findAll returned {}", data);
    return data;
  }

  @GetMapping("/find-one-with-exception")
  public Data findOneWithException() throws BindException {
    log.info("findOne()");
    BindException bindException = new BindException(Map.of("email", "dave"), "createProfile");
    bindException.addError(new FieldError("updateEmail", "email", null, false, new String[] {"Duplicate"}, null, null));
    log.info("findOneWithException returned {}", bindException);
    if (bindException != null) {
      throw bindException;
    }
    return null;
  }


}
