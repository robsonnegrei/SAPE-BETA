package com.quixada.sme.sape;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.access.SecurityConfig;

@Configuration
@Import({ WebSecurityConfig.class })
public class AppConfig {
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/sape");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("12345");
	    return driverManagerDataSource;
	}
	
}
