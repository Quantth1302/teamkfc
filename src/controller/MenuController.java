package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private MenuItem logout;

    @FXML
    private VBox vbMainMenu;

    @FXML
    private VBox vbItemSelected;

    @FXML
    private TextField tf_TotalPrice;

    @FXML
    private TextField tf_SalePrice;

    @FXML
    private TextField tf_PayPrice;

    private double totalPrice = 0.0;
    private double salePrice = 0.0;

    HashMap<String, Integer> checkExist = new HashMap<String, Integer>();

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
        String billDialogUrl = "/view/menu/invoice.fxml";

        Parent billDialog = FXMLLoader.load(getClass().getResource(billDialogUrl));
        Scene scene = new Scene(billDialog);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        String loginUrl = "/view/Login.fxml";
        Parent login = FXMLLoader.load(getClass().getResource(loginUrl));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Item> observableList = FXCollections.observableArrayList();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            ResultSet resultSet = connection.createStatement()
                    .executeQuery("select item.*, sale.percent, item_type.`name` as type_name  from item " +
                            "left join item_type on item.item_type_id = item_type.id " +
                            "left join sale on item.sale_id = sale.id");
            while (resultSet.next()) {
                observableList.add(new Item(resultSet.getString("id"),
                        resultSet.getString("employee_id"), resultSet.getDouble("price"),
                        resultSet.getInt("item_type_id"), resultSet.getInt("sale_id"),
                        resultSet.getInt("limit"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //add item into menu
        for (Item item : observableList) {
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(20, 20, 20, 20));
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getStyleClass().add("menu_item");

            ImageView imageView = new ImageView();
            imageView.setImage(new Image("/vendor/image/pizza_big.png"));
            imageView.setFitWidth(150);
            imageView.setFitHeight(100);
            hBox.getChildren().add(imageView);

            /* --vBox start-- */
            VBox vBox = new VBox();
            HBox.setMargin(vBox, new Insets(0, 0, 0, 20));

            String name = item.getName();
            Text itemName = new Text(name);
            itemName.getStyleClass().add("itemName");
            Text itemAddress = new Text("37 Tương mai, Q.Hai Bà Trưng, Hà Nội");
            itemAddress.getStyleClass().add("itemAddress");
            vBox.getChildren().add(itemName);
            vBox.getChildren().add(itemAddress);

            HBox hBoxIncludePrice = new HBox();
            hBoxIncludePrice.setAlignment(Pos.CENTER_LEFT);
            HBox.setMargin(hBoxIncludePrice, new Insets(5, 0, 0, 0));

            HBox hBoxMinimumPrice = new HBox();
            hBoxMinimumPrice.setAlignment(Pos.CENTER_LEFT);
            ImageView imageViewMinimumPrice = new ImageView("/vendor/icon/icons8-tags-64.png");
            imageViewMinimumPrice.setFitWidth(24);
            imageViewMinimumPrice.setFitHeight(24);

            //Giá KM
            //todo: get saleId not true
            double sale = (1 - (double) item.getSaleId() / 100) * item.getPrice();
            Text tMinimumPrice = new Text("Giá KM " + String.valueOf(sale) + "k");
            tMinimumPrice.setFont(Font.font("Arial", 14));
            hBoxMinimumPrice.getChildren().add(imageViewMinimumPrice);
            hBoxMinimumPrice.getChildren().add(tMinimumPrice);

            HBox hBoxPrice = new HBox();
            hBoxPrice.setAlignment(Pos.CENTER_LEFT);
            ImageView imageViewPrice = new ImageView("/vendor/icon/icons8-expensive-64.png");
            imageViewPrice.setFitWidth(24);
            imageViewPrice.setFitHeight(24);

            //Giá gốc
            Text tPrice = new Text("Giá " + String.valueOf(item.getPrice()) + "k");
            tPrice.setFont(Font.font("Arial", 14));
            hBoxPrice.getChildren().add(imageViewPrice);
            hBoxPrice.getChildren().add(tPrice);

            hBoxIncludePrice.getChildren().add(hBoxMinimumPrice);
            hBoxIncludePrice.getChildren().add(hBoxPrice);
            vBox.getChildren().add(hBoxIncludePrice);

            HBox hBoxSale = new HBox();
            hBoxSale.setAlignment(Pos.CENTER_LEFT);
            ImageView imageSale = new ImageView("/vendor/icon/icons8-sale-48.png");
            imageSale.setFitWidth(24);
            imageSale.setFitHeight(24);
            //Sale
            //todo: get saleId not true
            Text tSale = new Text("Khuyến mãi " + item.getSaleId() + "%");
            tSale.getStyleClass().add("tSale");
            hBoxSale.getChildren().add(imageSale);
            hBoxSale.getChildren().add(tSale);
            vBox.getChildren().add(hBoxSale);
            /* --vBox end-- */

            HBox hBoxButtonPrice = new HBox();
            hBoxButtonPrice.setAlignment(Pos.CENTER_RIGHT);
            Button btnPrice = new Button();
            btnPrice.getStyleClass().add("btnPrice");
            HBox.setMargin(btnPrice, new Insets(0, 0, 0, 50));
            ImageView imgPrice = new ImageView("/vendor/icon/icons8-us-dollar-64.png");
            imgPrice.setFitWidth(50);
            imgPrice.setFitHeight(53);
            btnPrice.setGraphic(imgPrice);

            btnPrice.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addItemSelected(item);
                }
            });

            hBoxButtonPrice.getChildren().add(btnPrice);

            hBox.getChildren().add(vBox);
            hBox.getChildren().add(hBoxButtonPrice);
            vbMainMenu.getChildren().add(hBox);
        }
    }

    private void addItemSelected(Item item) {
        String itemID = item.getId();

        if (!checkExist.containsKey(itemID)) {

            checkExist.put(itemID, 1);

            HBox hbItemSelected = new HBox();
            hbItemSelected.setId("hb" + itemID);
            hbItemSelected.setAlignment(Pos.CENTER_LEFT);
            Text quantity = new Text("1-");
            quantity.setId("txt" + itemID);
            hbItemSelected.getChildren().add(quantity);
            Text itemName = new Text(item.getName());
            hbItemSelected.getChildren().add(itemName);

            HBox hbButtonRemove = new HBox();
            hbButtonRemove.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(hbButtonRemove, Priority.ALWAYS);

            Button btnRemove = new Button();
            btnRemove.setText("X");
            btnRemove.getStyleClass().add("support_search");
            HBox.setMargin(btnRemove, new Insets(0, 5, 0, 5));

            btnRemove.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    removeItemFromListSelected(item);
                }
            });

            hbButtonRemove.getChildren().add(btnRemove);

            hbItemSelected.getChildren().add(hbButtonRemove);

            vbItemSelected.getChildren().add(hbItemSelected);
        } else {
            int quantity = checkExist.get(itemID);

            //tìm text có id là #txt...
            Text text = (Text) vbItemSelected.getParent().lookup("#txt" + itemID);
            if (text != null) {
                text.setText(String.valueOf(quantity + 1) + "-");
                checkExist.put(itemID, quantity + 1);
            }
        }

        double price = item.getPrice() * 1000;
        totalPrice = totalPrice + price;
        //todo: get saleId not true
        salePrice = salePrice + price * ((double) item.getSaleId() / 100);

        setTextPrice(totalPrice, salePrice);
    }

    private void removeItemFromListSelected(Item item) {
        String itemId = item.getId();
        double price = item.getPrice() * 1000;
        int quantity = checkExist.get(itemId);
        if (quantity - 1 > 0) {
            Text text = (Text) vbItemSelected.getParent().lookup("#txt" + itemId);
            text.setText(String.valueOf(quantity - 1) + "-");
            checkExist.put(itemId, quantity - 1);
        } else {
            HBox hbItemSelected = (HBox) vbItemSelected.getParent().lookup("#hb" + itemId);
            vbItemSelected.getChildren().remove(hbItemSelected);
            checkExist.remove(itemId);
        }

        totalPrice = totalPrice - price;

        //todo: get saleId not true
        salePrice = salePrice - price * ((double) item.getSaleId() / 100);
        setTextPrice(totalPrice, salePrice);
    }

    private void setTextPrice(Double totalPrice, Double salePrice) {
        tf_TotalPrice.setText(String.valueOf(totalPrice));
        tf_SalePrice.setText(String.valueOf(salePrice));
        tf_PayPrice.setText(String.valueOf(totalPrice - salePrice));
    }

}
