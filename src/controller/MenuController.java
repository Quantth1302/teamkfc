package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lib.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public void nextToDashboard(MouseEvent event) {
        String dashboard = "/view/index/index.fxml";

        try {
            Parent root = FXMLLoader.load(getClass().getResource(dashboard));
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void showBillDialog(MouseEvent event) throws IOException {
        String billDialogUrl = "/view/menu/bill.fxml";

        Parent billDialog = FXMLLoader.load(getClass().getResource(billDialogUrl));
        Scene scene = new Scene(billDialog);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
