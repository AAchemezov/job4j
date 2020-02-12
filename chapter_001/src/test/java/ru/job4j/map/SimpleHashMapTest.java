package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimpleHashMapTest {

    private SimpleHashMap<User, String> emptyMap;
    private SimpleHashMap<User, String> mapWithData;
    private Iterator<SimpleHashMap.Node<User, String>> iterator;
    private User ivan;
    private User ivanSecond;
    private User sara;
    private User john;

    @Before
    public void setUp() {
        emptyMap = new SimpleHashMap<>();
        mapWithData = new SimpleHashMap<>();
        ivan = new User("Ivan", 2, new GregorianCalendar(1979, Calendar.JANUARY, 25));
        ivanSecond = new User("Ivan", 2, new GregorianCalendar(1979, Calendar.JANUARY, 25));
        sara = new User("Sara", 1, new GregorianCalendar(1990, Calendar.MARCH, 1));
        john = new User("John", 0, new GregorianCalendar(1962, Calendar.AUGUST, 12));
        mapWithData.insert(ivan, "Иван");
        mapWithData.insert(sara, "Сара");
        iterator = mapWithData.iterator();
    }

    @Test
    public void basicFunctionalityInsertTest() {
        assertThat(emptyMap.insert(ivan, "Иван"), is(true));
        assertThat(emptyMap.insert(ivanSecond, "Иван второй"), is(false));
        assertThat(emptyMap.insert(john, null), is(true));
        assertThat(emptyMap.insert(null, "аноним"), is(false));
    }

    @Test
    public void basicFunctionalityGetTest() {
        assertThat(mapWithData.get(ivan), is("Иван"));
        assertThat(mapWithData.get(ivanSecond), is("Иван"));
        assertThat(mapWithData.get(john), equalTo(null));
        assertThat(mapWithData.get(null), equalTo(null));
    }

    @Test
    public void basicFunctionalityDeleteTest() {
        assertThat(mapWithData.delete(sara), is(true));
        assertThat(mapWithData.delete(ivanSecond), is(true));
        assertThat(mapWithData.delete(ivan), is(false));
        assertThat(mapWithData.delete(john), is(false));
        assertThat(mapWithData.delete(null), is(false));
    }

    @Test
    public void testIsEmptyAndSize() {
        assertThat(emptyMap.isEmpty(), is(true));
        assertThat(emptyMap.getSize(), is(0));
        assertThat(mapWithData.isEmpty(), is(false));
        assertThat(mapWithData.getSize(), is(2));
    }

    @Test
    public void testDynamicCapacity() {
        SimpleHashMap<Integer, Object> map = new SimpleHashMap<>();
        for (int i = 0; i < 89; i++) {
            assertThat(map.insert(i, null), is(true));
        }
        assertThat(map.getCapacity(), is(128)); // (89 / 128 = 0,6953125) < 0.7
        assertThat(map.insert(90, null), is(true));
        assertThat(map.getCapacity(), is(256)); // (90 / 128 = 0,703125) > 0.7
    }

    @Test
    public void testIteratorHasNext() {
        for (int i = 0; i < 5; i++) {
            assertThat(iterator.hasNext(), is(true));
        }
        iterator.next();
        for (int i = 0; i < 5; i++) {
            assertThat(iterator.hasNext(), is(true));
        }
        iterator.next();
        for (int i = 0; i < 5; i++) {
            assertThat(iterator.hasNext(), is(false));
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnHasNext() {
        assertThat(mapWithData.insert(john, "Джон, который сломал итератор"), is(true));
        iterator.hasNext();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnNext() {
        assertThat(mapWithData.insert(john, "Джон, который сломал итератор"), is(true));
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnForEach() {
        assertThat(mapWithData.insert(john, "Джон, который сломал итератор"), is(true));
        iterator.forEachRemaining((o) -> {
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorConcurrentModificationExceptionOnRemove() {
        iterator.remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementExceptionOnNext() {
        assertThat(mapWithData.getSize(), is(2));
        iterator.next();
        iterator.next();
        iterator.next();
    }
}
