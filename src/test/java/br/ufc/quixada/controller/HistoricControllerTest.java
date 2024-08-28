package br.ufc.quixada.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HistoricControllerTest extends ApplicationTest {

    private HistoricController controller;
    private Scene startingScene;

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a cena inicial
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufc/quixada/fxml/historic.fxml"));
        Parent root = loader.load();
        startingScene = new Scene(root);
        controller = new HistoricController();

        // Inicializa o controller, se necessário
        controller.initialize();

        // Exibe a cena
        stage.setScene(startingScene);
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
        // Cena antes do clique
        Parent previousRoot = startingScene.getRoot();

        // Clica no botão para voltar à Home
        clickOn("#ButtonBackToHome");

        // Aguarda a mudança de cena
        sleep(1000);

        // Verifica se a cena mudou
        assertNotEquals(previousRoot, targetWindow().getScene().getRoot());
    }
}
