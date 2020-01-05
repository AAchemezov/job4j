package ru.job4j.list;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ArrayContainerTest extends SimpleContainerTest {

    ArrayContainer<String> stringSimpleArray;

    @Override
    protected <E> ISimpleContainer<E> getEmptyContainer() {
        return new ArrayContainer<>();
    }

    @Test
    public void testCapacity() {
        stringSimpleArray = new ArrayContainer<String>(0);
        for (int i = 0; i < 100; i++) {
            stringSimpleArray.add("" + i);
        }
        for (int i = 0; i < 100; i++) {
            assertThat(stringSimpleArray.get(i), equalTo("" + i));
        }
    }

    @Test
    public void testIteratorCapacity() {
        stringSimpleArray = new ArrayContainer<String>(100);
        for (int i = 0; i < 100; i++) {
            stringSimpleArray.add("" + i);
        }
        Iterator<String> iterator = stringSimpleArray.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            assertThat(iterator.next(), equalTo("" + i));
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNegativeValueCapacity() {
        stringSimpleArray = new ArrayContainer<String>(-1);
    }


}
