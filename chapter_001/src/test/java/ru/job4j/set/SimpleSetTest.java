package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimpleSetTest {
    SimpleSet<String> set;

    @Before
    public void setUp() {
        set = new SimpleSet<>();
    }

    @Test
    public void basicFunctionalityStackTest() {
        set.add("one");
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("two");
        set.add("three");
        Iterator<String> iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            i++;
        }
        assertThat(i, is(3));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerOnTwoNullValues() {
        set.add(null);
    }
}
