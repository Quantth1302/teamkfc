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
import library.Helper;
import library.Support;
import model.ComboDetail;
import model.Item;
import model.Sale;
import service.ItemService;
import service.SaleService;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SaleController implements Initializable {

    @FXML
    private VBox indexSale;

    @FXML
    private VBox newSaleVB;

    @FXML
    private VBox vbItemSelected;

    @FXML
    private TextField tf_saleSearch;

    @FXML
    private Pagination salePagination;

    @FXML
    private TableView<Sale> saleTable;

    @FXML
    private TableColumn<Sale, Integer> col_saleId;

    @FXML
    private TableColumn<Sale, String> col_saleName;

    @FXML
    private TableColumn<Sale, Integer> col_salePercent;

    @FXML
    private TableColumn<Sale, String> col_startedTime;

    @FXML
    private TableColumn<Sale, String> col_endTime;

    @FXML
    private TableColumn<Sale, Button> col_edit;

    @FXML
    private TableColumn<Sale, Button> col_delete;


    //item

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_saleName;

    @FXML
    private TextField tf_salePercent;

    @FXML
    private DatePicker dp_startedTime;

    @FXML
    private DatePicker dp_endTime;

    @FXML
    private TableView<Item> itemTable;

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

    @FXML
    private Pagination itemPagination;

    Helper helper = new Helper();

    HashMap<String, Integer> checkExist = new HashMap<String, Integer>();

    private static Support action = IndexController.getAction();
    private static Sale saleLocal;
    private static Item itemLocal;
    private static String searchContent;
    private static String searchItemContent;

    private double totalPrice = 0.0;
    private double salePrice = 0.0;

    @FXML
    void addNewSale(MouseEvent event) {
        action = Support.NEW_ACTION;
        saleLocal = null;
        String saleUrl = "/view/sale/new.fxml";
        helper.loadVBoxContent(saleUrl, indexSale);
    }

    @FXML
    void saleSearchAction(MouseEvent event) {
        List<Sale> saleList = null;
        SaleService saleService = new SaleService();

        String text = tf_saleSearch.getText();
        searchContent = text;
        indexAction();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (action == Support.SALE_ACTION) {
            indexAction();
        }
        if (action == Support.NEW_ACTION) {
            newAction(null);
        } else if (action == Support.EDIT_ACTION) {
            newAction(saleLocal);
        }
    }

    private List<Sale> initData(int p, String text) {
        List<Sale> saleList = null;
        SaleService saleService = new SaleService();

        try {
            saleList = saleService.getAllSale_p(null, text, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleList;
    }

    private Node createPage(int p) {
        String text = null;
        if (searchContent != null) {
            text = searchContent;
        }
        List<Sale> saleList = initData(p, text);
        for (Sale sale : saleList) {
            sale.getEdit().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    saleLocal = sale;
                    editAction();
                }
            });

            sale.getDelete().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    saleLocal = sale;
                    deleteAction(sale);
                }
            });
        }
        saleTable.setItems(FXCollections.observableList(saleList));
        return saleTable;
    }

    private void indexAction() {
        Connection connection = DbConnection.getInstance().getConnection();
        int count = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from sale");
            rs.first();
            count = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_saleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_saleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_salePercent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        col_startedTime.setCellValueFactory(new PropertyValueFactory<>("startedTime"));
        col_endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        col_edit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        int pageCount = (count / 10) + 1;
        salePagination.setPageCount(pageCount);

        salePagination.setPageFactory(this::createPage);
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

    private void newAction(Sale sale) {
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
            tf_id.setText(sale.getId());
            tf_saleName.setText(sale.getName());
            tf_salePercent.setText(String.valueOf(sale.getPercent()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date startedTime = sale.getStartedTime();
            String startedTimeStr = String.valueOf(startedTime);
            LocalDate start = LocalDate.parse(startedTimeStr, formatter);
            dp_startedTime.setValue(start);


            Date endTime = sale.getEndTime();
            String endTimeStr = String.valueOf(endTime);
            LocalDate end = LocalDate.parse(endTimeStr, formatter);
            dp_endTime.setValue(end);

            ItemService itemService = new ItemService();

            ObservableList<Item> itemList  = FXCollections.observableArrayList();
            try {
                itemList = itemService.getAllItem_p(null, sale.getId(), null, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (checkExist.isEmpty()){
                for (Item item : itemList) {
                    addItemSelected(item);
                }

            } else checkExist.clear();
        }
        action = Support.SALE_ACTION;
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

    private void editAction() {
        action = Support.EDIT_ACTION;
        String url = "/view/sale/new.fxml";
        helper.loadVBoxContent(url, indexSale);
    }

    private void deleteAction(Sale sale) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete sale");
        alert.setHeaderText("Are you sure want to delete this sale?");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK){
            Connection connection = DbConnection.getInstance().getConnection();
            ItemService itemService = new ItemService();

            ObservableList<Item> itemList  = FXCollections.observableArrayList();
            try {
                itemList = itemService.getAllItem_p(null, sale.getId(), null, 0);
                String sql = null;
                Statement stmt = connection.createStatement();
                for (Item item: itemList) {
                    if (item.getSaleId() != null){
                        sql = "UPDATE `item` SET `sale_id`=NULL";
                        stmt.executeUpdate(sql);
                    }
                }
                sql = "delete from `sale` where id='" + sale.getId() +"'";
                stmt.executeUpdate(sql);
                System.out.println("Delete successfully!");
                indexAction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void close(MouseEvent event) {
        action = Support.SALE_ACTION;
        String itemUrl = "/view/sale/index.fxml";
        helper.loadVBoxContent(itemUrl, newSaleVB);
    }

    @FXML
    void save(MouseEvent event) {
        String saleId = tf_id.getText();
        String saleName = tf_saleName.getText();
        int salePercent = Integer.parseInt(tf_salePercent.getText());
        LocalDate start = dp_startedTime.getValue();
        Date startedTime = java.sql.Date.valueOf(start);
        LocalDate end = dp_endTime.getValue();
        Date endTime = java.sql.Date.valueOf(end);

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = null;

            if (saleLocal != null){
                sql = "UPDATE `sale` " +
                        "SET `id` ='" + saleId + "', `percent` = '"+ salePercent +"', `started_time` = '" + startedTime + "', `end_time`='" + endTime + "', " +
                        "`name`='"+ saleName +"' where `id`='"+ saleId +"'";
            } else {
                sql = "INSERT INTO sale values " +
                        "('" + saleId + "', '"+ salePercent +"', '" + startedTime + "', '" + endTime + "', '"+ saleName +"')";
            }
            stmt.executeUpdate(sql);

            for (HashMap.Entry<String, Integer> selected : checkExist.entrySet()) {
                String iId = selected.getKey();
                sql = "UPDATE `item` SET `sale_id`='"+ saleId +"' where `id` ='" + iId + "'";
                stmt.executeUpdate(sql);
            }
            stmt.close();
            System.out.println("save successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String comboUrl = "/view/sale/index.fxml";
        helper.loadVBoxContent(comboUrl, newSaleVB);
    }
}
