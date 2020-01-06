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

    @Test
    public void basicFunctionalityTest7() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> start = first;
        Node<Integer> end = first;
        for (int i = 0; i < 100000; i++) {
            end = new Node<>(i);
            start.next = end;
            start = end;
        }
        end.next = first;

        long st, en;
        for (int i = 0; i < 10; i++) {

            st = System.nanoTime();
            Node.hasCycleMy(first);
            en = System.nanoTime();
            System.out.println("hasCycleMy:" + (en - st));

            st = System.nanoTime();
            Node.hasCycle(first);
            en = System.nanoTime();
            System.out.println("hasCycle:  " + (en - st));
        }

    }
}
