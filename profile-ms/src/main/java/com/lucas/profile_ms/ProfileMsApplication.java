package com.lucas.profile_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProfileMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileMsApplication.class, args);
	}

}
