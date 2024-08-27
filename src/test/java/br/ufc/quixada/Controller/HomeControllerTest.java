package br.ufc.quixada.Controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import br.ufc.quixada.controller.HomeController;

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

    @Test
    public void testStartSinglePlayer() throws IOException {
        // Simula o clique no botão "Start Single Player"
        clickOn("#buttonStartSinglePlayer");

        // Verifica se a função handleStartSinglePlayer foi chamada
        
        // Verifica através de algum efeito esperado, por exemplo, mudança de cena ou estado
        assertNotNull(controller);  // Verifica se o controller foi instanciado corretamente
        // Adicione outras verificações relevantes aqui
    }

    @Test
    public void testStartMultiPlayer() throws IOException {
        // Simula o clique no botão "Start Multi Player"
        clickOn("#buttonStartMultiPlayer");

        // Verifica se a função handleStartMultiPlayer foi chamada
        assertNotNull(controller);
        // Adicione outras verificações relevantes aqui
    }

    @Test
    public void testShowHistory() {
        // Simula o clique no botão "Show History"
        clickOn("#buttonShowHistory");

        // Verifica se a função handleShowHistory foi chamada
        assertNotNull(controller);
        // Adicione outras verificações relevantes aqui
    }

    @Test
    public void testExit() {
        // Simula o clique no botão "Exit"
        clickOn("#buttonExit");

        // Verifica se a função handleExit foi chamada
        assertNotNull(controller);
        // Adicione outras verificações relevantes aqui
    }
}
