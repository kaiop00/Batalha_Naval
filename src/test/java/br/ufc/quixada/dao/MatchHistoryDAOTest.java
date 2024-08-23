package br.ufc.quixada.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufc.quixada.model.MatchHistory;
import br.ufc.quixada.model.Player;
import io.github.cdimascio.dotenv.Dotenv;

public class MatchHistoryDAOTest {
    static DatabaseConnection databaseConnection;

    @BeforeAll
    static void setUpAll() throws SQLException {
        Dotenv dotenv = mock(Dotenv.class);
        when(dotenv.get("DATABASE_URL")).thenReturn("jdbc:sqlite::memory:");
        databaseConnection = DatabaseConnection.getInstance(dotenv);
    }

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String createPlayerTable = "CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY, name VARCHAR(255) NOT NULL, ia BOOLEAN DEFAULT FALSE);";
        String createMatchHistoryTable = "CREATE TABLE IF NOT EXISTS match_history (id INTEGER PRIMARY KEY, winner_id INT NOT NULL, date DATETIME DEFAULT CURRENT_TIME, FOREIGN KEY (winner_id) REFERENCES player(id));";
        String createMatchPlayersTable = "CREATE TABLE IF NOT EXISTS match_players (match_id INT NOT NULL, player_id INT NOT NULL, FOREIGN KEY (match_id) REFERENCES match_history(id), FOREIGN KEY (player_id) REFERENCES player(id), UNIQUE(match_id, player_id));";

        statement.execute(createPlayerTable);
        statement.execute(createMatchHistoryTable);
        statement.execute(createMatchPlayersTable);

        statement.close();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Connection connection = databaseConnection.getConnection();
        Statement statement;
        statement = connection.createStatement();
        statement.execute("DROP TABLE IF EXISTS match_players;");
        statement.execute("DROP TABLE IF EXISTS match_history;");
        statement.execute("DROP TABLE IF EXISTS player;");
        statement.close();
    }

    @Test
    void testGetExistingMatchingHistoryDoesNotThrows() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        assertDoesNotThrow(() -> matchHistoryDAO.get(matchHistory.getId()));
    }

    @Test
    void testListWithExistingItensReturnResults() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        ArrayList<MatchHistory> matchHistories = (ArrayList<MatchHistory>) matchHistoryDAO.list();
        assertEquals(1, matchHistories.size());
    }

    @Test
    void testListWithoutExistingItensReturnEmptyList() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<MatchHistory> matchHistories = (ArrayList<MatchHistory>) matchHistoryDAO.list();
        assertEquals(0, matchHistories.size());
    }

    @Test
    void testcreateValidItemApplyChanges() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        MatchHistory matchHistoryFromDatabase = matchHistoryDAO.get(matchHistory.getId()).get();
        assertNotNull(matchHistory.getId());
        assertEquals(matchHistory, matchHistoryFromDatabase);
    }

    @Test
    void testCreateNullItemThrows() {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        MatchHistory matchHistory = null;
        assertThrows(IllegalArgumentException.class, () -> matchHistoryDAO.create(matchHistory));
    }

    @Test
    void testUpdateValidItemDoesNotThrows() throws SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        matchHistory.setWinner(players.get(1));
        matchHistory.setLocalDateTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        assertDoesNotThrow(() -> matchHistoryDAO.update(matchHistory));
    }

    @Test
    void testUpdateValidItemApplyChanges() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        matchHistory.setWinner(players.get(1));
        matchHistory.setLocalDateTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        matchHistoryDAO.update(matchHistory);
        assertEquals(matchHistory, matchHistoryDAO.get(matchHistory.getId()).get());
    }

    @Test
    void testUpdateNonExistingItemFails() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(-1, LocalDateTime.now(), players, players.get(0));
        assertFalse(matchHistoryDAO.update(matchHistory));
    }

    @Test
    void testUpdateNullItemThrows() {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        MatchHistory matchHistory = null;
        assertThrows(IllegalArgumentException.class, () -> matchHistoryDAO.update(matchHistory));
    }

    @Test
    void testUpdateNullIdItemThrows() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        assertThrows(IllegalArgumentException.class, () -> matchHistoryDAO.update(matchHistory));
    }

    @Test
    void testDeleteValidItemDoesNotThrows() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        assertDoesNotThrow(() -> matchHistoryDAO.delete(matchHistory.getId()));
    }

    @Test
    void testDeleteValidItemRemovesFromDatabase() throws IllegalArgumentException, SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        ArrayList<Player> players = (ArrayList<Player>) createPlayers(databaseConnection.getConnection());
        MatchHistory matchHistory = new MatchHistory(LocalDateTime.now(), players, players.get(0));
        matchHistoryDAO.create(matchHistory);
        matchHistoryDAO.delete(matchHistory.getId());
        Optional<MatchHistory> matchHistoryFromDatabase = matchHistoryDAO.get(matchHistory.getId());
        assertTrue(matchHistoryFromDatabase.isEmpty());
    }

    @Test
    void testDeleteInvalidItemFails() throws SQLException {
        MatchHistoryDAO matchHistoryDAO = new MatchHistoryDAO(databaseConnection.getConnection());
        assertFalse(matchHistoryDAO.delete(-1));
    }

    /**
     * Helper function to create a list of players
     * 
     * @return a two players list
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    List<Player> createPlayers(Connection connection) throws IllegalArgumentException, SQLException {
        PlayerDAO playerDAO = new PlayerDAO(connection);

        Player player1 = new Player("Player 1", false);
        Player player2 = new Player("Player 2", true);
        playerDAO.create(player1);
        playerDAO.create(player2);
        return new ArrayList<>(List.of(player1, player2));
    }
}
