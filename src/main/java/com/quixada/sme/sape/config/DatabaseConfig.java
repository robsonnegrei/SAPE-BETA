package com.quixada.sme.sape.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {

	//DataSource - Sera alterado para arquivo externo posteriormente
	@Bean(name = "dataSource")
	public HikariDataSource dataSource() throws ClassNotFoundException {
		HikariConfig config = new HikariConfig();	
		//config.setDriverClass("com.mysql.jdbc.Driver");
		//config.addDataSourceProperty("dataSourceClassName", "com.mysql.jdbc.Driver");

	 	config.setJdbcUrl("jdbc:mysql://sape_sql:3306/sape");
		config.setUsername("root");
		config.setPassword("12345");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMaximumPoolSize(40);
		config.setMinimumIdle(3);
		config.setInitializationFailFast(false); //Nao checa conexao ao iniciar ou buildar
	    HikariDataSource ds = new HikariDataSource(config);
		return ds; 	// fetch a connection
	}
	
	
}
