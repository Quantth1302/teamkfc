package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import library.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    Helper helper = new Helper();

    @FXML
    public BorderPane borderPane;

    @FXML
    public void item(MouseEvent event) {
        String item = "/view/item/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void combo(MouseEvent event) {
        String item = "/view/combo/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void sale(MouseEvent event) {
        String item = "/view/sale/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void statistic(MouseEvent event) {
        String item = "/view/statistic/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void nextToMenu(MouseEvent event) {
        String menu = "/view/menu/index.fxml";
        try {
            Parent root = FXMLLoader.load(getClass().getResource(menu));
            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
