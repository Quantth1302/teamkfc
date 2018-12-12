package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import library.Helper;

import java.net.URL;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    @FXML
    private VBox indexSale;

    Helper helper = new Helper();

    @FXML
    void addNewSale(MouseEvent event) {
        String saleUrl = "/view/sale/new.fxml";
        helper.loadVBoxContent(saleUrl, indexSale);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
