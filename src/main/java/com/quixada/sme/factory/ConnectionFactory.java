package com.quixada.sme.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String BANCO_DE_DADOS_SENHA = "";
	private static final String BANCO_DE_DADOS_USUARIO = "root";
	private static final String BANCO_DE_DADOS_URL = "jdbc:mysql://localhost/sape";
	private static Connection mySqlConnection;
	
	public static Connection getMySqlConnection(){
		if (mySqlConnection != null) {
			return mySqlConnection;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mySqlConnection  = DriverManager.getConnection(BANCO_DE_DADOS_URL,
					BANCO_DE_DADOS_USUARIO, BANCO_DE_DADOS_SENHA);
			return mySqlConnection;
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
}
