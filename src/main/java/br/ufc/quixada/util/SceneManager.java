package br.ufc.quixada.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setFullScreen(true);
    }

    public static void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            if (primaryStage.getScene() == null) {
                // Se não houver uma cena, crie uma nova
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } else {
                // Se já existe uma cena, apenas troque o elemento raiz
                primaryStage.getScene().setRoot(root);
            }
            
            // Não é mais necessário chamar setFullScreen aqui, pois o estado de tela cheia será mantido
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading scene: " + e.getMessage());
        }
    }
}