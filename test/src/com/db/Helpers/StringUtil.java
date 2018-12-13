package com.db.Helpers;

public class StringUtil {
    public static Boolean IsNullOrEmpty(String value) {
        if (value == null) {
            return true;
        } else if ("".equals(value)) {
            return true;
        } else {
            return false;
        }

    }
}