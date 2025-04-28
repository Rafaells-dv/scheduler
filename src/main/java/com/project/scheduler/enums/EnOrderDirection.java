package com.project.scheduler.enums;

public enum EnOrderDirection {
    ASC,
    DESC;

    public static EnOrderDirection fromString(String value) {
        try {
            return EnOrderDirection.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return DESC;
        }
    }
}
