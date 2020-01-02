package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {

    SimpleArray<String> stringSimpleArray;

    @Before
    public void setUp() {
        stringSimpleArray = new SimpleArray<String>(3);
    }

    @Test
    public void basicFunctionalityTest() {
        assertThat(stringSimpleArray.isEmpty(), is(true));
        assertThat(stringSimpleArray.getSize(), is(0));
        stringSimpleArray.add("zero");
        assertThat(stringSimpleArray.getSize(), is(1));
        stringSimpleArray.add("one");
        assertThat(stringSimpleArray.getSize(), is(2));
        stringSimpleArray.add("two");
        assertThat(stringSimpleArray.getSize(), is(3));
        assertThat(stringSimpleArray.isEmpty(), is(false));

        assertThat(stringSimpleArray.get(0), equalTo("zero"));
        assertThat(stringSimpleArray.get(1), equalTo("one"));
        assertThat(stringSimpleArray.get(2), equalTo("two"));
        assertThat(stringSimpleArray.getSize(), is(3));
        assertThat(stringSimpleArray.isEmpty(), is(false));

        stringSimpleArray.set(1, "first");
        assertThat(stringSimpleArray.get(1), equalTo("first"));
        assertThat(stringSimpleArray.getSize(), is(3));
        assertThat(stringSimpleArray.isEmpty(), is(false));

        assertThat(stringSimpleArray.remove(1), equalTo("first"));
        assertThat(stringSimpleArray.get(0), equalTo("zero"));
        assertThat(stringSimpleArray.get(1), equalTo("two"));
        assertThat(stringSimpleArray.getSize(), is(2));
        assertThat(stringSimpleArray.isEmpty(), is(false));

        assertThat(stringSimpleArray.remove(1), equalTo("two"));
        assertThat(stringSimpleArray.remove(0), equalTo("zero"));
        assertThat(stringSimpleArray.getSize(), is(0));
        assertThat(stringSimpleArray.isEmpty(), is(true));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsExceptionOnAddMethod() {
        stringSimpleArray = new SimpleArray<String>(0);
        stringSimpleArray.add("Error");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsExceptionOnLimitSize() {
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.add("Error");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsExceptionOnRemoveMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.remove(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsExceptionOnGetMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowIndexOutOfBoundsExceptionOnSetMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.set(1, "Error");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentExceptionOnNegativeCapacity() {
        stringSimpleArray = new SimpleArray<String>(-3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentExceptionOnNegativeIndexRemoveMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.remove(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentExceptionOnNegativeIndexGetMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.add("good");
        stringSimpleArray.get(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowIllegalArgumentExceptionOnNegativeIndexSetMethod() {
        stringSimpleArray.add("good");
        stringSimpleArray.set(-1, "error");
    }

    @Test
    public void testDefaultCapacity() {
        stringSimpleArray = new SimpleArray<String>();
        assertThat(stringSimpleArray.getSize(), is(0));
        stringSimpleArray.add("0");
        stringSimpleArray.add("1");
        stringSimpleArray.add("2");
        stringSimpleArray.add("3");
        stringSimpleArray.add("4");
        stringSimpleArray.add("5");
        stringSimpleArray.add("6");
        stringSimpleArray.add("7");
        stringSimpleArray.add("8");
        stringSimpleArray.add("9");
        assertThat(stringSimpleArray.getSize(), is(10));
    }

}
