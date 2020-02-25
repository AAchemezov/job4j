package ru.job4j.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static ru.job4j.xml.ElementConfiguration.Entry;

/**
 * Стор в БД
 */
public class StoreSQL implements AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(StoreSQL.class);

    private static final String TABLE = "entry";
    private static final String FIELD = "field";
    private static final String SQL_INSERT = String.format("INSERT INTO %1$s(%2$s) VALUES (?)", TABLE, FIELD);
    private static final String SQL_CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS  %1$s (%2$s INTEGER)", TABLE, FIELD);
    private static final String SQL_SELECT_TABLE = String.format("SELECT * FROM %1$s", TABLE);
    private static final String SQL_CLEAR_TABLE = String.format("DELETE FROM %1$s", TABLE);

    private Connection connect;
    private boolean autoCommit;

    public StoreSQL(Config config) throws SQLException {
        this.connect = getConnectionByConfig(config);
    }

    /**
     * Метод инициализация стора, генерация таблиц, удаление старых записей
     */
    void initDataBase() throws SQLException {
        createTable();
        clearTable();
    }

    private void createTable() throws SQLException {
        try (Statement statement = connect.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
            checkAutoCommit();
        } catch (SQLException e) {
            catchRollback(e);
        }
    }

    private void clearTable() throws SQLException {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate(SQL_CLEAR_TABLE);
            checkAutoCommit();
        } catch (SQLException e) {
            catchRollback(e);
        }
    }

    /**
     * Возвращает объект соединения с БД.
     */
    private Connection getConnectionByConfig(Config config) throws SQLException {
        Connection connection = DriverManager.getConnection(
                config.get("url"),
                config.get("username"),
                config.get("password")
        );
        autoCommit = Boolean.parseBoolean(config.get("autoCommit"));
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    /**
     * Генерация данных (пакетная)
     */
    public void generateWithBatch(int size) throws SQLException {
        try (PreparedStatement statement = connect.prepareStatement(SQL_INSERT)) {
            for (int i = 1; i <= size; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            checkAutoCommit();
        } catch (SQLException e) {
            catchRollback(e);
        }
    }

    /**
     * Генерация данных
     */
    public void generate(int size) throws SQLException {
        try {
            for (int i = 1; i <= size; i++) {
                try (PreparedStatement statement = connect.prepareStatement(SQL_INSERT)) {
                    statement.setInt(1, i);
                    statement.executeUpdate();
                }
            }
            checkAutoCommit();
        } catch (SQLException e) {
            catchRollback(e);
        }
    }

    /**
     * Возвращает лист Entry на основе записей в БД
     */
    public List<Entry> obtainListEntry() throws SQLException {
        List<Entry> list = new LinkedList<>();
        try (Statement statement = connect.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SQL_SELECT_TABLE)) {
                while (rs.next()) {
                    int value = rs.getInt(FIELD);
                    list.add(new Entry(value));
                }
            }
            checkAutoCommit();
        } catch (SQLException e) {
            catchRollback(e);
        }
        return list;
    }

    /**
     * Выполнение коммита, если autoCommit = false
     */
    private void checkAutoCommit() throws SQLException {
        if (!autoCommit) {
            connect.commit();
        }
    }

    /**
     * Обработка ошибки SQL и откат коммита, если autoCommit = false.
     */
    private void catchRollback(SQLException e) throws SQLException {
        LOG.error(e.getMessage(), e);
        if (!autoCommit) {
            connect.rollback();
        }
    }

    @Override
    public void close() {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}