package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.yauhen2012.repository.UserRepository;
import com.gmail.yauhen2012.repository.model.User;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static volatile UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        UserRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user (username, password, role) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return user;
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT user_id, username, password, createdBy, role FROM user;"
                )
        ) {
            List<User> userList = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = getUser(rs);
                    userList.add(user);
                }
                return userList;
            }
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        int userId = rs.getInt("user_id");
        user.setUserId(userId);
        String userName = rs.getString("username");
        user.setUserName(userName);
        String password = rs.getString("password");
        user.setPassword(password);
        String createdBy = rs.getString("createdBy");
        user.setCreatedBy(createdBy);
        String role = rs.getString("role");
        user.setRole(role);

        return user;
    }

}
