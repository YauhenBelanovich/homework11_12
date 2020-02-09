package com.gmail.yauhen2012.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    List<T> findAll(Connection connection) throws SQLException;

}
