package br.ufc.quixada.dao;

import java.sql.Connection;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
	private static Dotenv dotenv = Dotenv.load();

	private static final String DATABASE_URL = dotenv.get("DATABASE_URL");

	private static Connection connection;

	private DatabaseConnection() {
	}

	public Connection getConnection() {
		return null;
	}

	public void disconnect() {
	}

	public static DatabaseConnection getInstance() {
		return null;
	}

}
