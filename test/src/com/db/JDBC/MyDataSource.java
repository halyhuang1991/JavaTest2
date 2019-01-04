package com.db.JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.activation.DataSource;

import com.db.Helpers.JsonTest;

public class MyDataSource implements DataSource {
    private Connection conn;

    // 1.创建一个容器存放连接对象Connection
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    // 循环为pool添加5个连接对象

    static {
        for (int i = 0; i < 5; i++) {
            String host = JsonTest.GetAs400("host");
            String user = JsonTest.GetAs400("user");
            String passwd = JsonTest.GetAs400("password");
            Connection conn = JDBCConnection.getAS400Connection(host, user, passwd);
            pool.add(conn);
        }

    }

    public MyDataSource() {

    }

    public MyDataSource(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                String host = JsonTest.GetAs400("host");
                String user = JsonTest.GetAs400("user");
                String passwd = JsonTest.GetAs400("password");
                Connection conn = JDBCConnection.getAS400Connection(host, user, passwd);
                pool.add(conn);
            }
        }
        conn = pool.removeFirst();
        return conn;
    }

    public void backConnection(Connection conn) {
        pool.add(conn);
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    // @Override
    // public PrintWriter getLogWriter() throws SQLException {

    // return null;
    // }

    // @Override
    // public int getLoginTimeout() throws SQLException {

    // return 0;
    // }

    // @Override
    // public Logger getParentLogger() throws SQLFeatureNotSupportedException {

    // return null;
    // }

    // @Override
    // public void setLogWriter(PrintWriter arg0) throws SQLException {

    // }

    // @Override
    // public void setLoginTimeout(int arg0) throws SQLException {

    // }

    // @Override
    // public boolean isWrapperFor(Class<?> arg0) throws SQLException {

    // return false;
    // }

    // @Override
    // public <T> T unwrap(Class<T> arg0) throws SQLException {

    // return null;
    // }

    // @Override
    // public Connection getConnection(String arg0, String arg1) throws SQLException
    // {

    // return null;
    // }
}