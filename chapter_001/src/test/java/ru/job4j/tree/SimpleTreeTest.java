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
    private SimpleTree<Integer> emptyTree;
    private SimpleTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new SimpleTree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        iteratorTree = tree.iterator();
    }

    @Test
    public void when6ElFindLastThen6() {
        emptyTree = new SimpleTree<>(1);
        emptyTree.add(1, 2);
        emptyTree.add(1, 3);
        emptyTree.add(1, 4);
        emptyTree.add(4, 5);
        emptyTree.add(5, 6);
        assertThat(
                emptyTree.findBy(6).isPresent(),
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenAddNotUniqueValue() {
        assertThat(tree.add(1, 2), is(false));
    }

    @Test
    public void basicFunctionalityIteratorTest() {
        assertThat(iteratorTree.hasNext(), is(true));
        assertThat(iteratorTree.next(), is(1));
        assertThat(iteratorTree.next(), is(2));
        assertThat(iteratorTree.next(), is(3));
        for (int i = 0; i < 10; i++) {
            assertThat(iteratorTree.hasNext(), is(true));
        }
        assertThat(iteratorTree.next(), is(4));
        assertThat(iteratorTree.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnHasNext() {
        assertThat(tree.add(4, 5), is(true));
        iteratorTree.hasNext();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnNext() {
        assertThat(tree.add(4, 5), is(true));
        iteratorTree.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testIteratorConcurrentModificationExceptionOnForEach() {
        assertThat(tree.add(4, 5), is(true));
        iteratorTree.forEachRemaining((o) -> {
        });
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorConcurrentModificationExceptionOnRemove() {
        iteratorTree.remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void testIteratorNoSuchElementExceptionOnNext() {
        emptyTree = new SimpleTree<>(1);
        emptyTree.add(1, 2);
        Iterator<Integer> iterator = emptyTree.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    @Test
    public void testBinary() {
        assertThat(tree.isBinary(), is(true));
        tree.add(1, 5);
        assertThat(tree.isBinary(), is(false));
    }
}
