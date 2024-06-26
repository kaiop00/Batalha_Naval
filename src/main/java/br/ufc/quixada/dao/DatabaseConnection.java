package br.ufc.quixada.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
	private Dotenv dotenv;

	private static String DATABASE_URL;

	private static DatabaseConnection instance;
	private static Connection connection;

	private DatabaseConnection() throws SQLException {
		dotenv = Dotenv.load();
		connect();
	}

	private DatabaseConnection(Dotenv dotenv) throws SQLException {
		this.dotenv = dotenv;
		connect();
	}

	private void connect() throws SQLException {
		DATABASE_URL = dotenv.get("DATABASE_URL");
		connection = DriverManager.getConnection(DATABASE_URL);
		System.out.println("Connected to the database.");
	}

	public Connection getConnection() {
		return connection;
	}

	public void disconnect() throws SQLException {
		connection.close();
	}

	public static DatabaseConnection getInstance() throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}

	public static DatabaseConnection getInstance(Dotenv dotenv) throws SQLException {
		if (instance == null) {
			instance = new DatabaseConnection(dotenv);
		}
		return instance;
	}

}
