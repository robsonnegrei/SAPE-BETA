package com.quixada.sme.sape.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jolbox.bonecp.BoneCPDataSource;

@Component
public class AppConfig {
	
	private static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	
	@Bean(name = "dataSource")
	public BoneCPDataSource dataSource() throws ClassNotFoundException {
		BoneCPDataSource ds = new BoneCPDataSource();	// create a new configuration object
		ds.setDriverClass("com.mysql.jdbc.Driver");
	 	ds.setJdbcUrl("jdbc:mysql://localhost:3306/sape");	// set the JDBC url
		ds.setUsername("root");							// set the username
		ds.setPassword("12345");	
		ds.setIdleConnectionTestPeriod(60);
	    ds.setIdleMaxAge(240);
	    ds.setMaxConnectionsPerPartition(20);
	    ds.setMinConnectionsPerPartition(5);
	    ds.setPartitionCount(3);
	    ds.setAcquireIncrement(5);
	    ds.setStatementCacheSize(100);
	    ds.setReleaseHelperThreads(2);
		//logger.debug("Conexao obtida: " + ds.getJdbcUrl() + " : " + ds.getUser());
		return ds; 	// fetch a connection
	}
	
	
}
