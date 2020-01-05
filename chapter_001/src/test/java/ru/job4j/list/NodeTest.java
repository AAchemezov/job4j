package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NodeTest {

    @Test
    public void basicFunctionalityTest1() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        assertThat(Node.hasCycle(first), is(true));
    }

    @Test
    public void basicFunctionalityTest2() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);
        first.next = two;
        two.next = third;
        third.next = two;
        four.next = null;
        assertThat(Node.hasCycle(first), is(true));
    }

    @Test
    public void basicFunctionalityTest3() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);
        first.next = two;
        two.next = third;
        third.next = two;
        four.next = first;
        assertThat(Node.hasCycle(first), is(true));
    }

    @Test
    public void basicFunctionalityTest4() {
        Node<Integer> first = new Node<>(1);
        first.next = first;
        assertThat(Node.hasCycle(first), is(true));
    }

    @Test
    public void basicFunctionalityTest5() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> two = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> four = new Node<>(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = null;
        assertThat(Node.hasCycle(first), is(false));
    }

    @Test
    public void basicFunctionalityTest6() {
        assertThat(Node.hasCycle(null), is(false));
    }
}
