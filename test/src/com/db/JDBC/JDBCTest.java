package com.db.JDBC;

import java.sql.*;

import com.db.Helpers.JsonTest;

public class JDBCTest {
    public static void test() {
        Connection con;
        String host = "";
        String user = "";
        String passwd = "";
        String DatabaseAndTable = "";// Database.Table
        try {
            host = JsonTest.GetAs400("host");
            user = JsonTest.GetAs400("user");
            passwd = JsonTest.GetAs400("password");
            con = JDBCConnection.getAS400Connection(host, user, passwd);
            Statement stmt = con.createStatement(1005, 1007);
            DatabaseAndTable = JsonTest.GetAs400("DatabaseAndTable");
            ResultSet rs = stmt.executeQuery("select * from " + DatabaseAndTable);// rs进行读取一次 判断是否有数据
            ResultSetMetaData rsd = rs.getMetaData();
            for(int i = 0; i < rsd.getColumnCount(); i++) {
              System.out.print("java className："+rsd.getColumnClassName(i + 1));
              System.out.print("  Type:"+rsd.getColumnTypeName(i + 1));
              System.out.print("  Column Name:"+rsd.getColumnName(i + 1));
              System.out.print("  Maxlength:"+rsd.getColumnDisplaySize(i + 1));
              System.out.print("  Scale:"+rsd.getScale(i + 1));
              System.out.print("  isNullable:"+rsd.isNullable(i + 1));
              System.out.println();
            }
            if ((rs.next()) && (!rs.wasNull())) {
                String CSTORD = rs.getString("CSTORD").trim();
                System.out.println(CSTORD);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}