package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimpleQueueTest {
    private ISimpleQueue<Integer> simpleQueue;

    @Before
    public void setUp() {
        simpleQueue = new QueueOnTwoStacks<>();
    }

    @Test
    public void basicFunctionalityStackTest() {
        simpleQueue.push(1);
        simpleQueue.push(2);
        assertThat(simpleQueue.poll(), equalTo(1));
        simpleQueue.push(3);
        assertThat(simpleQueue.poll(), equalTo(2));
        assertThat(simpleQueue.poll(), equalTo(3));
        assertThat(simpleQueue.poll(), equalTo(null));
        assertThat(simpleQueue.poll(), equalTo(null));
    }

}
