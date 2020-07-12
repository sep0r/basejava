package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement prepareStatement = conn
                    .prepareStatement("" +
                            "    SELECT * FROM resume r " +
                            "     WHERE r.uuid =? ")) {
                prepareStatement.setString(1, uuid);
                ResultSet rsResume = prepareStatement.executeQuery();
                if (!rsResume.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rsResume.getString("full_name"));
            }
            try (PreparedStatement prepareStatement = conn
                    .prepareStatement("" +
                            "    SELECT c.type, c.value" +
                            "      FROM contact c " +
                            "     WHERE c.resume_uuid =? ")) {
                prepareStatement.setString(1, uuid);
                ResultSet rsContact = prepareStatement.executeQuery();
                while (rsContact.next()) {
                    addContact(r, rsContact);
                }
            }
            try (PreparedStatement prepareStatement = conn
                    .prepareStatement("" +
                            "    SELECT s.type, s.value" +
                            "      FROM section s " +
                            "     WHERE s.resume_uuid =? ")) {
                prepareStatement.setString(1, uuid);
                ResultSet rsSection = prepareStatement.executeQuery();
                while (rsSection.next()) {
                    addSection(r, rsSection);
                }
            }
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
            try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM section s " +
                    "WHERE s.resume_uuid = ? ")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.executeUpdate();
            }
            insertSection(r, conn);
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
                    insertSection(r, conn);
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

    void insertSection(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection
                .prepareStatement("INSERT INTO section (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getTextSection().entrySet()) {
                if (e.getValue() instanceof TextSection) {
                    ps.setString(1, ((TextSection) e.getValue()).getText());
                } else {
                    String sectionText = String.join("\n", ((ListSection) e.getValue()).getListText());
                    ps.setString(1, sectionText);
                }
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

    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "ORDER BY r.full_name, r.uuid")) {
                ResultSet rsResume = preparedStatement.executeQuery();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid, rsResume.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT c.resume_uuid, c.type, c.value " +
                    "FROM contact c ")) {
                ResultSet rsContact = preparedStatement.executeQuery();
                while (rsContact.next()) {
                    addContact(resumeMap.get(rsContact.getString("resume_uuid")), rsContact);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT s.resume_uuid, s.type, s.value " +
                    "FROM section s ")) {
                ResultSet rsSection = preparedStatement.executeQuery();
                while (rsSection.next()) {
                    addSection(resumeMap.get(rsSection.getString("resume_uuid")), rsSection);
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

    private void addContact(Resume r, ResultSet rsContact) throws SQLException {
        String type = rsContact.getString("type");
        String value = rsContact.getString("value");
        if (type != null) {
            r.addContact(ContactType.valueOf(type), value);
        }
    }

    private void addSection(Resume r, ResultSet rsSection) throws SQLException {
        String type = rsSection.getString("type");
        String value = rsSection.getString("value");
        if (type != null) {
            if (!value.contains("\n")) {
                r.addSection(SectionType.valueOf(type), new TextSection(value));
            } else {
                r.addSection(SectionType.valueOf(type),
                        new ListSection(Arrays.asList(value.split("\n"))));
            }
        }
    }
}