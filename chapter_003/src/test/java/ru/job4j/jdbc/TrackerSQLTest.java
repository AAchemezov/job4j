package ru.job4j.jdbc;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.Item;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TrackerSQLTest {
    private static TrackerSQL trackerSQL;

    @Before
    public void setUp() {
        trackerSQL = new TrackerSQL();
        assertThat(trackerSQL.init("appTest.properties"), is(true));
        trackerSQL.deleteAll();
    }

    @Test
    public void checkConnection() {
        trackerSQL = new TrackerSQL();
        assertThat(trackerSQL.init(), is(true));
    }

    @Test
    public void testAddItem() {
        Date date = new Date();
        Item item = new Item("TestItem", "TestItem description", date.getTime());
        trackerSQL.add(item);
        assertThat(item.getName(), is("TestItem"));
        assertThat(item.getDescription(), is("TestItem description"));
        assertThat(item.getCreate(), is(date.getTime()));
    }

    @Test
    public void testDeleteItem() {
        Item item = new Item("TestItemDelete", "TestItemDelete description", new Date().getTime());
        trackerSQL.add(item);
        trackerSQL.delete(item.getId());
        assertNull(trackerSQL.findById(item.getId()));
    }

    @Test
    public void testFindById() {
        Item item = new Item("TestItem", "TestItem description", new Date().getTime());
        trackerSQL.add(item);
        assertThat(trackerSQL.findById(item.getId()), is(item));
    }

    @Test
    public void testFindByName() {
        trackerSQL.add(new Item("TestItem", "Description1", new Date().getTime()));
        trackerSQL.add(new Item("FindItem", "Description2", new Date().getTime()));
        trackerSQL.add(new Item("FindItem", "Description3", new Date().getTime()));
        List<Item> list = trackerSQL.findByName("FindItem");
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getDescription(), Matchers.isOneOf("Description2", "Description3"));
        assertThat(list.get(1).getDescription(), Matchers.isOneOf("Description2", "Description3"));
    }

    @Test
    public void testReplace() {
        Item one = new Item("TestItem1", "Description1", new Date().getTime());
        Item two = new Item("TestItem2", "Description2", new Date().getTime());
        trackerSQL.add(one);
        trackerSQL.replace(one.getId(), two);
        assertThat(trackerSQL.findByName("TestItem2"), is(notNullValue()));
    }

    @Test
    public void testFindByAll() {
        Item one = new Item("TestItem1", "Description1", new Date().getTime());
        Item two = new Item("TestItem2", "Description2", new Date().getTime());
        trackerSQL.add(one);
        trackerSQL.add(two);
        assertThat(trackerSQL.findAll().get(0).getName(), is("TestItem1"));
        assertThat(trackerSQL.findAll().get(1).getName(), is("TestItem2"));
    }

}
