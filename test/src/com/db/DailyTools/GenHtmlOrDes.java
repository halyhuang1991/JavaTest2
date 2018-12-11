package com.db.DailyTools;

import java.util.*;

import com.db.mysql.OraDb;

public class GenHtmlOrDes {
    public static String GetDes(String cols, String[] tables) {
        String v_sql = "select a.column_name,b.comments,a.DATA_TYPE from  user_tab_columns a left join user_col_comments b on   a.Table_Name = b.Table_Name and a.Column_Name = b.Column_Name ";
        Integer i = 0;
        for (String str : tables) {
            if (i == 0) {
                v_sql += " where a.Table_Name = '" + str.toUpperCase() + "'";
            } else {
                v_sql += " or a.Table_Name = '" + str.toUpperCase() + "'";
            }
            i += 1;
        }
        try {
            List<Map<String, Object>> ls = OraDb.GetLs(v_sql);
            List<String> ls1 = new ArrayList<String>();
            String[] sarr = cols.split(",");
            for (String str : sarr) {
                Boolean ck = false;
                for (Map<String, Object> map : ls) {
                    String column_name = (String) map.get("COLUMN_NAME");
                    String comments = (String) map.get("COMMENTS");
                    if (str.toUpperCase().equals(column_name)) {
                        ls1.add(comments.trim());
                        ck = true;
                    }
                }
                if (!ck) {
                    ls1.add(str);
                }
            }
            String comments = String.join(",", ls1);
            System.out.println(comments);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void run() {
        String[] tables = new String[] { "PCFTECB" };
        String cols = "PRTYP,CSTORD";
        System.out.println(GetDes(cols, tables));
    }
}
