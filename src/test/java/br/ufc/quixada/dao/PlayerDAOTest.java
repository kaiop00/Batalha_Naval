package br.ufc.quixada.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufc.quixada.model.Player;
import io.github.cdimascio.dotenv.Dotenv;

public class PlayerDAOTest {
    static DatabaseConnection databaseConnection;

    /**
     * creating a mock Dotenv object to be injected into the DatabaseConnection
     * getInstance method
     * 
     * @throws SQLException
     * @throws IOException
     */
    @BeforeAll
    public static void setUpAll() throws SQLException, IOException {
        Dotenv dotenv = mock(Dotenv.class);
        when(dotenv.get("DATABASE_URL")).thenReturn("jdbc:sqlite:test.db");
        databaseConnection = DatabaseConnection.getInstance(dotenv);
    }

    @BeforeEach
    public void setUp() throws SQLException {
        databaseConnection.getConnection().createStatement().execute("DROP TABLE IF EXISTS players");
        databaseConnection.getConnection().createStatement().execute(
                "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY AUTOINCREMENT, name string, ia BOOLEAN)");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        databaseConnection.getConnection().createStatement().execute("DROP TABLE IF EXISTS player");
    }

    @Test
    void testCreateValidPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player("Player 1", false);
        assertDoesNotThrow(() -> playerDAO.create(player));
        assertNotNull(playerDAO.get(player.getId()));
    }

    @Test
    void testCreateNullPlayer() {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = null;
        assertThrows(IllegalArgumentException.class, () -> playerDAO.create(player));
    }

    @Test
    void testGetExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player("Player 1", false);
        playerDAO.create(player);
        Player playerFromDB = playerDAO.get(player.getId()).get();
        assertAll(() -> assertEquals("Player 1", playerFromDB.getName()), () -> assertFalse(playerFromDB.getIa()));
    }

    @Test
    void testGetNonExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        assertFalse(playerDAO.get(1).isPresent());
    }

    @Test
    void testUpdateExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player("Player 1", false);
        playerDAO.create(player);
        player.setName("Player 2");
        player.setIa(true);
        assertTrue(playerDAO.update(player));
        Player playerFromDB = playerDAO.get(player.getId()).get();
        assertAll(() -> assertEquals("Player 2", playerFromDB.getName()), () -> assertTrue(playerFromDB.getIa()));
    }

    @Test
    void testUpdateNonExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player(1, "Player 1", false);
        assertFalse(playerDAO.update(player));
    }

    @Test
    void testUpdatePlayerWithoutId() throws IllegalArgumentException, SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player("Player 1", false);
        assertThrows(IllegalArgumentException.class, () -> playerDAO.update(player));
    }

    @Test
    void testUpdateNullPlayer() {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = null;
        assertThrows(IllegalArgumentException.class, () -> playerDAO.update(player));
    }

    @Test
    void testDeleteExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        Player player = new Player("Player 1", false);
        playerDAO.create(player);
        assertTrue(playerDAO.delete(player.getId()));
    }

    @Test
    void testDeleteNonExistingPlayer() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        assertFalse(playerDAO.delete(1));
    }

    @Test
    void testListEmptyPlayers() throws SQLException {
        PlayerDAO playerDAO = new PlayerDAO(databaseConnection.getConnection());
        List<Player> players = playerDAO.list();

        assertNotNull(players);
        assertTrue(players.isEmpty());
    }

    @Test
    void testListWithPlayers() throws SQLException {
        // Arrange
        PlayerDAO playerDAO = new PlayerDAO();
        Player player1 = new Player("Player 1", false);
        Player player2 = new Player("Player 2", true);
        playerDAO.create(player1);
        playerDAO.create(player2);

        // Act
        List<Player> players = playerDAO.list();
        // Assert
        assertNotNull(players);
        assertEquals(2, players.size());
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
    }
}