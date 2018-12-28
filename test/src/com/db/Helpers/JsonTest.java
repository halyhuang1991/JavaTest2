package com.db.Helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonTest {
    public static void Test() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
        String path = "D:\\C\\github\\JavaTest2\\test\\src\\com\\db\\Helpers\\test.json";
        JsonParser parser = new JsonParser(); // 创建JSON解析器
        JsonObject object = (JsonObject) parser.parse(new FileReader(path)); // 创建JsonObject对象
        System.out.println("cat=" + object.get("cat").getAsString());
        JsonArray array = object.get("language").getAsJsonArray(); // 得到为json的数组
        for (int i = 0; i < array.size(); i++) {
            System.out.println("---------------");
            JsonObject subObject = array.get(i).getAsJsonObject();
            System.out.println("ide=" + subObject.get("ide").getAsString());

        }
    }

    public static String GetOrl(String option) {
        try {
            String path = "E:\\web\\Setting.json";
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(new FileReader(path));
            JsonObject subObject = object.get("OracleConnectString").getAsJsonObject();
            return subObject.get(option).getAsString();
        } catch (Exception e) {

            return "";
        }
    }

    public static String GetDB2(String option) {
        try {
            String path = "E:\\web\\Setting.json";
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(new FileReader(path));
            JsonObject subObject = object.get("DB2ConnectString").getAsJsonObject();
            return subObject.get(option).getAsString();
        } catch (Exception e) {

            return "";
        }
    }

    public static String GetAs400(String option) {
        try {
            String path = "E:\\web\\Setting.json";
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(new FileReader(path));
            JsonObject subObject = object.get("DB2ConnectString1").getAsJsonObject();
            return subObject.get(option).getAsString();
        } catch (Exception e) {

            return "";
        }
    }
}