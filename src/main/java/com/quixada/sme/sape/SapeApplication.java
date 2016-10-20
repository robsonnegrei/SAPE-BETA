package com.quixada.sme.sape;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class SapeApplication {
	private static Logger logger = LoggerFactory.getLogger(SapeApplication.class);
	public static void main(String[] args) {
		logger.debug("SAPE - Sistema de Acompanhamento Online de Projetos Educacionais");
		SpringApplication.run(SapeApplication.class, args);		
	}
}
