package controller;

import database.DbConnection;
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
import library.Constant;
import library.Helper;
import library.Support;
import model.Combo;
import model.Item;
import service.ComboService;
import service.ItemService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private MenuItem logout;

    @FXML
    private Button btn_dashboard;

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

    @FXML
    private TextField menuSearchingContent;

    private double totalPrice = 0.0;
    private double salePrice = 0.0;

    HashMap<String, Integer> checkExist = new HashMap<String, Integer>();
    HashMap<String, Integer> checkType = new HashMap<>();

    private static String invoice;

    //function sent invoiceId into InvoiceController
    public static String getController() {
        return invoice;
    }

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
    public void logout(ActionEvent event) throws IOException {
        String loginUrl = "/view/Login.fxml";
        Parent login = FXMLLoader.load(getClass().getResource(loginUrl));

    }

    @FXML
    public void comboSupportedSearch(MouseEvent event) {
        ComboService comboService = new ComboService();
        ObservableList<Combo> comboList = null;
        try {
            comboList = comboService.getAllCombo_p(null, null, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addComboToMenu(comboList, Support.COMBO);
    }

    @FXML
    public void itemSupportedSearch(MouseEvent event) {
        ItemService itemService = new ItemService();
        ObservableList<Item> itemList = null;
        try {
            itemList = itemService.getAllItem_p(null, null, null, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addItemToMenu(itemList, Support.ITEM);
    }

    @FXML
    public void pay(MouseEvent event) {
        Helper helper = new Helper();
        //        String invoiceId = "KI-" + helper.randomString();
        String invoiceId = helper.getRandomInvoiceID();
        String currentDate = helper.getCurrentDate();
        String customerId = LoginController.getCustomerId();
        String employeeId = LoginController.getEmployeeId();

        String sql = null;

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            if (customerId == null) {
                sql = "INSERT INTO invoice " +
                        "VALUES ('" + invoiceId + "', null , '"+ employeeId +"' , '" + currentDate + "', '" + totalPrice + "', '" + (totalPrice - salePrice) + "')";
            } else if (employeeId == null){
                sql = "INSERT INTO invoice " +
                        "VALUES ('" + invoiceId + "', '"+ customerId +"' , null , '" + currentDate + "', '" + totalPrice + "', '" + (totalPrice - salePrice) + "')";
            } else {
                sql = "INSERT INTO invoice " +
                        "VALUES ('" + invoiceId + "', '"+ customerId +"' , '"+ employeeId +"' , '" + currentDate + "', '" + totalPrice + "', '" + (totalPrice - salePrice) + "')";
            }
            stmt.executeUpdate(sql);

            for (HashMap.Entry<String, Integer> selected : checkExist.entrySet()) {
                String key = selected.getKey();
                if (checkType.get(key) == 1) {
                    sql = "INSERT INTO invoice_detail " +
                            "VALUES ('" + invoiceId + "', '" + key + "' , '" + selected.getValue() + "' , null, null)";
                } else {
                    sql = "INSERT INTO invoice_detail " +
                            "VALUES ('" + invoiceId + "', null , null , '" + key + "', '" + selected.getValue() + "')";
                }
                stmt.executeUpdate(sql);

            }
            invoice = invoiceId;
            showInvoiceDialog();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void saleSupportedSearch(MouseEvent event) {
        ItemService itemService = new ItemService();
        ObservableList<Item> itemList = null;
        try {
            itemList = itemService.getAllItem_p(null, Constant.IS_SALE, null, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addItemToMenu(itemList, Support.ITEM);
    }

    @FXML
    public void menuSearch(MouseEvent event) throws SQLException {
        ItemService itemService = new ItemService();
        ComboService comboService = new ComboService();

        String contentSearch = menuSearchingContent.getText();
        ObservableList<Item> itemResultSearching = itemService.getAllItem_p(null, null, contentSearch, 0);
        ObservableList<Combo> comboResultSearching = comboService.getAllCombo_p(null, contentSearch, 0);

        if (!itemResultSearching.isEmpty()) {
            addItemToMenu(itemResultSearching, Support.SEARCH);
        }

        if (!comboResultSearching.isEmpty()) {
            addComboToMenu(comboResultSearching, Support.SEARCH);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int roleId = LoginController.getRoleId();

        if (roleId == Constant.CUSTOMER)
            btn_dashboard.setDisable(true);

        ItemService itemService = new ItemService();
        ObservableList<Item> itemList = null;
        try {
            itemList = itemService.getAllItem_p(null, null, null, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addItemToMenu(itemList, Support.ITEM);

    }

    //add item or combo to menu support sale
    private void addItemToMenu(ObservableList<Item> list, Support Support) {
        vbMainMenu.getChildren().clear();
        //add item into menu
        for (Item item : list) {
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

            //Giá KM
            HBox hBoxMinimumPrice = new HBox();
            hBoxMinimumPrice.setAlignment(Pos.CENTER_LEFT);
            ImageView imageViewMinimumPrice = new ImageView("/vendor/icon/icons8-tags-64.png");
            imageViewMinimumPrice.setFitWidth(24);
            imageViewMinimumPrice.setFitHeight(24);

            double sale = item.getPrice() - item.getSalePrice();
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

            //Sale
            int percent = item.getPercent();
            if (percent > 0) {
                HBox hBoxSale = new HBox();
                hBoxSale.setAlignment(Pos.CENTER_LEFT);
                ImageView imageSale = new ImageView("/vendor/icon/icons8-sale-48.png");
                imageSale.setFitWidth(24);
                imageSale.setFitHeight(24);
                Text tSale = new Text("Khuyến mãi " + percent + "%");
                tSale.getStyleClass().add("tSale");
                hBoxSale.getChildren().add(imageSale);
                hBoxSale.getChildren().add(tSale);
                vBox.getChildren().add(hBoxSale);
            }
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
                    addItemSelected(item, Support);
                }
            });

            hBoxButtonPrice.getChildren().add(btnPrice);

            hBox.getChildren().add(vBox);
            hBox.getChildren().add(hBoxButtonPrice);
            vbMainMenu.getChildren().add(hBox);
        }
    }

    private void addComboToMenu(ObservableList<Combo> list, Support Support) {
        vbMainMenu.getChildren().clear();
        //add item into menu
        for (Combo item : list) {
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

            HBox hBoxPrice = new HBox();
            hBoxPrice.setAlignment(Pos.CENTER_LEFT);
            ImageView imageViewPrice = new ImageView("/vendor/icon/icons8-expensive-64.png");
            imageViewPrice.setFitWidth(24);
            imageViewPrice.setFitHeight(24);

            //Giá gốc
            Text tPrice = new Text("Giá " + String.valueOf(item.getComboPrice()) + "k");
            tPrice.setFont(Font.font("Arial", 14));
            hBoxPrice.getChildren().add(imageViewPrice);
            hBoxPrice.getChildren().add(tPrice);

            hBoxIncludePrice.getChildren().add(hBoxPrice);
            vBox.getChildren().add(hBoxIncludePrice);

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
                    addComboSelected(item, Support);
                }
            });

            hBoxButtonPrice.getChildren().add(btnPrice);

            hBox.getChildren().add(vBox);
            hBox.getChildren().add(hBoxButtonPrice);
            vbMainMenu.getChildren().add(hBox);
        }
    }

    private void addItemSelected(Item item, Support Support) {
        String itemID = item.getId();

        if (!checkExist.containsKey(itemID)) {

            checkExist.put(itemID, 1);
            if (Support == Support.ITEM) {
                checkType.put(itemID, Constant.ITEM_TYPE);
            } else checkType.put(itemID, Constant.COMBO_TYPE);

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
                    removeItemFromListSelected(item, Support);
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

        double price = item.getPrice();
        totalPrice = totalPrice + price;

        if (Support == Support.ITEM) {
            salePrice = salePrice + item.getSalePrice();
        }

        String customerId = LoginController.getCustomerId();
        if (customerId != null) {
            salePrice = salePrice + totalPrice * 0.1;
        }

        setTextPrice(totalPrice, salePrice);
    }

    private void addComboSelected(Combo combo, Support Support) {
        String comboID = combo.getId();
        if (!checkExist.containsKey(comboID)) {

            checkExist.put(comboID, 1);
            if (Support == Support.ITEM) {
                checkType.put(comboID, Constant.ITEM_TYPE);
            } else checkType.put(comboID, Constant.COMBO_TYPE);

            HBox hbItemSelected = new HBox();
            hbItemSelected.setId("hb" + comboID);
            hbItemSelected.setAlignment(Pos.CENTER_LEFT);
            Text quantity = new Text("1-");
            quantity.setId("txt" + comboID);
            hbItemSelected.getChildren().add(quantity);
            Text itemName = new Text(combo.getName());
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
                    removeComboFromListSelected(combo, Support);
                }
            });

            hbButtonRemove.getChildren().add(btnRemove);

            hbItemSelected.getChildren().add(hbButtonRemove);

            vbItemSelected.getChildren().add(hbItemSelected);
        } else {
            int quantity = checkExist.get(comboID);

            //tìm text có id là #txt...
            Text text = (Text) vbItemSelected.getParent().lookup("#txt" + comboID);
            if (text != null) {
                text.setText(String.valueOf(quantity + 1) + "-");
                checkExist.put(comboID, quantity + 1);
            }
        }

        double price = combo.getComboPrice();
        totalPrice = totalPrice + price;

        String customerId = LoginController.getCustomerId();
        if (customerId != null) {
            salePrice = salePrice + totalPrice * 0.1;
        }

        setTextPrice(totalPrice, salePrice);
    }

    private void removeItemFromListSelected(Item item, Support Support) {
        String itemId = item.getId();
        double price = item.getPrice();
        int quantity = checkExist.get(itemId);

        checkRemove(itemId, quantity);

        String customerId = LoginController.getCustomerId();
        if (customerId != null) {
            salePrice = salePrice - totalPrice * 0.1;
        }

        totalPrice = totalPrice - price;

        if (Support == Support.ITEM) {
            salePrice = salePrice - item.getSalePrice();
        }
        setTextPrice(totalPrice, salePrice);
    }

    private void removeComboFromListSelected(Combo combo, Support Support) {
        String comboId = combo.getId();
        double price = combo.getComboPrice();
        int quantity = checkExist.get(comboId);

        checkRemove(comboId, quantity);

        String customerId = LoginController.getCustomerId();
        if (customerId != null) {
            salePrice = salePrice - totalPrice * 0.1;
        }

        totalPrice = totalPrice - price;
        setTextPrice(totalPrice, salePrice);
    }

    private void checkRemove(String id, int quantity){
        if (quantity - 1 > 0) {
            Text text = (Text) vbItemSelected.getParent().lookup("#txt" + id);
            text.setText(String.valueOf(quantity - 1) + "-");
            checkExist.put(id, quantity - 1);
        } else {
            HBox hbItemSelected = (HBox) vbItemSelected.getParent().lookup("#hb" + id);
            vbItemSelected.getChildren().remove(hbItemSelected);
            checkExist.remove(id);
            checkType.remove(id);
        }
    }

    private void setTextPrice(Double totalPrice, Double salePrice) {
        tf_TotalPrice.setText(String.valueOf(totalPrice));
        tf_SalePrice.setText(String.valueOf(salePrice));
        tf_PayPrice.setText(String.valueOf(totalPrice - salePrice));
    }

    private void showInvoiceDialog() throws IOException {
        String billDialogUrl = "/view/invoice/invoice.fxml";

        Parent billDialog = FXMLLoader.load(getClass().getResource(billDialogUrl));
        Scene scene = new Scene(billDialog);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
