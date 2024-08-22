package br.ufc.quixada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.ufc.quixada.model.Player;

public class PlayerDAO {
    private Connection connection;

    public PlayerDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Player> list() throws SQLException {
        // Lista todos os jogadores do banco de dados
        String sql = "SELECT id, name, ia FROM player";
        ArrayList<Player> players = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Player player = new Player(resultSet.getInt("id"), resultSet.getString("name"),
                            resultSet.getBoolean("ia"));
                    // Adiciona o jogador na lista
                    players.add(player);
                }
                return players;
            }
        }
    }

    public Optional<Player> get(int id) throws SQLException {
        String sql = "SELECT id, name, ia FROM player WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Player player = new Player(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getBoolean("ia"));
                    return Optional.of(player);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    /**
     * Inserts a new player into the database and sets the id of the player object
     * 
     * @param player
     */
    public void create(Player player) throws SQLException, IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        String sql = "INSERT INTO player (name, ia) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setBoolean(2, player.getIa());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating player failed, no ID obtained.");
                }
            }
        }
    }

    public boolean update(Player player) throws SQLException, IllegalArgumentException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (player.getId() == null) {
            throw new IllegalArgumentException("Player must have an id");
        }
        String sql = "UPDATE player SET name = ?, ia = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getName());
            statement.setBoolean(2, player.getIa());
            statement.setInt(3, player.getId());

            int numberOfUpdates = statement.executeUpdate();
            return numberOfUpdates == 1;

        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM player WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, id);
            int numberOfDeletes = statement.executeUpdate();
            return numberOfDeletes == 1;
        }
    }
}
