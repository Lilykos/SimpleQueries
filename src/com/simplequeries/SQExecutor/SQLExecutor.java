package com.simplequeries.SQExecutor;

import com.simplequeries.SQConnection.Connection;
import com.simplequeries.SQResults.SQResultSet;
import com.simplequeries.SQResults.SQResultSetCreator;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLExecutor {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static java.sql.Connection connect(Connection connection) throws ClassNotFoundException, SQLException {

        // Needs JDBC driver added to the build path to work!

        Class.forName(JDBC_DRIVER);
        return DriverManager.getConnection("jdbc:mysql://"
                + connection.getUrl() + "/"
                + connection.getDb() + "?"
                + "user=" + connection.getUsername()
                + "&password=" + connection.getPasswd());
    }

    public static SQResultSet execute(String sql, Connection connection) throws ClassNotFoundException {
        java.sql.Connection connect = null;
        Statement statement = null;

        try {
            connect = connect(connection);
            statement = connect.createStatement();

            ResultSet results = statement.executeQuery(sql);
            return SQResultSetCreator.createResultSet(results);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}