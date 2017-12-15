package com.ibmcloud.kickster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RestApiService {

	public static void main(String[] args) {
		SpringApplication.run(RestApiService.class, args);
	}
}