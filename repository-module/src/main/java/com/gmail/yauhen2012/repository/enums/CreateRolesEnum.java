package com.gmail.yauhen2012.repository.enums;

public enum CreateRolesEnum {
    CREATE_ADMIN_ROLE("INSERT INTO role values ('ADMIN', 'All rights')"),
    CREATE_USER_ROLE("INSERT INTO role values ('USER', 'Limited rights')");

    private final String query;

    CreateRolesEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
