package br.ufc.quixada.controller;

// import br.ufc.quixada.model.MatchHistory;
// import br.ufc.quixada.dao.HistoricDAO;
import br.ufc.quixada.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
// import javafx.scene.layout.HBox;
// import javafx.scene.control.Label;

// import java.util.List;

public class HistoricController {

    @FXML
    private VBox matchesHistory;

    @FXML
    private Button ButtonBackToHome;

    // private HistoricDAO historicDAO;

    @FXML
    public void initialize() {
        // historicDAO = new HistoricDAO();
        // loadMatchHistory();
        ButtonBackToHome.setOnAction(e -> backToHome());
    }

    // private void loadMatchHistory() {
    //     List<MatchHistory> matches = historicDAO.list();
    //     for (MatchHistory match : matches) {
    //         HBox matchBox = createMatchBox(match);
    //         matchesHistory.getChildren().add(matchBox);
    //     }
    // }

    // private HBox createMatchBox(MatchHistory match) {
    //     // ... (método permanece o mesmo)
    // }

    @FXML
    private void backToHome() {
        SceneManager.loadScene("/br/ufc/quixada/fxml/home.fxml");
    }
}