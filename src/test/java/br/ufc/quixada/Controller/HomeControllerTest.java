package br.ufc.quixada.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import br.ufc.quixada.dao.MatchHistoryDAO;
import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeControllerTest extends BaseControllerTest {

    private Scene startingScene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/home.fxml"));
        Parent root = loader.load();
        startingScene = new Scene(root);
        HomeController controller = loader.getController();
        MatchHistoryDAO historicDAO = mock(MatchHistoryDAO.class);
        controller.setMatchHistoryDAO(historicDAO);
        when(historicDAO.list()).thenReturn(new ArrayList<>());
        stage.show();
    }

    @BeforeEach
    public void setUp() throws SQLException, TimeoutException {
        FxToolkit.registerPrimaryStage();

        FxToolkit.setupStage(stage -> {
            stage.setScene(startingScene); // Certifique-se de que a cena está definida stage.show();
            SceneManager.initialize(stage);
        });
    }

    @Test
    public void testStartSinglePlayer() throws IOException {
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage(); // Obtém o Stage registrado
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, stage.getScene().getRoot());

        // Clica no botão para voltar à Home
        clickOn("#buttonStartSinglePlayer");

        // Aguarda a mudança de cena
        sleep(1000);

        // Verifica se a cena mudou
        assertNotEquals(previousRoot, stage.getScene().getRoot());
    }

    @Test
    public void testStartMultiPlayer() throws IOException {
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage(); // Obtém o Stage registrado
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, stage.getScene().getRoot());

        // Clica no botão para voltar à Home
        clickOn("#buttonStartMultiPlayer");

        // Aguarda a mudança de cena
        sleep(1000);

        // Verifica se a cena mudou
        assertNotEquals(previousRoot, stage.getScene().getRoot());
    }

    @Test
    public void testShowHistory() {
        Stage stage = FxToolkit.toolkitContext().getRegisteredStage(); // Obtém o Stage registrado
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, stage.getScene().getRoot());

        // Clica no botão para voltar à Home
        clickOn("#buttonShowHistory");

        // Aguarda a mudança de cena
        sleep(1000);

        // Verifica se a cena mudou
        assertNotEquals(previousRoot, stage.getScene().getRoot());
    }
}