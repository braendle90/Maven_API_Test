package Mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static String dbhost = "jdbc:mysql://localhost:3306/student?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root123@";
    private static Connection conn;

    // jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC

    @SuppressWarnings("finally")
    public static Connection createNewDBconnection() {

        System.out.println("Try connection");


        try  {
            conn = DriverManager.getConnection(
                    dbhost, username, password);
        } catch (SQLException e) {
            System.out.println("Cannot create database connection");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }
}