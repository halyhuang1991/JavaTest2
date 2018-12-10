package com.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db.Helpers.*;

public class OraDb {
    public static ResultSet GetRs(String sql) {
        // 声明Connection对象
        Connection con;
        // 驱动程序名
        String driver = "oracle.jdbc.OracleDriver";
        // URL指向要访问的数据库名mydata
        String url = JsonTest.GetOrl("url");// "jdbc:oracle:thin:@localhost:1521:database";
        String user = JsonTest.GetOrl("user");// "root";
        String password = JsonTest.GetOrl("password");// "admin";
        // 遍历查询结果集
        ResultSet rs = null;
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // 2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            // 3.ResultSet类，用来存放获取的结果集！！
            rs = statement.executeQuery(sql);
            // while(rs.next()){
            // //获取stuname这列数据
            // job = rs.getString("job");
            // //获取stuid这列数据
            // id = rs.getString("ename");

            // //输出结果
            // System.out.println(id + "\t" + job);
            // }
            // con.close();
        } catch (ClassNotFoundException e) {
            // 数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            // 数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            System.out.println("数据库数据成功获取！！");
        }
        return rs;

    }

    private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ResultSetMetaData md = rs.getMetaData();// 获取键名
            int columnCount = md.getColumnCount();// 获取行的数量
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();// 声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));// 获取键名及值
                }
                list.add(rowData);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> GetLs(String sql) throws SQLException {
        return convertList(GetRs(sql));
    }

    public static void Test() {
        String sql;
        try {
            sql = "select * from edicsct";
            List<Map<String, Object>> ls = GetLs(sql);
            // for (Map<String, Object> map : ls) {
            // for (String s : map.keySet()) {
            // System.out.print("key:" + s + "\t");
            // System.out.println("value:" + map.get(s));
            // }
            // }
            for (Map<String, Object> map : ls) {
                System.out.println("--------------------------------------------------");
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    System.out.print(m.getKey() + "    ");
                    System.out.println(m.getValue());
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}