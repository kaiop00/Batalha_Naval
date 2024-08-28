package br.ufc.quixada.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.ufc.quixada.util.SceneManager;

public class HomeControllerTest extends ApplicationTest {

    private Scene startingScene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/home.fxml"));
        Parent root = loader.load();
        startingScene = new Scene(root);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(stage -> {
            SceneManager.initialize(stage);
            SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
            stage.show();
        });
    }


    @Test
    public void testStartSinglePlayer() throws IOException {
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, targetWindow().getScene().getRoot(), "A cena de match no modo singleplayer não foi carregada.");
        clickOn("#buttonStartSinglePlayer");
        sleep(1000);
        // Verifica se a match scene foi carregada
        assertNotEquals(previousRoot, targetWindow().getScene().getRoot(), "A cena de match no modo singleplayer não foi carregada.");
    }

    @Test
    public void testStartMultiPlayer() throws IOException {
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, targetWindow().getScene().getRoot());
        clickOn("#buttonStartMultiPlayer");
        sleep(1000);
        // Verifica se a match scene foi carregada
        assertNotEquals(previousRoot, targetWindow().getScene().getRoot());
    }

    @Test
    public void testShowHistory() {
        Parent previousRoot = startingScene.getRoot();
        assertEquals(previousRoot, targetWindow().getScene().getRoot(), "A cena de histórico de partidas não foi carregada.");
        clickOn("#buttonShowHistory");
        sleep(1000);
        // Verifica se a cena histórico foi carregada
        assertNotEquals(previousRoot, targetWindow().getScene().getRoot(), "A cena de histórico de partidas não foi carregada.");
    }
}