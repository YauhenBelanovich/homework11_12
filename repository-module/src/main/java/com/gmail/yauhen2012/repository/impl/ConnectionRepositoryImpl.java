package com.gmail.yauhen2012.repository.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.constant.ConnectionConstant;
import com.gmail.yauhen2012.util.PropertyUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.yauhen2012.repository.constant.ConnectionConstant.DATABASE_PASSWORD;
import static com.gmail.yauhen2012.repository.constant.ConnectionConstant.DATABASE_URL;
import static com.gmail.yauhen2012.repository.constant.ConnectionConstant.DATABASE_USERNAME;

public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CACHE_PREP_STMTS = "cachePrepStmts";
    private static final String PREP_STMT_CACHE_SIZE = "prepStmtCacheSize";
    private static final String PREP_STMT_CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    private static HikariDataSource ds;
    private static volatile ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        ConnectionRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
                throw new IllegalArgumentException();
            }
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty(CACHE_PREP_STMTS, propertyUtil.getProperty(ConnectionConstant.CACHE_PREP_STMTS));
            config.addDataSourceProperty(PREP_STMT_CACHE_SIZE, propertyUtil.getProperty(ConnectionConstant.PREP_STMT_CACHE_SIZE));
            config.addDataSourceProperty(PREP_STMT_CACHE_SQL_LIMIT, propertyUtil.getProperty(ConnectionConstant.PREP_STMT_CACHE_SQL_LIMIT));
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }

}