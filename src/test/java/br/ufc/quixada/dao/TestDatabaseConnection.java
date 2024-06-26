package br.ufc.quixada.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.cdimascio.dotenv.Dotenv;

public class TestDatabaseConnection {
    private static Dotenv dotenv;

    /**
     * creating a mock Dotenv object to be injected into the DatabaseConnection
     * getInstance method
     */
    @BeforeAll
    public static void setUpAll() {
        dotenv = mock(Dotenv.class);
        when(dotenv.get("DATABASE_URL")).thenReturn("jdbc:sqlite::memory:");
    }

    /**
     * resetting the instance field to null on each test, using reflection
     * 
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        // resetting the instance field to null on each test
        setPrivateField(DatabaseConnection.class, DatabaseConnection.getInstance(dotenv), "instance", null);
    }

    @Test
    void testGetInstanceWithValidDatabaseURL() throws Exception {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(dotenv);

        assertDoesNotThrow(() -> DatabaseConnection.getInstance(dotenv),
                "Getting instance should not throw an Exception with valid database URL");
        assertNotNull(databaseConnection, "DatabaseConnection instance should not be null");
    }

    @Test
    void testGetInstanceWithInvalidDatabaseURL() throws Exception {
        Dotenv invalidDotenv = mock(Dotenv.class);
        when(invalidDotenv.get("DATABASE_URL")).thenReturn("invalid_url");

        assertThrows(SQLException.class, () -> DatabaseConnection.getInstance(invalidDotenv),
                "Getting instance should throw an Exception with a invalid database URL");

    }

    @Test
    void testGetValidConnection() throws Exception {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(dotenv);

        assertNotNull(databaseConnection);
        assertNotNull(databaseConnection.getConnection(), "Connection should not be null");
    }

    @Test
    void testDisconnectValidConnection() throws Exception {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(dotenv);

        assertNotNull(databaseConnection);
        assertDoesNotThrow(() -> databaseConnection.disconnect(),
                "Disconnecting should not throw an Exception");
        assertDoesNotThrow(() -> databaseConnection.getConnection().isClosed());
        assertTrue(databaseConnection.getConnection().isClosed(), "Connection should be closed");
    }

    @Test
    void testGetInstanceReturnSameObjectOnRepeatedCall() throws Exception {
        DatabaseConnection databaseConnection1 = DatabaseConnection.getInstance(dotenv);
        DatabaseConnection databaseConnection2 = DatabaseConnection.getInstance(dotenv);

        assertNotNull(databaseConnection1);
        assertNotNull(databaseConnection2);
        assertSame(databaseConnection1, databaseConnection2, "Both instances should be the same");
    }

    /**
     * Reflection method to set a private field of a class to a new value.
     * Reference:
     * https://stackoverflow.com/questions/54819923/how-to-destroy-or-reset-singleton-instance-in-java
     * 
     * @param clazz class to set the field
     * @param inst  instance of the class
     * @param field
     * @param value
     * @throws Exception
     */
    public static void setPrivateField(Class<DatabaseConnection> clazz, Object inst, String field, Object value)
            throws Exception {
        java.lang.reflect.Field f = clazz.getDeclaredField(field);
        f.setAccessible(true);
        f.set(inst, value);
        f.setAccessible(false);
    }

}
