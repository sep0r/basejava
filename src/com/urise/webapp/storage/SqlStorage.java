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
            selectResumeGet(r, uuid, conn, "SELECT type, value FROM contact WHERE resume_uuid =?");
            selectResumeGet(r, uuid, conn, "SELECT type, value FROM section WHERE resume_uuid =?");
            return r;
        });
    }

    void selectResumeGet(Resume r, String uuid, Connection conn, String query) throws SQLException {
        try (PreparedStatement prepareStatement = conn
                .prepareStatement(query)) {
            prepareStatement.setString(1, uuid);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("type")) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        addSection(r, rs);
                        break;
                    case "TELEPHONE":
                    case "SKYPE":
                    case "EMAIL":
                    case "LINKEDIN":
                    case "GITHUB":
                    case "STACKOVERFLOW":
                        addContact(r, rs);
                        break;
                }
            }
        }
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
            deleteResumeUpdate(r, conn, "DELETE FROM contact WHERE resume_uuid = ? ");
            insertContact(r, conn);
            deleteResumeUpdate(r, conn, "DELETE FROM section WHERE resume_uuid = ? ");
            insertSection(r, conn);
            return null;
        });
    }

    void deleteResumeUpdate(Resume r, Connection conn, String query) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.executeUpdate();
        }
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
                switch (e.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(1, ((TextSection) e.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String sectionText = String.join("\n", ((ListSection) e.getValue()).getListText());
                        ps.setString(1, sectionText);
                        break;
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
            selectResumeGetAll(resumeMap, connection, "SELECT resume_uuid, type, value FROM contact");
            selectResumeGetAll(resumeMap, connection, "SELECT resume_uuid, type, value FROM section");
            return new ArrayList<>(resumeMap.values());
        });
    }

    void selectResumeGetAll(Map<String, Resume> resumeMap, Connection conn, String query) throws SQLException {
        try (
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("type")) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        addSection(resumeMap.get(rs.getString("resume_uuid")), rs);
                        break;
                    case "TELEPHONE":
                    case "SKYPE":
                    case "EMAIL":
                    case "LINKEDIN":
                    case "GITHUB":
                    case "STACKOVERFLOW":
                        addContact(resumeMap.get(rs.getString("resume_uuid")), rs);
                        break;
                }
            }
        }
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
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(SectionType.valueOf(type), new TextSection(value));
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    r.addSection(SectionType.valueOf(type),
                            new ListSection(Arrays.asList(value.split("\n"))));
                    break;
            }
        }
    }
}