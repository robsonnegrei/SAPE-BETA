package com.quixada.sme.sape;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;




@SpringBootApplication
public class SapeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SapeApplication.class, args);		
	}
}
