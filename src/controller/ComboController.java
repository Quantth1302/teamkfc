package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.Constant;
import library.Helper;
import library.Support;
import model.Combo;
import model.ComboDetail;
import model.Item;
import service.ComboService;
import service.ItemService;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ComboController implements Initializable {

    @FXML
    private VBox indexCombo;

    @FXML
    private VBox newComboVB;

    @FXML
    private VBox vbItemSelected;

    @FXML
    private Pagination comboPagination;

    @FXML
    private TextField tf_comboSearch;

    @FXML
    private TableView<Combo> comboTable;

    @FXML
    private TableColumn<Combo, String> col_comboId;

    @FXML
    private TableColumn<Combo, String> col_comboName;

    @FXML
    private TableColumn<Combo, Integer> col_comboLimit;

    @FXML
    private TableColumn<Combo, Double> col_comboPrice;

    @FXML
    private TableColumn<Combo, Integer> col_comboStatus;

    @FXML
    private TableColumn<Combo, Button> col_comboDelete;

    @FXML
    private TableColumn<Combo, Button> col_comboEdit;

    //new action

    @FXML
    private TextField tf_itemSearchContent;

    @FXML
    private TextField tf_Id;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_comboSale;

    @FXML
    private TextField tf_limit;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private Pagination itemPagination;

    @FXML
    private TableColumn<Item, String> col_itemId;

    @FXML
    private TableColumn<Item, String> col_itemName;

    @FXML
    private TableColumn<Item, String> col_itemType;

    @FXML
    private TableColumn<Item, String> col_itemCreator;

    @FXML
    private TableColumn<Item, Integer> col_itemSale;

    @FXML
    private TableColumn<Item, Double> col_itemPrice;

    @FXML
    private TableColumn<Item, Button> col_itemManage;

    Helper helper = new Helper();
    HashMap<String, Integer> checkExist = new HashMap<String, Integer>();

    private static Support action = IndexController.getAction();
    private static Combo comboLocal;
    private static Item itemLocal;
    private static String searchContent;
    private static String searchItemContent;

    private double totalPrice = 0.0;
    private double salePrice = 0.0;

    @FXML
    void addNewCombo(MouseEvent event) {
        action = Support.NEW_ACTION;
        comboLocal = null;
        String comboUrl = "/view/combo/new.fxml";
        helper.loadVBoxContent(comboUrl, indexCombo);
    }

    @FXML
    public void comboSearchAction(MouseEvent event) {
        ObservableList<Combo> comboList = null;
        ComboService itemService = new ComboService();

        String text = tf_comboSearch.getText();
        searchContent = text;
        indexAction();
    }

    @FXML
    public void itemSearchAction(MouseEvent event) {
        String text = tf_itemSearchContent.getText();
        searchItemContent = text;
        if (comboLocal != null){
            newAction(comboLocal);
        } else newAction(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (action == Support.COMBO_ACTION) {
            indexAction();
        }
        if (action == Support.NEW_ACTION) {
            newAction(null);
        } else if (action == Support.EDIT_ACTION) {
            newAction(comboLocal);
        }
    }

    private ObservableList<Combo> initData(int p, String text) {
        ObservableList<Combo> comboList = null;
        ComboService comboService = new ComboService();

        try {
            comboList = comboService.getAllCombo_p(null, text, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comboList;
    }

    private Node createPage(int p) {
        String text = null;
        if (searchContent != null) {
            text = searchContent;
        }
        ObservableList<Combo> comboList = initData(p, text);
        for (Combo combo : comboList) {
            combo.getEdit().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    comboLocal = combo;
                    editAction();
                }
            });

            combo.getDelete().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    comboLocal = combo;
                    deleteAction(combo);
                }
            });
        }
        comboTable.setItems(FXCollections.observableList(comboList));
        return comboTable;
    }

    private ObservableList<Item> initItemData(int p, String text) {
        ObservableList<Item> itemList = null;
        ItemService itemService = new ItemService();

        try {
            itemList = itemService.getAllItem_p(null, null, text, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    private Node createItemPage(int p) {
        String text = null;
        if (searchItemContent != null) {
            text = searchItemContent;
        }
        ObservableList<Item> itemList = initItemData(p, text);
        for (Item item : itemList) {
            item.getAdd().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    addItemSelected(item);
                }
            });
        }
        itemTable.setItems(FXCollections.observableList(itemList));
        return itemTable;
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
    }

    private void removeItemFromListSelected(Item item){
        String itemId = item.getId();
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
    }

    private void indexAction() {
        Connection connection = DbConnection.getInstance().getConnection();
        int count = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from combo");
            rs.first();
            count = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_comboId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_comboName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_comboLimit.setCellValueFactory(new PropertyValueFactory<>("limit"));
        col_comboPrice.setCellValueFactory(new PropertyValueFactory<>("comboPrice"));
        col_comboStatus.setCellValueFactory(new PropertyValueFactory<>("active"));
        col_comboEdit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        col_comboDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        int pageCount = (count / 10) + 1;
        comboPagination.setPageCount(pageCount);

        comboPagination.setPageFactory(this::createPage);
    }

    private void newAction(Combo combo) {
        Connection connection = DbConnection.getInstance().getConnection();
        int count = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from item");
            rs.first();
            count = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_itemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_itemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_itemType.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        col_itemCreator.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        col_itemSale.setCellValueFactory(new PropertyValueFactory<>("percent"));
        col_itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_itemManage.setCellValueFactory(new PropertyValueFactory<>("add"));

        int pageCount = (count / 10) + 1;
        itemPagination.setPageCount(pageCount);

        itemPagination.setPageFactory(this::createItemPage);
        if (action == Support.EDIT_ACTION){
            tf_Id.setText(combo.getId());
            tf_name.setText(combo.getName());
            tf_comboSale.setText(String.valueOf(combo.getPercent()));
            tf_limit.setText(String.valueOf(combo.getLimit()));

            ComboService comboService = new ComboService();
            ItemService itemService = new ItemService();
            List<ComboDetail> comboDetailList = new ArrayList<>();
            try {
                comboDetailList = comboService.getAllComboDetail(combo.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (checkExist.isEmpty()){
                for (ComboDetail comboDetail : comboDetailList) {
                    String itemId = comboDetail.getItemId();
                    Item item;
                    try {
                        ObservableList<Item> items = itemService.getAllItem_p(itemId, null, null, 0);
                        item = items.get(0);
                        addItemSelected(item);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            } else checkExist.clear();
        }
        action = Support.COMBO_ACTION;
    }

    private void editAction() {
        action = Support.EDIT_ACTION;
        String url = "/view/combo/new.fxml";
        helper.loadVBoxContent(url, indexCombo);
    }

    private void deleteAction(Combo combo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Combo");
        alert.setHeaderText("Are you sure want to delete this Combo?");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK){
            Connection connection = DbConnection.getInstance().getConnection();
            try {
                String sql = null;
                Statement stmt = connection.createStatement();
                sql = "delete from `combo_detail` where combo_id='" + combo.getId() +"'";
                stmt.executeUpdate(sql);
                sql = "delete from `combo` where id='" + combo.getId() +"'";
                stmt.executeUpdate(sql);
                System.out.println("Delete successfully!");

                helper.showMessageSuccessfully();

                indexAction();
            } catch (SQLException e) {
                helper.showMessageFail();
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void close(MouseEvent event) {
        action = Support.COMBO_ACTION;
        String itemUrl = "/view/combo/index.fxml";
        helper.loadVBoxContent(itemUrl, newComboVB);
    }

    @FXML
    public void save(MouseEvent event) {
        String comboId = tf_Id.getText();
        String comboName = tf_name.getText();
        int comboSale = Integer.parseInt(tf_comboSale.getText());
        int limit = Integer.parseInt(tf_limit.getText());

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = null;

            if (comboLocal != null){
                sql = "UPDATE `combo` " +
                        "SET `id` ='" + comboId + "', `name` = '"+ comboName +"', `limit` = '" + limit + "', `percent`='" + comboSale + "', " +
                        "`active`='1' where `id`='"+ comboId +"'";
            } else {
                sql = "INSERT INTO combo values " +
                        "('" + comboId + "', '"+ comboName +"', '" + limit + "', '" + comboSale + "', '1')";
            }
            stmt.executeUpdate(sql);

            for (HashMap.Entry<String, Integer> selected : checkExist.entrySet()) {
                String iId = selected.getKey();
                sql = "INSERT INTO combo_detail " +
                            "VALUES ('" + iId + "', '" + comboId + "' , '" + selected.getValue() + "')";
                stmt.executeUpdate(sql);

            }
            stmt.close();
            helper.showMessageSuccessfully();
            System.out.println("save successfully!");
        } catch (SQLException e) {
            helper.showMessageFail();
            e.printStackTrace();
        }

        String comboUrl = "/view/combo/index.fxml";
        helper.loadVBoxContent(comboUrl, newComboVB);
    }
}
