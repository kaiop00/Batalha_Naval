package br.ufc.quixada.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import br.ufc.quixada.dao.MatchHistoryDAO;
import br.ufc.quixada.model.MatchHistory;
import br.ufc.quixada.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoricControllerTest extends ApplicationTest {

    private MatchHistoryDAO mockMatchHistoryDAO;
    private HistoricController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/historic.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        // Substitua o DAO real por um mock
        mockMatchHistoryDAO = Mockito.mock(MatchHistoryDAO.class);
        controller.initialize();

        // Exibe a cena
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        // Configura um histórico de partidas mockado
        LocalDateTime matchDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Player winner = new Player("John Doe", false);
        MatchHistory match = new MatchHistory(matchDate, Arrays.asList(winner, new Player("Jane Doe", false)), winner);

        // Mocka a resposta do DAO para retornar a lista com o histórico de partidas
        Mockito.when(mockMatchHistoryDAO.list()).thenReturn(Collections.singletonList(match));
    }

    @Test
    public void testLoadMatchHistory(FxRobot robot) {
        VBox matchesHistory = robot.lookup("#matchesHistory").queryAs(VBox.class);
        assertNotNull(matchesHistory);
        assertEquals(1, matchesHistory.getChildren().size());

        HBox matchBox = (HBox) matchesHistory.getChildren().get(0);
        assertNotNull(matchBox);

        VBox matchInfo = (VBox) matchBox.getChildren().get(0);
        Label dateLabel = (Label) matchInfo.getChildren().get(0);
        Label winnerLabel = (Label) matchInfo.getChildren().get(1);
        
        assertEquals("Date: 2023-08-26", dateLabel.getText());
        assertEquals("Winner: John Doe", winnerLabel.getText());
    }

    /*@Test
    public void testBackToHome(FxRobot robot) {
        robot.clickOn("#ButtonBackToHome");

        Parent root = robot.lookup(".root").query();
        assertNotNull(root);

        Label homeLabel = robot.lookup("#homeLabel").queryAs(Label.class);
        assertNotNull(homeLabel);
        assertEquals("Home", homeLabel.getText());
    }*/
}
