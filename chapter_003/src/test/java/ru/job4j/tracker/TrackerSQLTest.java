package ru.job4j.tracker;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TrackerSQLTest {

    private Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.add(new Item("name", "desc", new Date().getTime()));
            assertThat(trackerSQL.findByName("name").size(), is(1));
        }
    }


    @Test
    public void checkConnection() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            assertThat(trackerSQL.init(), is(true));
        }
    }

    @Test
    public void testAddItem() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            Date date = new Date();
            Item item = new Item("TestItem", "TestItem description", date.getTime());
            trackerSQL.add(item);
            assertThat(item.getName(), is("TestItem"));
            assertThat(item.getDescription(), is("TestItem description"));
            assertThat(item.getCreate(), is(date.getTime()));
        }
    }

    @Test
    public void testDeleteItem() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            Item item = new Item("TestItemDelete", "TestItemDelete description", new Date().getTime());
            trackerSQL.add(item);
            trackerSQL.delete(item.getId());
            assertNull(trackerSQL.findById(item.getId()));
        }
    }

    @Test
    public void testFindById() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            Item item = new Item("TestItem", "TestItem description", new Date().getTime());
            trackerSQL.add(item);
            assertThat(trackerSQL.findById(item.getId()), is(item));
        }
    }

    @Test
    public void testFindByName() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            trackerSQL.add(new Item("TestItem", "Description1", new Date().getTime()));
            trackerSQL.add(new Item("FindItem", "Description2", new Date().getTime()));
            trackerSQL.add(new Item("FindItem", "Description3", new Date().getTime()));
            List<Item> list = trackerSQL.findByName("FindItem");
            assertThat(list.size(), is(2));
            assertThat(list.get(0).getDescription(), Matchers.isOneOf("Description2", "Description3"));
            assertThat(list.get(1).getDescription(), Matchers.isOneOf("Description2", "Description3"));
        }
    }

    @Test
    public void testReplace() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            Item one = new Item("TestItem1", "Description1", new Date().getTime());
            Item two = new Item("TestItem2", "Description2", new Date().getTime());
            trackerSQL.add(one);
            trackerSQL.replace(one.getId(), two);
            assertThat(trackerSQL.findByName("TestItem2"), is(notNullValue()));
        }
    }

    @Test
    public void testFindByAll() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            trackerSQL.deleteAll();
            Item one = new Item("TestItem1", "Description1", new Date().getTime());
            Item two = new Item("TestItem2", "Description2", new Date().getTime());
            trackerSQL.add(one);
            trackerSQL.add(two);
            assertThat(trackerSQL.findAll().get(0).getName(), is("TestItem1"));
            assertThat(trackerSQL.findAll().get(1).getName(), is("TestItem2"));
        }
    }

}
