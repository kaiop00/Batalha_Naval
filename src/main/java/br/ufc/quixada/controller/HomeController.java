package br.ufc.quixada.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class HomeController {
    @FXML
    private ImageView imageTest;

    @FXML
    private StackPane rootPane;
    
    @FXML
    private ImageView backgroundImage;

    @FXML
    private Button buttonStartSinglePlayer;

    @FXML
    private Button buttonStartMultiPlayer;

    @FXML
    private Button buttonShowHistory;

    @FXML
    private Button buttonExit;

    @FXML
    private void initialize() {
        this.imageTest = new ImageView(new Image("/br/ufc/quixada/images/wallpaper.jpg"));
        this.imageTest.setFitWidth(200);
        this.imageTest.setFitHeight(200);
        this.rootPane.getChildren().add(this.imageTest);
    }
}