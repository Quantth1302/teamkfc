package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection  {
    private DbConnection(){

    }

    public static DbConnection getInstance(){
        return new DbConnection();
    }

    public Connection getConnection(){
        String connectionString = "jdbc:mysql://127.0.0.1:3306/doancsdl?useUnicode=yes&characterEncoding=UTF-8";
        String userName = "quannv";
        String password = "quan1998";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.jdbc.Driver
            connection = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
