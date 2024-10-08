package br.ufc.quixada.controller;

import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import br.ufc.quixada.model.MatchHistory;
import br.ufc.quixada.model.Player;

import java.sql.SQLException;
import java.util.List;

import br.ufc.quixada.dao.MatchHistoryDAO;
// import javafx.scene.layout.HBox;
// import javafx.scene.control.Label;

// import java.util.List;

public class HistoricController {

    @FXML
    private VBox matchesHistory;

    @FXML
    private Button ButtonBackToHome;

    private MatchHistoryDAO historicDAO;

    @FXML
    public void initialize() {
        ButtonBackToHome.setOnAction(e -> backToHome());
    }

    private void loadMatchHistory() throws SQLException {
        List<MatchHistory> matchHistories = historicDAO.list();
        for (MatchHistory match : matchHistories) {
            HBox matchBox = createMatchBox(match);
            matchesHistory.getChildren().add(matchBox);
        }
    }

    public void setMatchHistoryDAO(MatchHistoryDAO historyDAO) throws SQLException {
        this.historicDAO = historyDAO;
        loadMatchHistory();
    }

    private HBox createMatchBox(MatchHistory match) {
        HBox matchBox = new HBox();
        matchBox.getStyleClass().add("match-box");
        matchBox.setMinWidth(300); // Define uma largura mínima
        matchBox.setPrefWidth(400); // Define uma largura preferida
    
        VBox matchInfo = new VBox();
        matchInfo.getStyleClass().add("match-info");
        matchInfo.setMinWidth(200); // Define uma largura mínima para a VBox
    
        Label dateLabel = new Label("Date: " + match.getDateTime().toString());
        Label winnerLabel = new Label("Winner: " + match.getWinner().getName());
        VBox playersLabel = new VBox();
        playersLabel.getChildren().add(new Label("Players: "));
        for (Player player : match.getPlayers()) {
            Label playerLabel = new Label(player.getName());
            playersLabel.getChildren().add(playerLabel);
        }
    
        matchInfo.getChildren().addAll(dateLabel, winnerLabel, playersLabel);
        matchBox.getChildren().add(matchInfo); // Adiciona matchInfo a matchBox
    
        return matchBox;
    }

    @FXML
    private void backToHome() {
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
    }
}