package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimpleContainerTest {

    private ISimpleContainer<String> stringSimpleArray;

    @Before
    public void setUp() {
        stringSimpleArray = getEmptyContainer();
        stringSimpleArray.add("zero");
        stringSimpleArray.add("one");
    }

    protected <E> ISimpleContainer<E> getEmptyContainer() {
        return new LinkedContainer<E>();
    }


    @Test
    public void basicFunctionalityTest() {
        assertThat(stringSimpleArray.get(0), equalTo("zero"));
        assertThat(stringSimpleArray.get(1), equalTo("one"));
    }

    @Test
    public void testCapacity() {
        stringSimpleArray = getEmptyContainer();
        for (int i = 0; i < 100; i++) {
            stringSimpleArray.add("" + i);
        }
        for (int i = 0; i < 100; i++) {
            assertThat(stringSimpleArray.get(i), equalTo("" + i));
        }
    }

    @Test
    public void testIteratorCapacity() {
        stringSimpleArray = getEmptyContainer();
        for (int i = 0; i < 100; i++) {
            stringSimpleArray.add("" + i);
        }
        Iterator<String> iterator = stringSimpleArray.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            assertThat(iterator.next(), equalTo("" + i));
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnHasNext() {
        Iterator<String> iterator = stringSimpleArray.iterator();
        stringSimpleArray.add("Modification");
        iterator.hasNext();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnNext() {
        Iterator<String> iterator = stringSimpleArray.iterator();
        stringSimpleArray.add("Modification");
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnForEach() {
        Iterator<String> iterator = stringSimpleArray.iterator();
        stringSimpleArray.add("Modification");
        iterator.forEachRemaining((o) -> {
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorConcurrentModificationExceptionOnRemove() {
        Iterator<String> iterator = stringSimpleArray.iterator();
        iterator.next();
        iterator.remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementExceptionOnNext() {
        Iterator<String> iterator = stringSimpleArray.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }

}
