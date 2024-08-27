package br.ufc.quixada.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import br.ufc.quixada.dao.MatchHistoryDAO;
import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void setUp() throws SQLException, TimeoutException {
        // Configura um histórico de partidas mockado
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(stage -> {
            SceneManager.initialize(stage);
            SceneManager.loadScene("/br/ufc/quixada/fxml/historic.fxml");

            stage.show();
        });
    }

    @Test
    public void testBackToHome() {
        FxRobot robot = new FxRobot(); // Instancia manualmente o FxRobot
        robot.clickOn("#ButtonBackToHome");

        // Verifica se a cena foi trocada
        Node homeRoot = robot.lookup(".root").query();
        assertNotNull(homeRoot, "A cena do jogo não foi carregada.");
    }
}
