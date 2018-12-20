package controller;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import library.Helper;
import javafx.scene.layout.VBox;
import library.Support;
import model.Item;
import model.ItemType;
import model.Sale;
import service.ItemService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private VBox indexItem;

    @FXML
    private VBox newItemVB;

    @FXML
    private Pagination itemPagination;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, String> col_id;

    @FXML
    private TableColumn<Item, String> col_name;

    @FXML
    private TableColumn<Item, String> col_typeName;

    @FXML
    private TableColumn<Item, String> col_employeeName;

    @FXML
    private TableColumn<Item, Integer> col_sale;

    @FXML
    private TableColumn<Item, Double> col_price;

    @FXML
    private TableColumn<Item, Button> col_edit;

    @FXML
    private TableColumn<Item, Button> col_delete;


    ///new action
    @FXML
    private TextField tf_itemId;

    @FXML
    private TextField tf_itemName;

    @FXML
    private ComboBox<ItemType> cb_itemType;

    @FXML
    private TextField tf_itemPrice;

    @FXML
    private TextField tf_limit;

    @FXML
    private ComboBox<Sale> cb_sale;

    @FXML
    private TextField tf_itemSearch;

    Helper helper = new Helper();

    private static Support action = IndexController.getAction();
    private static Item itemLocal;
    private static String searchContent;

    @FXML
    public void addNewItem(MouseEvent event) {
        action = Support.NEW_ITEM_ACTION;
        String itemUrl = "/view/item/new.fxml";
        helper.loadVBoxContent(itemUrl, indexItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (action == Support.ITEM_ACTION) {
            indexAction();
        }

        if (action == Support.NEW_ITEM_ACTION) {
            newAction(null);
        } else if (action == Support.EDIT_ACTION){
            newAction(itemLocal);
        }
    }

    @FXML
    public void itemSearchAction(MouseEvent event) {
        ObservableList<Item> itemList = null;
        ItemService itemService = new ItemService();

        String text = tf_itemSearch.getText();
        searchContent = text;
        indexAction();
    }

    private ObservableList<Item> initData(int p, String text){
        ObservableList<Item> itemList = null;
        ItemService itemService = new ItemService();

        try {
            itemList = itemService.getAllItem_p(null, null, text, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    private Node createPage(int p){
        String text = null;
        if (searchContent != null){
            text = searchContent;
        }
        ObservableList<Item> itemList = initData(p, text);
        for (Item item : itemList) {
            item.getEdit().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    itemLocal = item;
                    editAction();
                }
            });

            item.getDelete().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    itemLocal = item;
                    deleteAction(item);
                }
            });
        }
        itemTable.setItems(FXCollections.observableList(itemList));
        return itemTable;
    }

    private void indexAction() {

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
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));
        col_employeeName.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        col_sale.setCellValueFactory(new PropertyValueFactory<>("percent"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_edit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        int pageCount = (count / 10) + 1;
        itemPagination.setPageCount(pageCount);

        itemPagination.setPageFactory(this::createPage);
    }

    private void newAction(Item item) {
        ObservableList<ItemType> itemTypeList = FXCollections.observableArrayList();
        ObservableList<Sale> saleList = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from item_type");

            while (resultSet.next()) {
                itemTypeList.add(new ItemType(resultSet.getInt("id"), resultSet.getString("name")));
            }

            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cb_itemType.setCellFactory(new Callback<ListView<ItemType>, ListCell<ItemType>>() {
            @Override
            public ListCell<ItemType> call(ListView<ItemType> itemTypeListView) {
                final ListCell<ItemType> cell = new ListCell<ItemType>() {
                    @Override
                    protected void updateItem(ItemType itemType, boolean bln) {
                        super.updateItem(itemType, bln);

                        if (itemType != null) {
                            setText(itemType.getName());
                        } else setText(null);
                    }
                };
                return cell;
            }
        });
        cb_itemType.setButtonCell(new ListCell<ItemType>() {
            @Override
            protected void updateItem(ItemType itemType, boolean bln) {
                super.updateItem(itemType, bln);
                if (itemType != null) {
                    setText(itemType.getName());
                } else setText(null);
            }
        });
        cb_itemType.setItems(itemTypeList);


        //combo box sale

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from sale");

            while (resultSet.next()) {
                saleList.add(new Sale(
                                resultSet.getInt("id"),
                                resultSet.getInt("percent"),
                                resultSet.getDate("started_time"),
                                resultSet.getDate("end_time"),
                                resultSet.getString("name")
                        )
                );
            }

            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cb_sale.setCellFactory(new Callback<ListView<Sale>, ListCell<Sale>>() {
            @Override
            public ListCell<Sale> call(ListView<Sale> itemTypeListView) {
                final ListCell<Sale> cell = new ListCell<Sale>() {
                    @Override
                    protected void updateItem(Sale sale, boolean bln) {
                        super.updateItem(sale, bln);

                        if (sale != null) {
                            setText(sale.getName() + " " + sale.getPercent() + "%");
                        } else setText(null);
                    }
                };
                return cell;
            }
        });
        cb_sale.setButtonCell(new ListCell<Sale>() {
            @Override
            protected void updateItem(Sale sale, boolean bln) {
                super.updateItem(sale, bln);
                if (sale != null) {
                    setText(sale.getName() + " " + sale.getPercent() + "%");
                } else setText(null);
            }
        });
        cb_sale.setItems(saleList);

        //edit item
        if (action == Support.EDIT_ACTION){
            tf_itemId.setText(item.getId());
            tf_itemName.setText(item.getName());
            tf_itemPrice.setText(String.valueOf(item.getPrice()));
            tf_limit.setText(String.valueOf(item.getLimit()));
            ItemType itemType = null;
            Sale sale = null;

            try {
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("select * from item_type where id = "+ item.getItemTypeId());

                while (resultSet.next()) {
                    itemType = new ItemType(resultSet.getInt("id"), resultSet.getString("name"));
                }
                resultSet.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (ItemType type : cb_itemType.getItems()){
                if (itemType.getName().equals(type.getName())){
                    cb_itemType.getSelectionModel().select(type);
                    break;
                }
            }

            try {
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery("select * from sale where id=" + item.getSaleId());

                while (resultSet.next()) {
                    sale = new Sale(
                                    resultSet.getInt("id"),
                                    resultSet.getInt("percent"),
                                    resultSet.getDate("started_time"),
                                    resultSet.getDate("end_time"),
                                    resultSet.getString("name")
                    );
                }
                resultSet.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (sale != null){
                for (Sale s : cb_sale.getItems()){
                    if (sale.getName().equals(s.getName())){
                        cb_sale.getSelectionModel().select(s);
                        break;
                    }
                }
            }
        } else itemLocal = null;
        action = Support.ITEM_ACTION;
    }

    public void editAction() {
        action = Support.EDIT_ACTION;
        String itemUrl = "/view/item/new.fxml";
        helper.loadVBoxContent(itemUrl, indexItem);
    }

    private void deleteAction(Item item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure want to delete this item?");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK){
            Connection connection = DbConnection.getInstance().getConnection();
            try {
                Statement stmt = connection.createStatement();
                String sql = "delete from `item` where id='" + item.getId() +"'";
                stmt.executeUpdate(sql);
                System.out.println("Delete successfully!");
                indexAction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //new action
    @FXML
    public void close(MouseEvent event) {
        action = Support.ITEM_ACTION;
        String itemUrl = "/view/item/index.fxml";
        helper.loadVBoxContent(itemUrl, newItemVB);
    }

    @FXML
    public void save(MouseEvent event) {
        String itemId = tf_itemId.getText();
        String itemName = tf_itemName.getText();
        double price = Double.parseDouble(tf_itemPrice.getText());
        int limit = Integer.parseInt(tf_limit.getText());

        int itemTypeId = cb_itemType.getValue().getId();
        int saleId = cb_sale.getValue().getId();

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = null;

            if (itemLocal != null){
                sql = "UPDATE `item` " +
                        "SET `id` ='" + itemId + "', `employee_id` = '3', `price` = '" + price + "', `item_type_id`='" + itemTypeId + "', " +
                        "`sale_id`='" + saleId + "',`limit`='" + limit + "', `name` ='" + itemName + "' " +
                        "where `id`='"+ itemId +"'";
            } else {
                sql = "INSERT INTO item values " +
                        "('" + itemId + "', '3', '" + price + "', '" + itemTypeId + "', '" + saleId + "','" + limit + "', '" + itemName + "')";
            }
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("save successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String itemUrl = "/view/item/index.fxml";
        helper.loadVBoxContent(itemUrl, newItemVB);
    }
}
