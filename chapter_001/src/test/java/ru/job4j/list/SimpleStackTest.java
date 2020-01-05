package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimpleStackTest {

    private ISimpleStack<Integer> simpleStack;

    @Before
    public void setUp() {
        simpleStack = new StackOnLinkedContainer<>();
    }

    @Test
    public void basicFunctionalityStackTest() {
        assertThat(simpleStack.poll(), equalTo(null));
        simpleStack.push(1);
        simpleStack.push(2);
        simpleStack.push(3);
        assertThat(simpleStack.poll(), equalTo(3));
        assertThat(simpleStack.poll(), equalTo(2));
        assertThat(simpleStack.poll(), equalTo(1));
        assertThat(simpleStack.poll(), equalTo(null));
    }

}