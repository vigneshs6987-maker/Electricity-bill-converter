import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/electricdb";
    private static final String USER = "root";
    private static final String PASS = "Vicky@2005";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con;
    }
}
