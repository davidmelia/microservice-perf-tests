package com.example.demo;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

}
