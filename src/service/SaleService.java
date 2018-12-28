package service;

import database.DbConnection;
import javafx.scene.control.Button;
import model.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

    public List<Sale> getAllSale_p(String id, String searchContent, int p) throws SQLException {
        int limit = 10;
        int offset = p * limit;

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "{ call sale_get_all(?, ?, ?, ?) }";
        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pId", id);
        stmt.setString("pSearch", searchContent);
        stmt.setInt("pLimit", limit);
        stmt.setInt("pOffset", offset);


        ResultSet rs = stmt.executeQuery();
        List<Sale> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Sale(
                    rs.getString("id"),
                    rs.getInt("percent"),
                    rs.getDate("started_time"),
                    rs.getDate("end_time"),
                    rs.getString("name"),
                    new Button("edit"),
                    new Button("delete")
            ));
        }
        return list;
    }
}
