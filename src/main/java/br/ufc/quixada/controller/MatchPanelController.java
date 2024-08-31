package br.ufc.quixada.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import br.ufc.quixada.model.Match;
import br.ufc.quixada.util.SceneManager;

public class MatchPanelController {

    
    @FXML
    public GridPane playerBoard; 

    @FXML
    public GridPane opponentBoard;

    @FXML
    private Button ButtonQuit;

    @FXML
    private Pane shipsSpaces;

    private Match match;

    @FXML
    private void initialize() {
        match = new Match(null, null); // Nova partida
        setupBoards(); // Configura os tabuleiros do jogador e oponente
        setupShipsSpaces(); // Configura os espaços para os navios
        ButtonQuit.setOnAction(e -> giveUp()); // Configura o botão de desistência
    }

    public void setupBoards() {
        // Configuração dos tabuleiros
        for (int row = 0; row < playerBoard.getRowCount(); row++) {
            for (int col = 0; col < playerBoard.getColumnCount(); col++) {
                Button cell = new Button();
                cell.setPrefSize(30, 30);
                cell.setOnAction(e -> makePlay(row, col, playerBoard));
                playerBoard.add(cell, col, row);
            }
        }

        for (int row = 0; row < opponentBoard.getRowCount(); row++) {
            for (int col = 0; col < opponentBoard.getColumnCount(); col++) {
                Button cell = new Button();
                cell.setPrefSize(30, 30);
                cell.setOnAction(e -> makePlay(row, col, opponentBoard));
                opponentBoard.add(cell, col, row);
            }
        }
    }

    public void setupShipsSpaces() {
        //  Posicionar navios aleatoriamente no grid do jogador
        int[] shipSizes = {5, 4, 3, 3, 2}; // Tamanhos dos navios

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                // Gera uma posição inicial aleatória
                int row = (int) (Math.random() * playerBoard.getRowCount());
                int col = (int) (Math.random() * playerBoard.getColumnCount());

                // Define a direção do navio (0 = horizontal, 1 = vertical)
                int direction = (int) (Math.random() * 2);

                // Tenta posicionar o navio
                if (canPlaceShip(row, col, size, direction)) {
                    placeShip(row, col, size, direction);
                    placed = true;
                }
            }
        }
    }

    boolean canPlaceShip(int row, int col, int size, int direction) {
        // Lógica para verificar se o navio pode ser colocado na posição especificada
        if (direction == 0) { // Horizontal
            if (col + size > playerBoard.getColumnCount()) return false;
        } else { // Vertical
            if (row + size > playerBoard.getRowCount()) return false;
        }
        
        return true;
    }

    private void placeShip(int row, int col, int size, int direction) {
        // Lógica para posicionar o navio no tabuleiro
        for (int i = 0; i < size; i++) {
            Button shipCell = new Button();
            shipCell.setStyle("-fx-background-color: gray;"); // Cor do navio
            if (direction == 0) {
                playerBoard.add(shipCell, col + i, row); // Horizontal
            } else {
                playerBoard.add(shipCell, col, row + i); // Vertical
            }
        }
    }

    public void makePlay(int row, int col, GridPane board) {
        boolean hit = match.makePlay(row, col, board); // Realiza a jogada e verifica se foi um acerto

        Button clickedCell = getButtonAt(board, row, col);
        if (hit) {
            clickedCell.setStyle("-fx-background-color: red;"); // Marca um acerto
        } else {
            clickedCell.setStyle("-fx-background-color: blue;"); // Marca um erro
        }
    }

    public Button getButtonAt(GridPane grid, int row, int col) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (Button) node;
            }
        }
        return null;
    }

    public MatchPanelController getController() {
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void rematch() {
        startMatch(); // Reinicia a partida
    }

    public void startMatch() {
        match = new Match(null, null); // Inicia uma nova partida
        setupBoards(); // Reconfigura os tabuleiros
        setupShipsSpaces(); // Reconfigura os espaços dos navios
    }

    public void giveUp() {
        match.end(); // Finaliza a partida atual
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml"); // Retorna à tela inicial
    }
}
