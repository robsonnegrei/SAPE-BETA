package com.quixada.sme.sape.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class AppConfig {
	
	private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://sape_sql:3306/sape");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("12345");
	    logger.debug("Conexao : " + driverManagerDataSource.getUrl() + " : " + driverManagerDataSource.getUsername());
	    return driverManagerDataSource;
	}
	
	
}
