package com.example.securityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
