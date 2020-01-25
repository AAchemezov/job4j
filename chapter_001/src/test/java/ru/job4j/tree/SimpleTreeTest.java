package ru.job4j.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class SimpleTreeTest {
    private Iterator<Integer> iteratorTree;
    private SimpleTree<Integer> simpleTree;

    @Before
    public void setUp() {
        simpleTree = new SimpleTree<>(1);
        simpleTree.add(1, 2);
        simpleTree.add(1, 3);
        simpleTree.add(1, 4);
        simpleTree.add(4, 5);
        simpleTree.add(5, 6);
        iteratorTree = simpleTree.iterator();
    }

    @Test
    public void when6ElFindLastThen6() {
        assertThat(
                simpleTree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        assertThat(
                simpleTree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenAddNotUniqueValue() {
        assertThat(simpleTree.add(1, 2), is(false));
        assertThat(simpleTree.add(1, 3), is(false));
        assertThat(simpleTree.add(1, 4), is(false));
        assertThat(simpleTree.add(4, 5), is(false));
        assertThat(simpleTree.add(5, 6), is(false));
        assertThat(simpleTree.add(1, 1), is(false));
        assertThat(simpleTree.add(6, 8), is(true));
    }

    @Test
    public void basicFunctionalityIteratorTest() {
        for (int i = 0; i < 20; i++) {
            assertThat(iteratorTree.hasNext(), is(true));
        }
        assertThat(iteratorTree.next(), is(1));
        assertThat(iteratorTree.next(), is(2));
        assertThat(iteratorTree.next(), is(3));
        assertThat(iteratorTree.next(), is(4));
        assertThat(iteratorTree.next(), is(5));
        for (int i = 0; i < 20; i++) {
            assertThat(iteratorTree.hasNext(), is(true));
        }
        assertThat(iteratorTree.next(), is(6));
        for (int i = 0; i < 20; i++) {
            assertThat(iteratorTree.hasNext(), is(false));
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnHasNext() {
        iteratorTree.hasNext();
        assertThat(simpleTree.add(4, 10), is(true));
        iteratorTree.hasNext();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnNext() {
        iteratorTree.next();
        assertThat(simpleTree.add(4, 10), is(true));
        iteratorTree.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnForEach() {
        assertThat(simpleTree.add(4, 10), is(true));
        iteratorTree.forEachRemaining((o) -> {
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorConcurrentModificationExceptionOnRemove() {
        iteratorTree.remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementExceptionOnNext() {
        iteratorTree.next();
        iteratorTree.next();
        iteratorTree.next();
        iteratorTree.next();
        iteratorTree.next();
        iteratorTree.next();
        iteratorTree.next();
    }
}
