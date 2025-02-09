package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper
{
    public Connection getConnection() throws SQLException
    {
        final String userName = "root";
        final String password = "010420";
        final String dbUrl = "jdbc:mysql://localhost:3306/library";
        return DriverManager.getConnection(dbUrl,userName,password);
    }

    public void showErrorMessage(SQLException exception)
    {
        System.out.println("Error : "+exception.getMessage());
        System.out.println("Error code : "+exception.getErrorCode());
    }
}