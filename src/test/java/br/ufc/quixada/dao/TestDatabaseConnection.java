package br.ufc.quixada.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

public class TestDatabaseConnection {

    @Test
    @SetEnvironmentVariable(key = "DATABASE_URL", value = "jdbc:sqlite:test_database.db")
    void testGetInstanceWithValidDatabaseURL() {
        DatabaseConnection databaseConnection = null;
        assertDoesNotThrow(() -> DatabaseConnection.getInstance(),
                "Getting instance should not throw an Exception with valid database URL");
        assertNotNull(databaseConnection, "DatabaseConnection instance should not be null");
    }

    @Test
    @SetEnvironmentVariable(key = "DATABASE_URL", value = "invalid_database_url")
    void testGetInstanceWithInvalidDatabaseURL() {
        assertThrows(SQLException.class, () -> DatabaseConnection.getInstance(),
                "Getting instance should throw an Exception with a invalid database URL");
    }

    @Test
    @SetEnvironmentVariable(key = "DATABASE_URL", value = "jdbc:sqlite:test_database.db")
    void testGetValidConnection() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        assertNotNull(databaseConnection);
        assertNotNull(databaseConnection.getConnection(), "Connection should not be null");
    }

    @Test
    @SetEnvironmentVariable(key = "DATABASE_URL", value = "jdbc:sqlite:test_database.db")
    void testDisconnectValidConnection() throws SQLException {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        assertNotNull(databaseConnection);
        assertDoesNotThrow(() -> databaseConnection.disconnect(),
                "Disconnecting should not throw an Exception");
        assertDoesNotThrow(() -> databaseConnection.getConnection().isClosed());
        assertTrue(databaseConnection.getConnection().isClosed(), "Connection should be closed");
    }

    @Test
    void testGetInstanceReturnSameObjectOnRepeatedCall() {
        DatabaseConnection databaseConnection1 = DatabaseConnection.getInstance();
        DatabaseConnection databaseConnection2 = DatabaseConnection.getInstance();

        assertNotNull(databaseConnection1);
        assertNotNull(databaseConnection2);
        assertSame(databaseConnection1, databaseConnection2, "Both instances should be the same");
    }
}
