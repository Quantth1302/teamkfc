package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import library.Helper;
import library.Support;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    Helper helper = new Helper();

    private static Support action;
    public static Support getAction(){
        return action;
    }

    @FXML
    public BorderPane borderPane;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    void index(MouseEvent event) {
        String item = "/view/chart/chart.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void item(MouseEvent event) {
        action = Support.ITEM_ACTION;
        String item = "/view/item/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void combo(MouseEvent event) {
        action = Support.COMBO_ACTION;
        String item = "/view/combo/index.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

    @FXML
    public void sale(MouseEvent event) {
        action = Support.SALE_ACTION;
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
            helper.loadParentNode(menu, event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String item = "/view/chart/chart.fxml";
        helper.loadBorderPaneContent(item, borderPane);
    }

}
