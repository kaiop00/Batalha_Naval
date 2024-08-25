package br.ufc.quixada.controller;

import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import javafx.application.Platform;
import br.ufc.quixada.model.Player;
import br.ufc.quixada.model.Match;
import br.ufc.quixada.model.Board;

import java.io.IOException;
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
        buttonStartSinglePlayer.setOnAction(e -> {
            try {
                handleStartSinglePlayer();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonStartMultiPlayer.setOnAction(e -> {
            try {
                handleStartMultiPlayer();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonShowHistory.setOnAction(e -> handleShowHistory());
        buttonExit.setOnAction(e -> handleExit());
    }

    @FXML
    private void handleStartSinglePlayer() throws IOException {
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

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/br/ufc/quixada/fxml/match.fxml"));
        Parent root = loader.load();
        MatchPanelController controller = loader.getController();

        controller.setMatch(match);

        SceneManager.setRoot(root);
    }

    @FXML
    private void handleStartMultiPlayer() throws IOException {
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

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/br/ufc/quixada/fxml/match.fxml"));
        Parent root = loader.load();
        MatchPanelController controller = loader.getController();

        controller.setMatch(match);

        SceneManager.setRoot(root);
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
