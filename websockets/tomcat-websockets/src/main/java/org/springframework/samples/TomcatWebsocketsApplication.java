package org.springframework.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TomcatWebsocketsApplication {

	public static void main(String[] args) {
		SuperFudgeHelper.enhanceWebsocketMessageSelectorSpelContext();
		SpringApplication.run(TomcatWebsocketsApplication.class, args);

	}
}
