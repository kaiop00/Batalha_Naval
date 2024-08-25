package br.ufc.quixada.controller;

import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import javafx.application.Platform;
import br.ufc.quixada.model.Player;
import br.ufc.quixada.model.Match;
import br.ufc.quixada.model.Board;

import java.util.ArrayList;
import java.util.List;

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
        Player player1 = new Player("Player 1", false);
        Player player2 = new Player("Player 2", true);

        // Criação dos tabuleiros
        Board board1 = new Board();

        // Associação dos jogadores com seus tabuleiros
        List<Pair<Player, Board>> playerBoards = new ArrayList<>();
        playerBoards.add(new Pair<>(player1, board1));
        playerBoards.add(new Pair<>(player2, board1)); // Ambos os jogadores usam o mesmo tabuleiro no modo single player

        // Criação da partida
        Match match = new Match(playerBoards, player1);

        // Passar a partida para a próxima cena (assumindo que o SceneManager pode lidar com isso)
        SceneManager.loadScene("/br/ufc/quixada/fxml/match.fxml");
    }

    @FXML
    private void handleStartMultiPlayer() {
        System.out.println("Starting Multi Player game...");
        Player player1 = new Player("Player 1", false);
        Player player2 = new Player("Player 2", false);

        // Criação dos tabuleiros
        Board board1 = new Board();
        Board board2 = new Board();

        // Associação dos jogadores com seus tabuleiros
        List<Pair<Player, Board>> playerBoards = new ArrayList<>();
        playerBoards.add(new Pair<>(player1, board1));
        playerBoards.add(new Pair<>(player2, board2));

        // Criação da partida
        Match match = new Match(playerBoards, player1);

        // Passar a partida para a próxima cena
        SceneManager.loadScene("/br/ufc/quixada/fxml/match.fxml");
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
