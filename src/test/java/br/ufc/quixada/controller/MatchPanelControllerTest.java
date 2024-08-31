package br.ufc.quixada.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import br.ufc.quixada.controller.MatchPanelController;
import br.ufc.quixada.model.Match;
import br.ufc.quixada.util.SceneManager;

public class MatchPanelControllerTest extends ApplicationTest {

    private Scene startingScene;
    private MatchPanelController controller;
    private Match matchMock;
    private GridPane playerBoardMock;
    private GridPane opponentBoardMock;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/matchPanel.fxml"));
        Parent root = loader.load();
        startingScene = new Scene(root);
        controller = loader.getController();

        // Mock Match
        matchMock = mock(Match.class);
        controller.setMatch(matchMock);

        // Mock GridPanes
        playerBoardMock = mock(GridPane.class);
        opponentBoardMock = mock(GridPane.class);

        controller.playerBoard = playerBoardMock;
        controller.opponentBoard = opponentBoardMock;

        // Mock GetButtonAt
        when(playerBoardMock.getRowCount()).thenReturn(10);
        when(playerBoardMock.getColumnCount()).thenReturn(10);
        when(opponentBoardMock.getRowCount()).thenReturn(10);
        when(opponentBoardMock.getColumnCount()).thenReturn(10);

        stage.setScene(startingScene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(stage -> {
            stage.setScene(startingScene);  // Certifique-se de que a cena está definida
            stage.show();
            SceneManager.initialize(stage);
        });
    }

    @Test
    public void testMakePlayHit() {
        int row = 2;
        int col = 3;
        GridPane board = opponentBoardMock;

        // Configure o mock para retornar "hit" (acerto)
        when(matchMock.makePlay(row, col, board)).thenReturn(true);

        // Chama o método que deve ser testado
        controller.makePlay(row, col, board);

        // Verifica se o botão na posição (row, col) foi estilizado corretamente
        Button clickedCell = (Button) controller.getButtonAt(board, row, col);
        assertNotNull(clickedCell, "O botão deve estar presente no tabuleiro.");
        assertEquals("-fx-background-color: red;", clickedCell.getStyle(), "A célula deve estar marcada como acerto.");
    }

    @Test
    public void testMakePlayMiss() {
        int row = 4;
        int col = 5;
        GridPane board = opponentBoardMock;

        // Configure o mock para retornar "miss" (erro)
        when(matchMock.makePlay(row, col, board)).thenReturn(false);

        // Chama o método que deve ser testado
        controller.makePlay(row, col, board);

        // Verifica se o botão na posição (row, col) foi estilizado corretamente
        Button clickedCell = (Button) controller.getButtonAt(board, row, col);
        assertNotNull(clickedCell, "O botão deve estar presente no tabuleiro.");
        assertEquals("-fx-background-color: blue;", clickedCell.getStyle(), "A célula deve estar marcada como erro.");
    }

    @Test
    public void testSetupBoards() {
        // Verifica se os métodos foram chamados para configurar os tabuleiros
        controller.setupBoards();

        // Verifica se os botões foram adicionados aos tabuleiros
        verify(playerBoardMock, times(10)).add(any(Button.class), anyInt(), anyInt());
        verify(opponentBoardMock, times(10)).add(any(Button.class), anyInt(), anyInt());
    }


    @Test
    public void testGiveUp() {
        // Verifica se a partida é finalizada e a cena é alterada
        doNothing().when(matchMock).end();
        controller.giveUp();
        verify(matchMock).end();
        verify(SceneManager.class);
        
    }

    @Test
    public void testRematch() {
        // Verifica se uma nova partida é iniciada
        controller.rematch();
        verify(matchMock).end(); // Verifica se o método end() foi chamado para finalizar a partida antiga
        verify(controller).startMatch(); // Verifica se startMatch() foi chamado para iniciar uma nova partida
    }
}
