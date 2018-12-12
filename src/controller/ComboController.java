package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import library.Helper;

import java.net.URL;
import java.util.ResourceBundle;

public class ComboController implements Initializable {

    @FXML
    private VBox indexCombo;

    Helper helper = new Helper();

    @FXML
    void addNewCombo(MouseEvent event) {
        String comboUrl = "/view/combo/new.fxml";
        helper.loadVBoxContent(comboUrl, indexCombo);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
