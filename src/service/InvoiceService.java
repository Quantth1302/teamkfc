package service;

import database.DbConnection;

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
            invoiceField.put("id", resultSet.getString("id"));

            invoiceInfo.add(invoiceField);
        }

        return invoiceInfo;
    }
}
