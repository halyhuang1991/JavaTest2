package com.test;

import java.util.HashMap;
import java.util.Map;

public class HM {
    public static void TestHashMap() {
        Map<String, Object> rowData = new HashMap<String, Object>();
        rowData.put("a", "a");
        rowData.put("b", "a");
        rowData.put("", "ac");
        rowData.put(null, "ac");
        rowData.put(null, null);
        for (Map.Entry<String, Object> m : rowData.entrySet()) {
            System.out.print(m.getKey() + "    ");
            System.out.println(m.getValue());
        }
    }
}