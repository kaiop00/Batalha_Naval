package br.ufc.quixada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.ufc.quixada.model.MatchHistory;
import br.ufc.quixada.model.Player;

public class MatchHistoryDAO {
    private Connection connection;
    private PlayerDAO playerDAO;

    public MatchHistoryDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.playerDAO = new PlayerDAO(connection);
    }

    public MatchHistoryDAO(Connection connection) {
        this.connection = connection;
        this.playerDAO = new PlayerDAO(connection);
    }

    public List<MatchHistory> list() throws SQLException {
        List<MatchHistory> matchHistories = new ArrayList<>();
        String matchHistorySQL = "SELECT * FROM match_history";
        try (PreparedStatement statement = connection.prepareStatement(matchHistorySQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String matchPlayersSQL = "SELECT * FROM match_players WHERE match_id = ?";
                List<Player> players = new ArrayList<>();
                Player winner = this.playerDAO.get(resultSet.getInt("winner_id")).get();
                try (PreparedStatement playerStatement = connection.prepareStatement(matchPlayersSQL)) {
                    playerStatement.setInt(1, resultSet.getInt("id"));
                    ResultSet playerResultSet = playerStatement.executeQuery();
                    while (playerResultSet.next()) {
                        Player player = this.playerDAO.get(playerResultSet.getInt("player_id")).get();
                        players.add(player);
                    }
                }
                MatchHistory matchHistory = new MatchHistory(resultSet.getTimestamp("date").toLocalDateTime(), players,
                        winner);
                matchHistories.add(matchHistory);
            }
            return matchHistories;
        }
    }

    public Optional<MatchHistory> get(int id) throws SQLException {
        String match_table_alias = "mh";
        String match_players_table_alias = "mp";
        String sql = "SELECT * FROM match_history " + match_table_alias + " LEFT JOIN match_players "
                + match_players_table_alias +
                " ON(" + match_table_alias + ".id = " + match_players_table_alias + ".match_id) WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int resultSize = 0;
            List<Player> players = new ArrayList<>();
            Player winner = null;
            LocalDateTime date = null;
            while (resultSet.next()) {
                resultSize++;
                int playerId = resultSet.getInt("player_id");
                int winnerId = resultSet.getInt("winner_id");
                Player player = this.playerDAO.get(playerId).get();
                players.add(player);
                if (date == null) {
                    date = resultSet.getTimestamp("date").toLocalDateTime();
                }
                if (winner == null & playerId == winnerId) {
                    winner = player;
                }
            }
            MatchHistory matchHistory = new MatchHistory(
                    id,
                    date,
                    players,
                    winner);
            if (resultSize == 0) {
                return Optional.empty();
            }
            return Optional.of(matchHistory);
        }
    }

    public void create(MatchHistory matchHistory) throws SQLException {
        if (matchHistory == null) {
            throw new IllegalArgumentException("MatchHistory cannot be null");
        }
        String matchHistorySQL = "INSERT INTO match_history (winner_id, date) VALUES (?, ?)";
        String matchPlayersSQL = "INSERT INTO match_players (match_id, player_id) VALUES (?, ?)";
        connection.setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(matchHistorySQL);

            statement.setInt(1, matchHistory.getWinner().getId());
            statement.setTimestamp(2, Timestamp.valueOf(matchHistory.getDateTime()));

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                matchHistory.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating match history failed, no row added.");
            }
            for (Player player : matchHistory.getPlayers()) {
                try (PreparedStatement playerStatement = connection.prepareStatement(matchPlayersSQL)) {
                    playerStatement.setInt(1, matchHistory.getId());
                    playerStatement.setInt(2, player.getId());
                    playerStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean update(MatchHistory matchHistory) throws SQLException {
        if (matchHistory == null) {
            throw new IllegalArgumentException("MatchHistory cannot be null");
        }
        if (matchHistory.getId() == null) {
            throw new IllegalArgumentException("MatchHistory id cannot be null");
        }
        int numberOfMatchHistoryUpdates = 0;
        connection.setAutoCommit(false);
        String updateMatchSQL = "UPDATE match_history SET winner_id = ? , date = ? WHERE id = ?";
        try {

            try (PreparedStatement statement = connection.prepareStatement(updateMatchSQL)) {
                statement.setInt(1, matchHistory.getWinner().getId());
                statement.setTimestamp(2, Timestamp.valueOf(matchHistory.getDateTime()));
                statement.setInt(3, matchHistory.getId());
                numberOfMatchHistoryUpdates = statement.executeUpdate();
                statement.close();
            }
            try (PreparedStatement deletePlayersStatement = connection
                    .prepareStatement("DELETE FROM match_players WHERE match_id = ?")) {
                deletePlayersStatement.setInt(1, matchHistory.getId());
                deletePlayersStatement.executeUpdate();
                deletePlayersStatement.close();
            }
            for (Player player : matchHistory.getPlayers()) {
                String updatePlayersSQL = "INSERT INTO match_players (player_id,match_id) VALUES(?,?)";
                try (PreparedStatement playerStatement = connection.prepareStatement(updatePlayersSQL)) {
                    playerStatement.setInt(1, player.getId());
                    playerStatement.setInt(2, matchHistory.getId());
                    playerStatement.executeUpdate();
                    playerStatement.close();
                }
            }
            return numberOfMatchHistoryUpdates == 1;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM match_history WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int numberOfDeletes = statement.executeUpdate();
            return numberOfDeletes == 1;
        }
    }

}
