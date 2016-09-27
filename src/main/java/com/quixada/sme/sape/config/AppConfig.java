package com.quixada.sme.sape.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

@Component
public class AppConfig {
	
	@Bean(name = "dataSource")
	public HikariDataSource dataSource() throws ClassNotFoundException {
		HikariConfig config = new HikariConfig();	
		//config.setDriverClass("com.mysql.jdbc.Driver");
		//config.addDataSourceProperty("dataSourceClassName", "com.mysql.jdbc.Driver");
		config.setInitializationFailFast(false); //Nao checa conexao ao iniciar ou buildar

	 	config.setJdbcUrl("jdbc:mysql://sape_sql:3306/sape");
		config.setUsername("root");
		config.setPassword("12345");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaximumPoolSize(40);
		config.setMinimumIdle(3);
	    HikariDataSource ds = new HikariDataSource(config);
		return ds; 	// fetch a connection
	}
	
	
}
