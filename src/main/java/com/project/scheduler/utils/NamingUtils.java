package com.project.scheduler.utils;

public class NamingUtils {

    public static String kebabToCamel(String kebab) {
        StringBuilder result = new StringBuilder();
        boolean toUpper = false;

        for (char ch : kebab.toCharArray()) {
            if (ch == '-') {
                toUpper = true;
            } else {
                result.append(toUpper ? Character.toUpperCase(ch) : ch);
                toUpper = false;
            }
        }

        return result.toString();
    }
}

