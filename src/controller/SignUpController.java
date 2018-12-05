package controller;

import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.SortedSet;

public class SignUpController implements Initializable {
    double x = 0, y = 0;

    @FXML
    private TextField tf_userName;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField pf_password;

    @FXML
    void signUp(MouseEvent event) {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            String userName = tf_userName.getText();
            String email = tf_email.getText();
            String password = pf_password.getText();
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate("insert into users (user_name, email, password)" +
                    " values ('"+ userName +"','" + email +"','"+ password +"')");
            if (status > 0){
                System.out.println("user registered");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getSceneX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
