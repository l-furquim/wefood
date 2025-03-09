package com.lucas.profile_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProfileMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileMsApplication.class, args);
	}

}
