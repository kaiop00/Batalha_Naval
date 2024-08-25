package br.ufc.quixada;

import br.ufc.quixada.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Batalha Naval");
        stage.setMaximized(true);
        
        SceneManager.initialize(stage);
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
        
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}