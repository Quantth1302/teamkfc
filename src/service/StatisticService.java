package service;

import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Statistic;

import java.sql.*;

public class StatisticService {

    private static double t;
    private static int q;

    public static double getTotal(){
        return t;
    }
    public static int getQuantity(){
        return q;
    }

    public ObservableList<Statistic> getAllStatistic_p(String name, String saleId,
                                                       Date time, int choice, String month, String year, int p) throws SQLException {
        int limit = 10;
        int offset = p * limit;

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "{ call revenue_get_all(?, ?, ? , ?, ?, ?, ?, ?, ?, ?) }";
        CallableStatement stmt = connection.prepareCall(sql);
        stmt.setString("pName", name);
        stmt.setString("pSale", saleId);
        stmt.setDate("pTime", time);
        stmt.setInt("pChoice", choice);
        stmt.setString("pMonth", month);
        stmt.setString("pYear", year);
        stmt.setInt("pLimit", limit);
        stmt.setInt("pOffset", offset);

        stmt.registerOutParameter("oTotal", Types.DOUBLE);
        stmt.registerOutParameter("oQuantity", Types.INTEGER);

        ResultSet rs = stmt.executeQuery();

        Double total = stmt.getDouble("oTotal");
        t = total;
        int quantity = stmt.getInt("oQuantity");
        q = quantity;
        ObservableList<Statistic> statistics = FXCollections.observableArrayList();
        while (rs.next()) {
            statistics.add(new Statistic(
                    rs.getString("eName"),
                    rs.getDate("created_time"),
                    rs.getString("item_id"),
                    rs.getInt("sItemQuantity"),
                    rs.getString("item_name"),
                    rs.getString("saleName"),
                    rs.getDouble("sItemPrice"),
                    rs.getString("combo_id"),
                    rs.getInt("sComboQuantity"),
                    rs.getString("combo_name"),
                    rs.getDouble("sComboPrice")
            ));
        }
        return statistics;
    }
}
