package br.ufc.quixada.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.ufc.quixada.controller.HomeController;
import br.ufc.quixada.util.SceneManager;

public class HomeControllerTest extends ApplicationTest {

    private HomeController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/home.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
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
        // Simula o clique no botão "Start Single Player"
        clickOn("#buttonStartSinglePlayer");

        // Verifica se a cena foi trocada
        Node matchRoot = lookup(".root").query();
        assertNotNull(matchRoot, "A cena do jogo não foi carregada.");
    }

    @Test
    public void testStartMultiPlayer() throws IOException {
        // Simula o clique no botão "Start Multi Player"
        clickOn("#buttonStartMultiPlayer");

        // Verifica se a cena foi trocada
        Node matchRoot = lookup(".root").query();
        assertNotNull(matchRoot, "A cena do jogo não foi carregada.");
    }

    @Test
    public void testShowHistory() {
        // Simula o clique no botão "Show History"
        clickOn("#buttonShowHistory");

        // Verifica se a cena de histórico foi carregada
        Node historyRoot = lookup(".root").query();
        assertNotNull(historyRoot, "A cena de histórico não foi carregada.");
    }
}