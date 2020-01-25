package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimpleHashMapTest {

    private SimpleHashMap<User, String> map;
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @Before
    public void setUp() {
        map = new SimpleHashMap<>();
        user1 = new User("Ivan", 2, new GregorianCalendar(1999, Calendar.JANUARY, 25));
        user2 = new User("Sara", 1, new GregorianCalendar(1990, Calendar.MARCH, 1));
        user3 = new User("Ivan", 2, new GregorianCalendar(1999, Calendar.JANUARY, 25));
        user4 = new User("John", 0, new GregorianCalendar(2000, Calendar.DECEMBER, 15));
    }

    @Test
    public void basicFunctionalityTest() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        assertThat(map.insert(user4, null), is(true));
        assertThat(map.insert(user1, "user5"), is(false));
        assertThat(map.insert(user2, "user6"), is(false));
        assertThat(map.insert(null, "user7"), is(false));

        assertThat(map.get(user1), is("user1"));
        assertThat(map.get(user2), is("user2"));
        assertThat(map.get(user3), is("user1"));
        assertThat(map.get(user4), equalTo(null));
        assertThat(map.get(null), equalTo(null));

        assertThat(map.delete(user1), is(true));
        assertThat(map.delete(user2), is(true));
        assertThat(map.delete(user3), is(false));
        assertThat(map.delete(user4), is(true));
        assertThat(map.delete(user1), is(false));
        assertThat(map.delete(user2), is(false));
        assertThat(map.delete(user4), is(false));
        assertThat(map.delete(null), is(false));
        for (int i = 0; i < 200; i++) {
            map.insert(new User(i + "", i, new GregorianCalendar(1800 + i, Calendar.JANUARY, 1)), "" + i);
        }
    }

    @Test
    public void basicFunctionalityIteratorTest() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        assertThat(map.insert(user4, "user4"), is(true));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        for (int i = 0; i < 20; i++) {
            assertThat(iterator.hasNext(), is(true));
        }
        iterator.next();
        iterator.next();
        for (int i = 0; i < 20; i++) {
            assertThat(iterator.hasNext(), is(true));
        }
        iterator.next();
        for (int i = 0; i < 20; i++) {
            assertThat(iterator.hasNext(), is(false));
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnHasNext() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        assertThat(map.insert(user4, "user4"), is(true));
        iterator.hasNext();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnNext() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        assertThat(map.insert(user4, "user4"), is(true));
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnForEach() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        assertThat(map.insert(user4, "user4"), is(true));
        iterator.forEachRemaining((o) -> {
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorConcurrentModificationExceptionOnRemove() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        iterator.next();
        iterator.next();
        iterator.remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementExceptionOnNext() {
        assertThat(map.insert(user1, "user1"), is(true));
        assertThat(map.insert(user2, "user2"), is(true));
        assertThat(map.insert(user3, "user3"), is(false));
        Iterator<SimpleHashMap.Node<User, String>> iterator = map.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }
}
