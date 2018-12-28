package com.db.JDBC;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class JDBCConnection {
    public static Connection getOracleConnection(String localhost, String user, String password, String database) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + localhost + ":1521:" + database;
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getSQL2KConnection(String user, String password, String database) {
        return getSQL2KConnection("localhost", user, password, database);
    }

    public static Connection getSQL2KConnection(String localhost, String user, String password, String database) {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            String url = "jdbc:microsoft:sqlserver://" + localhost + ":1433;DatabaseName=" + database;
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getAccessConnection(String localhost) {
        return getAccessConnection(localhost, "admin", "");
    }

    public static Connection getAccessConnection(String localhost, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + localhost;

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getAS400Connection(String localhost, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            // Driver driver = (Driver)
            // Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
            // DriverManager.registerDriver(driver);
            conn = DriverManager.getConnection("jdbc:as400://" + localhost, user, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}