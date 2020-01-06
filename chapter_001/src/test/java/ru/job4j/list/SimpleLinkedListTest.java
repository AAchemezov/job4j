package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleLinkedListTest {

    private SimpleLinkedList<Integer> list, emptyList;

    @Before
    public void beforeTest() {
        emptyList = new SimpleLinkedList<>();
        list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void testDeleteAllElements() {
        assertThat(list.delete(), is(3));
        assertThat(list.delete(), is(2));
        assertThat(list.get(0), is(1));
        assertThat(list.getSize(), is(1));
        assertThat(list.delete(), is(1));
        assertThat(list.getSize(), is(0));
    }

    @Test(expected = NullPointerException.class)
    public void testGetFromEmptyList() {
        emptyList.get(0);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteFromEmptyList() {
        emptyList.delete();
    }
}
