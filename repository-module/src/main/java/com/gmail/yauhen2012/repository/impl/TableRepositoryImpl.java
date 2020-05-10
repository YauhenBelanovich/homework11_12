package com.gmail.yauhen2012.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.gmail.yauhen2012.repository.TableRepository;

public class TableRepositoryImpl implements TableRepository {

    private static volatile TableRepository instance;

    public static TableRepository getInstance() {
        TableRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (TableRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TableRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public void executeQuery(Connection connection, String query) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
        }
    }

}
