package com.example.demo;

import java.util.Collection;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HardcodedDataRepository implements DataRepository<Data, Collection<Data>> {

	private final Random random = new Random();

	@Override
	public Data findOne(long delay) {
		log.info("findOne()");
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
		log.info("findOne returned {}", HardcodedData.DATA);
		return HardcodedData.DATA;
	}

	@Override
	public Collection<Data> findAll(long delay) {
		log.info("findAll()");
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

		log.info("findAll returned {}", datas);
		return datas;
	}
}
