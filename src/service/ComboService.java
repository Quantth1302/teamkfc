package service;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Combo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComboService {

    public List<HashMap> getAllCombo(String id, String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "{ call combo_get_all(?, ?) }";
        CallableStatement stmt = connection.prepareCall(sql);

        stmt.setString("pComboId", id);
        stmt.setString("pName", name);
        ResultSet resultSet = stmt.executeQuery();
        List<HashMap> comboList = new ArrayList<>();
        while (resultSet.next()){
            HashMap combo = new HashMap();
            combo.put("id", resultSet.getString("id")); //do not change because this value is using in the same class
            combo.put("name", resultSet.getString("name")); //do not change because this value is using in the same class
            combo.put("limit", resultSet.getInt("limit"));
            combo.put("percent", resultSet.getInt("percent")); //do not change because this value is using in the same class
            combo.put("active", resultSet.getInt("active"));
            combo.put("price", resultSet.getDouble("combo_price")); //do not change because this value is using in the same class

            comboList.add(combo);
        }
        return comboList;
    }

    public ObservableList<Combo> getAllCombo_p(String id, String name) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "{ call combo_get_all(?, ?) }";
        CallableStatement stmt = connection.prepareCall(sql);

        stmt.setString("pComboId", id);
        stmt.setString("pName", name);
        ResultSet resultSet = stmt.executeQuery();
        ObservableList<Combo> comboList = FXCollections.observableArrayList();
        while (resultSet.next()){
            comboList.add(new Combo(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("limit"),
                    resultSet.getInt("percent"),
                    resultSet.getInt("active"),
                    resultSet.getDouble("combo_price")
            ));
        }
        return comboList;
    }
}
