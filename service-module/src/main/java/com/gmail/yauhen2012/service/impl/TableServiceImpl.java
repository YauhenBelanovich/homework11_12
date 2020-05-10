package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.TableRepository;
import com.gmail.yauhen2012.repository.enums.CreateTableEnum;
import com.gmail.yauhen2012.repository.enums.DropTableEnum;
import com.gmail.yauhen2012.repository.impl.ConnectionRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.TableRepositoryImpl;
import com.gmail.yauhen2012.service.TableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableServiceImpl implements TableService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static volatile TableService instance;
    private TableRepository tableRepository;
    private ConnectionRepository connectionRepository;

    private TableServiceImpl(ConnectionRepository connectionRepository, TableRepository tableRepository) {
        this.connectionRepository = connectionRepository;
        this.tableRepository = tableRepository;
    }

    public static TableService getInstance() {
        TableService localInstance = instance;
        if (localInstance == null) {
            synchronized (TableService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TableServiceImpl(
                            ConnectionRepositoryImpl.getInstance(),
                            TableRepositoryImpl.getInstance());
                }
            }
        }
        return localInstance;
    }

    @Override
    public void deleteAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropTableEnum action : DropTableEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void createAllTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateTableEnum action : CreateTableEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

}

