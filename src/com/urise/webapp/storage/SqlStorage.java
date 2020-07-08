package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", preparedStatement -> {
            preparedStatement.executeUpdate();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String type = rs.getString("type");
                        String value = rs.getString("value");
                        if (type != null && value != null) {
                            r.addContact(ContactType.valueOf(type), value);
                        }
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement prepareStatement = conn
                    .prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                prepareStatement.setString(1, r.getFullName());
                prepareStatement.setString(2, r.getUuid());
                prepareStatement.execute();

                checkNotExistStorageException(r.getUuid(), prepareStatement);
            }
            try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM contact c " +
                    "WHERE c.resume_uuid = ? ")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.executeUpdate();
            }
            insertContact(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement prepareStatement = conn
                            .prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)")) {
                        prepareStatement.setString(1, r.getFullName());
                        prepareStatement.setString(2, r.getUuid());
                        prepareStatement.execute();
                    }
                    insertContact(r, conn);
                    return null;
                }
        );
    }

    void insertContact(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection
                .prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContact().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            checkNotExistStorageException(uuid, preparedStatement);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "  ORDER BY full_name, uuid ",
                ps -> {
                    Map<String, Resume> resumeMap = new LinkedHashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        Resume r = resumeMap.computeIfAbsent(uuid, u -> new Resume(uuid, fullName));
                        String type = rs.getString("type");
                        String value = rs.getString("value");
                        if (type != null) {
                            r.addContact(ContactType.valueOf(type), value);
                        }
                    }
                    return new ArrayList<>(resumeMap.values());
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private static void checkNotExistStorageException(String uuid, PreparedStatement preparedStatement) throws
            SQLException {
        if (preparedStatement.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}