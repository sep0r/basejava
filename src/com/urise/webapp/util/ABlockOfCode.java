package com.urise.webapp.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ABlockOfCode<T> {
    T execute(PreparedStatement preparedStatement) throws SQLException;
}