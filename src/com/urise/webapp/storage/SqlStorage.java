package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    queryMethod(r, conn, sqlQuery.UPDATE.QUERY, sqlQuery.UPDATE_CONTACT.QUERY);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    queryMethod(r, conn, sqlQuery.ADD_NAME.QUERY, sqlQuery.ADD_CONTACT.QUERY);
                    return null;
                }
        );
    }

    void queryMethod(Resume r, Connection connection, String queryName, String queryContact) throws SQLException {
        try (PreparedStatement prepareStatement = connection
                .prepareStatement(queryName)) {
            prepareStatement.setString(1, r.getFullName());
            prepareStatement.setString(2, r.getUuid());
            prepareStatement.execute();
        }
        try (PreparedStatement ps = connection
                .prepareStatement(queryContact)) {
            for (Map.Entry<ContactType, String> e : r.getContact().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    enum sqlQuery {
        ADD_NAME("INSERT INTO resume (full_name, uuid) VALUES (?,?)"),
        ADD_CONTACT("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)"),
        UPDATE("UPDATE resume SET full_name = ? WHERE uuid = ?"),
        UPDATE_CONTACT("UPDATE contact SET value = ? WHERE resume_uuid = ? and type = ?");

        String QUERY;

        sqlQuery(String QUERY) {
            this.QUERY = QUERY;
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
                    List<Resume> list = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    int i = 0;
                    Resume resume = null;
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        ContactType contactType = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("value");
                        if (uuid != null && i == 0 || !resume.getUuid().equals(uuid)) {
                            resume = new Resume(uuid, fullName);
                            resume.addContact(contactType, value);
                            list.add(resume);
                        }
                        if (resume.getUuid().equals(uuid)) {
                            resume.addContact(contactType, value);
                        }
                        i++;
                    }
                    return list;
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

    private static void checkNotExistStorageException(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}