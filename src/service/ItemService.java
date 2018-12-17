package service;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemService {

    public List<HashMap> getAllItem(String id, String isSale, String name) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "{ call item_get_all(?, ?, ?) }";

        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pItemId", id);
        stmt.setString("pSale", isSale);
        stmt.setString("pName", name);

        ResultSet resultSet = stmt.executeQuery();
        List<HashMap> itemList = new ArrayList<>();
        while (resultSet.next()){
            HashMap item = new HashMap();
            item.put("id", resultSet.getString("id")); //do not change because this value is using in the same class
            item.put("employeeId", resultSet.getInt("employee_id"));
            item.put("price", resultSet.getDouble("price")); //do not change because this value is using in the same class
            item.put("itemTypeId", resultSet.getInt("item_type_id"));
            item.put("saleId", resultSet.getInt("sale_id"));
            item.put("limit", resultSet.getInt("limit"));
            item.put("name", resultSet.getString("name")); //do not change because this value is using in the same class
            item.put("percent", resultSet.getInt("percent"));
            item.put("typeName", resultSet.getString("type_name"));
            item.put("salePrice", resultSet.getDouble("sale_price"));

            itemList.add(item);
        }

        return itemList;
    }

    public ObservableList<Item> getAllItem_p(String id, String isSale, String name) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "{ call item_get_all(?, ?, ?) }";

        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pItemId", id);
        stmt.setString("pSale", isSale);
        stmt.setString("pName", name);

        ResultSet resultSet = stmt.executeQuery();
        ObservableList<Item> list  = FXCollections.observableArrayList();

        while (resultSet.next()){
            list.add(new Item(
                    resultSet.getString("id"),
                    resultSet.getString("employee_id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("item_type_id"),
                    resultSet.getInt("sale_id"),
                    resultSet.getInt("limit"),
                    resultSet.getString("name"),
                    resultSet.getInt("percent"),
                    resultSet.getString("type_name"),
                    resultSet.getDouble("sale_price"),
                    new Button("edit"),
                    new Button("delete")
            ));
        }

        return list;
    }
}
