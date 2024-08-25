package br.ufc.quixada.controller;

import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;

public class HomeController {
    @FXML
    private StackPane rootPane;
    
    @FXML
    private Button buttonStartSinglePlayer;
    
    @FXML
    private Button buttonStartMultiPlayer;
    
    @FXML
    private Button buttonShowHistory;
    
    @FXML
    private Button buttonExit;

    @FXML
    private void initialize() {
        buttonStartSinglePlayer.setOnAction(e -> handleStartSinglePlayer());
        buttonStartMultiPlayer.setOnAction(e -> handleStartMultiPlayer());
        buttonShowHistory.setOnAction(e -> handleShowHistory());
        buttonExit.setOnAction(e -> handleExit());
    }

    @FXML
    private void handleStartSinglePlayer() {
        System.out.println("Starting Single Player game...");
        // TODO: Implement single player game logic
    }

    @FXML
    private void handleStartMultiPlayer() {
        System.out.println("Starting Multi Player game...");
        // TODO: Implement multi player game logic
    }

    @FXML
    private void handleShowHistory() {
        SceneManager.loadScene("/br/ufc/quixada/fxml/historic.fxml");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}