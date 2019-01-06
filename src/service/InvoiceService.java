package service;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Invoice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InvoiceService {
    public List<HashMap> getInvoiceInfo(String invoiceId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "{ call invoice_get_info(?) }";

        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pInvoiceId", invoiceId);
        ResultSet resultSet = stmt.executeQuery();
        List<HashMap> invoiceInfo = new ArrayList<>();
        while (resultSet.next()){
            HashMap invoiceField = new HashMap();

            invoiceField.put("createdTime", resultSet.getDate("created_time"));
            invoiceField.put("itemId", resultSet.getString("item_id"));
            invoiceField.put("itemQuantity", resultSet.getInt("item_quantity"));
            invoiceField.put("comboId", resultSet.getString("combo_id"));
            invoiceField.put("comboQuantity", resultSet.getInt("combo_quantity"));
            invoiceField.put("itemName", resultSet.getString("item_name"));
            invoiceField.put("percent", resultSet.getInt("percent"));
            invoiceField.put("itemPrice", resultSet.getDouble("item_price"));
            invoiceField.put("comboName", resultSet.getString("combo_name"));
            invoiceField.put("comboPrice", resultSet.getDouble("combo_price"));
            invoiceField.put("totalPrice", resultSet.getDouble("total_price"));
            invoiceField.put("payPrice", resultSet.getDouble("pay_price"));

            invoiceInfo.add(invoiceField);
        }

        return invoiceInfo;
    }

    public ObservableList<Invoice> getAllInvoice_p(String id, String search, int p) throws SQLException {
        int limit = 10;
        int offset = p * limit;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "{ call invoice_get_all(?, ?, ?, ?) }";
        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pInvoiceId", id);
        stmt.setString("pSearch", search);
        stmt.setInt("pLimit", limit);
        stmt.setInt("pOffset", offset);

        ResultSet rs = stmt.executeQuery();
        ObservableList<Invoice> list = FXCollections.observableArrayList();
        while (rs.next()){
            list.add(new Invoice(
                    rs.getString("id"),
                    rs.getString("customer_id"),
                    rs.getString("employee_id"),
                    rs.getDate("created_time"),
                    rs.getDouble("total_price"),
                    rs.getDouble("pay_price")
            ));
        }
        return list;
    }
}
