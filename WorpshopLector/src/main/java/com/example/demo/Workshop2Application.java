package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Workshop2Application {

	public static void main(String[] args) {
		SpringApplication.run(Workshop2Application.class, args);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl("http://localhost:8081/validador/").build();
	}

}
