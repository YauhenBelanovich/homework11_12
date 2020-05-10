package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.yauhen2012.repository.RoleRepository;
import com.gmail.yauhen2012.repository.model.Role;

public class RoleRepositoryImpl extends GeneralRepositoryImpl<Role> implements RoleRepository {

    private static volatile RoleRepository instance;

    private RoleRepositoryImpl() {
    }

    public static RoleRepository getInstance() {
        RoleRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (RoleRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RoleRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public List<Role> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT name, description FROM role;"
                )
        ) {
            List<Role> userList = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Role role = getRole(rs);
                    userList.add(role);
                }
                return userList;
            }
        }
    }

    private Role getRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        String name = rs.getString("name");
        role.setName(name);
        String description = rs.getString("description");
        role.setDescription(description);
        return role;
    }

}
