package com.example.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class LoadTest {

	@Autowired
	private WebClient.Builder builder;
	
	private static int COUNT = 0;

	@RequiredArgsConstructor
	public class PerfCallable implements Callable<Data> {

		private final WebClient webClient;
		private final int count = ++COUNT;

		@Override
		public Data call() throws Exception {
			Data data = webClient.get().uri("/find-one?microserviceDelay=" + 100).retrieve().bodyToMono(Data.class)
					.block();

			
//			if (data != null) {
//				System.out.print("Y");
//			}
			if(count%200==0) {
				System.out.println("Processed " + count);	
			}
			return data;
		}


	}

	@Test
	public void test() throws Exception {
		WebClient webClient = builder.baseUrl("http://localhost:8090").build();
		ExecutorService executor = Executors.newFixedThreadPool(3000);
		System.out.println(new PerfCallable(webClient).call());
		StopWatch sw = new StopWatch();
		sw.start();
		for (int i = 0; i < 60000; i++) {
			Future<Data> data = executor.submit(new PerfCallable(webClient));

		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
		sw.stop();
		System.out.println("Took " + sw.getTotalTimeSeconds());
		System.out.println("request/sec " + 60000.0/sw.getTotalTimeSeconds());
		// System.out.println(data.get());
	}

}
