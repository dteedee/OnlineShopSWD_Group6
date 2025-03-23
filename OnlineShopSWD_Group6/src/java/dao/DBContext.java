package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    public static Connection makeConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OSSDB", "root", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    protected Connection connection;

    public DBContext() {
        try {
            String user = "root";
            String pass = "1234";
            String url = "jdbc:mysql://localhost:3306/OSSDB";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println(makeConnection());
    }
}
