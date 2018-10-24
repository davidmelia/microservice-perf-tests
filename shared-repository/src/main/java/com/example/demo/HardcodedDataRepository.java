package com.example.demo;

import java.util.Collection;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HardcodedDataRepository implements DataRepository<Data, Collection<Data>> {

	private final Random random = new Random();

	public Data findOne(long delay) {
		if (-1l == delay) {
			delay = random.nextInt(1001);
		}
		try {
			if (delay > 0) {
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return HardcodedData.DATA;
	}

	public Collection<Data> findAll(long delay) {
		log.info("sync()");
		if (-1l == delay) {
			delay = random.nextInt(1001);
		}
		try {
			if (delay > 0) {
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Collection<Data> datas = HardcodedData.DATAS;

		log.info("sync returned {}", datas);
		return datas;
	}
}
