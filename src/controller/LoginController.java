package controller;

import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import library.Helper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    double x = 0, y = 0;

    @FXML
    private TextField tf_userName;

    @FXML
    private PasswordField pf_password;

    Helper helper = new Helper();

    private static int role;
    private static String customer;
    private static String employee;

    //function sent roleId into MenuController
    public static int getRoleId() {
        return role;
    }
    public static String getCustomerId(){return customer;}
    public static String getEmployeeId(){return employee;}


    @FXML
    void login(MouseEvent event) throws SQLException, IOException {
        String userName = tf_userName.getText();
        String password = pf_password.getText();
        Connection connection = DbConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select users.user_name, users.password, users.role_id, users.id, customer.id as customer_id, employee.id as employee_id " +
                        "from users " +
                        "left join customer on customer.user_id = users.id " +
                        "left join employee on employee.user_id = users.id " +
                        "where users.user_name = '"+ userName +"' and password = '"+ password +"'");
        if (resultSet.next()){
            int roleId = resultSet.getInt("role_id");
            role = roleId;

            String customerId = resultSet.getString("customer_id");
            customer = customerId;

            String employeeId = resultSet.getString("employee_id");
            employee = employeeId;

            String indexUrl = "/view/menu/index.fxml";
            helper.loadParentNode(indexUrl, event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Tên đăng nhập hoặc mật khẩu không đúng!");
            alert.showAndWait();
        }
    }

    @FXML
    void signUp(MouseEvent event) throws IOException {
        String signUpUrl = "/view/Signup.fxml";
        helper.loadParentNode(signUpUrl, event);
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
