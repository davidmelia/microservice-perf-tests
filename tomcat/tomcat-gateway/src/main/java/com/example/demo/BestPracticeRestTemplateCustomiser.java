package com.example.demo;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BestPracticeRestTemplateCustomiser implements RestTemplateCustomizer {

	private final int maxConnectionsPerRoute;
	private final int maxConnectionsTotal;

	public BestPracticeRestTemplateCustomiser() {
		this.maxConnectionsPerRoute = 200;
		this.maxConnectionsTotal = 200;
	}

	public BestPracticeRestTemplateCustomiser(int maxConnectionsPerRoute, int maxConnectionsTotal) {
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
		this.maxConnectionsTotal = maxConnectionsTotal;
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		HttpComponentsClientHttpRequestFactory requestFactory = requestFactory();
		restTemplate.setRequestFactory(requestFactory);
	}

	public HttpComponentsClientHttpRequestFactory requestFactory() {
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(maxConnectionsPerRoute)
				.setMaxConnTotal(maxConnectionsTotal).build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

}
