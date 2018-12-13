package com.db.DailyTools;

import java.util.*;

import com.db.Helpers.StringUtil;
import com.db.mysql.OraDb;

public class GenHtmlOrDes {
    public static String GetDes(String cols, String[] tables) {
        cols = cols.toUpperCase();
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

    public static String GetHtml(String cols, String[] tables, Integer count) {
        cols = cols.toUpperCase();

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
            String DialogRow = "<div class=\"Dialog-Row\">\r\n%1$s\r\n<div class=\"clear\">\r\n</div>\r\n</ div > ";
            String DialogColumn = " <div class=\"Dialog-Column\">\r\n<div class=\"title\">\r\n<span><%%=GetFunText(\"lab%1$s\",\"%2$s\") %%></span>\r\n</div>\r\n<div class=\"input\">\r\n<input type=\"text\" id=\"txt%1$s\"  %2$s />\r\n</div><div class=\"clear\"></div>\r\n</div>";
            String[] sarr = cols.split(",");
            i = 1;
            String rows = "";
            for (String str : sarr) {
                if (StringUtil.IsNullOrEmpty(str))
                    continue;
                Boolean ck = false;
                String column_name = "";
                String comments = "";
                String DATA_TYPE = "";
                for (Map<String, Object> map : ls) {
                    String column_name1 = (String) map.get("COLUMN_NAME");
                    if (str.toUpperCase().equals(column_name1)) {
                        column_name = (String) map.get("COLUMN_NAME");
                        comments = (String) map.get("COMMENTS");
                        DATA_TYPE = (String) map.get("DATA_TYPE");
                        ck = true;
                    }
                }
                if (!ck) {
                    column_name = str;
                    comments = str;
                    DATA_TYPE = "";
                }
                String OneCol = String.format(DialogColumn, column_name, comments, "");
                if (DATA_TYPE.equals("DATE")) {
                    OneCol = String.format(DialogColumn, column_name, comments, "class='calendar'");
                }
                if (i % count == 0) {
                    rows += OneCol + "\r\n";
                    String one = String.format(DialogRow, rows);
                    ls1.add(one);
                    rows = "";
                } else {
                    rows += OneCol + "\r\n";
                }
                i++;

            }
            if (!"".equals(rows)) {
                String one = String.format(DialogRow, rows);
                ls1.add(one);
            }
            String comments = String.join(",", ls1);
            System.out.println(comments);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static void run() {
        String cols = "sug,ovy,kcol,lcol,skeyp,itramn,itramx,ordqty";
        String[] tables = new String[] { "phfhut" };
        if (cols.equals(""))
            return;
        // System.out.println(GetDes(cols, tables));
        System.out.println(GetHtml(cols, tables, 2));

    }
}
