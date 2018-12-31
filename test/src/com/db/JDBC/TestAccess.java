package com.db.JDBC;

import java.sql.*;

public class TestAccess {
    public static void test() {
        Connection con = JDBCConnection.getUcanAccessConnection("E:\\bgtemp\\ProtexWrk.mdb");
        // JDBCConnection.getAccessConnection("E:\\bgtemp\\ProtexWrk.mdb");
        // JDK1.8已经没有rt.jar
        try {
            Statement stmt = con.createStatement(1005, 1007);
            ResultSet rs = stmt.executeQuery("select * from BMW001X1");
            while ((rs.next()) && (!rs.wasNull())) {
                String SUG = rs.getString("SUG").trim();
                System.out.println(SUG);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}