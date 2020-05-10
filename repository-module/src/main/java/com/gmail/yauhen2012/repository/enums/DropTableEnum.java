package com.gmail.yauhen2012.repository.enums;

public enum DropTableEnum {
    DROP_USER_TABLE("DROP TABLE IF EXISTS user"),
    DROP_ROLE_TABLE("DROP TABLE IF EXISTS role");

    private final String query;

    DropTableEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
