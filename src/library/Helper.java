package library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public String randomString(){
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);

        return currentDate;
    }
}
