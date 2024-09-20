package br.ufc.quixada.controller;

import br.ufc.quixada.model.MatchHistory;
import br.ufc.quixada.model.Player;
import br.ufc.quixada.dao.MatchHistoryDAO;
import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class HistoricController {
    @FXML
    private VBox matchesHistory;
    
    @FXML
    private Button ButtonBackToHome;
    
    private MatchHistoryDAO matchHistoryDAO;
    
    @FXML
    public void initialize() {
        try {
            matchHistoryDAO = new MatchHistoryDAO();
            loadMatchHistory();
        } catch (SQLException e) {
            System.err.println("Error initializing MatchHistoryDAO: " + e.getMessage());
            e.printStackTrace();
        }
        ButtonBackToHome.setOnAction(e -> backToHome());
    }
    
    private void loadMatchHistory() {
        try {
            List<MatchHistory> matches = matchHistoryDAO.list();
            for (MatchHistory match : matches) {
                HBox matchBox = createMatchBox(match);
                matchesHistory.getChildren().add(matchBox);
            }
        } catch (SQLException e) {
            System.err.println("Error loading match history: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private HBox createMatchBox(MatchHistory match) {
        HBox matchBox = new HBox(10);
        matchBox.getStyleClass().add("match-box");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = match.getDateTime().format(formatter);
        
        Label dateLabel = new Label("Date: " + formattedDate);
        dateLabel.getStyleClass().add("match-info");
        
        String playerNames = match.getPlayers().stream()
                                  .map(Player::getName)
                                  .collect(Collectors.joining(", "));
        Label playersLabel = new Label("Players: " + playerNames);
        playersLabel.getStyleClass().add("match-info");
        
        Label winnerLabel = new Label("Winner: " + match.getWinner().getName());
        winnerLabel.getStyleClass().add("match-info");
        
        matchBox.getChildren().addAll(dateLabel, playersLabel, winnerLabel);
        
        return matchBox;
    }
    
    @FXML
    private void backToHome() {
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
    }
}