package lib;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class Helper {
    public void loadBorderPaneContent(String content, BorderPane borderPane){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        borderPane.setCenter(root);
    }

    public void loadVBoxContent(String content, VBox vBox){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
        vBox.getChildren().removeAll();
        vBox.getChildren().setAll(root);
    }
}
