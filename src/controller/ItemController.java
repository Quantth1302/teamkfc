package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import helper.Helper;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private VBox indexItem;

    @FXML
    private VBox newItemVB;

    Helper helper = new Helper();

    @FXML
    void addNewItem(MouseEvent event) {
        String itemUrl = "/view/item/new.fxml";
        helper.loadVBoxContent(itemUrl, indexItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
