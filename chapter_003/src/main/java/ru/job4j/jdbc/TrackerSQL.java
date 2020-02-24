package ru.job4j.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.tracker.*;

import java.io.Closeable;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TrackerSQL implements ITracker, Closeable {

    private static final Logger LOG = LogManager.getLogger(TrackerSQL.class.getName());

    private Connection connection;

    //Запросы
    private static final String QUERY_INSERT = "INSERT INTO public.items(name, description, created) VALUES (?,?,?);";
    private static final String QUERY_REPLACE = "UPDATE public.items SET name = ?,description = ?,created = ?WHERE id = ?";
    private static final String QUERY_DELETE_ALL = "DELETE FROM public.items";
    private static final String QUERY_DELETE = QUERY_DELETE_ALL + " where id = ?";
    private static final String QUERY_FIND_ALL = "Select id, name, description, created FROM public.items";
    private static final String QUERY_FIND_BY_NAME = QUERY_FIND_ALL + " WHERE name = ?";
    private static final String QUERY_FIND_BY_ID = QUERY_FIND_ALL + " WHERE id = ?";
    private static final String QUERY_IS_SCHEMA_CORRECT = "SELECT COUNT(t.table_name) FROM information_schema.tables t WHERE t.table_schema='public' AND t.table_name = 'items'";
    private static final String CREATE_ITEM_SQL = "CREATE TABLE public.items"
            + "( id serial PRIMARY KEY, "
            + "name character varying, "
            + "description character varying, "
            + "created bigint "
            + ")";

    /**
     * Инициализация подключения с дефлтными настройками в файле ресурсов "app.properties"
     *
     * @return true - подключение установлено
     */
    public boolean init() {
        return this.init("app.properties");
    }

    /**
     * Инициализация подключения с настройками в файле ресурсов propertiesPath
     *
     * @param propertiesPath путь к файлу с настройками подключения
     * @return true - подключение установлено
     */
    public boolean init(String propertiesPath) {
        Properties config = new Properties();
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream(propertiesPath)) {
            config.load(in);
            return init(config);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * Инициализация подключения с настройками из config
     *
     * @param config настройки подключения
     * @return true - подключение установлено
     */
    public boolean init(Properties config) {
        try {
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            checkSchema();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return this.connection != null;
    }


    @Override
    public Item add(Item item) {
        try (PreparedStatement ps = this.connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setLong(3, item.getCreate());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getString(1));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }


    @Override
    public void replace(String id, Item item) {

        try (PreparedStatement ps = connection.prepareStatement(QUERY_REPLACE)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setLong(3, item.getCreate());
            ps.setInt(4, Integer.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String id) {
        try (PreparedStatement ps = connection.prepareStatement(QUERY_DELETE)) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Удалить все заявки
     */
    public void deleteAll() {
        try (PreparedStatement ps = connection.prepareStatement(QUERY_DELETE_ALL)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Item> findAll() {
        ArrayList<Item> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(QUERY_FIND_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Item(rs.getString("id"), rs.getString("name"),
                        rs.getString("description"), rs.getLong("created")));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_FIND_BY_NAME)) {
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Item(rs.getString("id"), rs.getString("name"),
                        rs.getString("description"), rs.getLong("created")));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        try (PreparedStatement ps = connection.prepareStatement(QUERY_FIND_BY_ID)) {
            ps.setInt(1, (Integer.parseInt(id)));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Item(rs.getString("id"), rs.getString("name"),
                        rs.getString("description"), rs.getLong("created"));
            }
            rs.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Проверяет корректность схемы (наличие таблицы), если схема не корректна, то создаёт таблицу
     */
    private void checkSchema() throws SQLException {
        if (!isSchemaCorrect()) {
            createItemsTable();
        }
    }

    /**
     * Проверяет наличие таблицы в схеме
     */
    private boolean isSchemaCorrect() throws SQLException {
        boolean result = false;
        try (PreparedStatement ps = this.connection.prepareStatement(QUERY_IS_SCHEMA_CORRECT)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count") == 1;
            }
            rs.close();
        }
        return result;
    }

    /**
     * Создаёт таблицу items
     */
    private void createItemsTable() throws SQLException {
        try (PreparedStatement ps = this.connection.prepareStatement(CREATE_ITEM_SQL)) {
            ps.execute();
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        TrackerSQL trackerSQL = new TrackerSQL();
        if (trackerSQL.init()) {
            new StartUI(new ValidateInput(new ConsoleInput()), trackerSQL).init();
        }
    }
}