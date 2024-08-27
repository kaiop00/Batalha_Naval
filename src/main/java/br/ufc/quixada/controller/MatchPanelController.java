package br.ufc.quixada.controller;

import javafx.fxml.FXML;
import br.ufc.quixada.util.SceneManager;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import br.ufc.quixada.model.Match;
import javafx.scene.layout.Pane;

public class MatchPanelController {

    private GridPane boards;

    // @FXML
    private GridPane playerBoard;

    // @FXML
    private GridPane opponentBoard;

    @FXML
    private Button ButtonQuit;

    @FXML
    private Pane shipsSpaces;

    private Match match;


    @FXML
    private void initialize() {
        // match = new Match();
        // setupBoards();
        // setupShipsSpaces();
        ButtonQuit.setOnAction(e -> giveUp());
    }

    private void setupBoards() {
        // TODO: Configurar os tabuleiros no GridPane
    }

    private void setupShipsSpaces() {
        // TODO: Configurar os espaços para os navios no Pane
    }

    public void makePlay() {
        // TODO: Implementar a lógica para realizar uma jogada
    }

    public MatchPanelController getController() {
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void rematch() {
        // TODO: Implementar a lógica para reiniciar a partida
    }

    public void startMatch() {
        // TODO: Implementar a lógica para iniciar uma nova partida
    }

    private void giveUp() {
        // TODO: Implementar a lógica para desistir da partida
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
    }
}