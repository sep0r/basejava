package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.util.ABlockOfCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T request(String request, ABlockOfCode<T> aBlockOfCode) {
        try (Connection conn = this.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(request)) {
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}