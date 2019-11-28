package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация итератора итераторов.
 *
 * @author Andrey Chemezov
 * @since 26.11.2019
 */
public class Converter {

    /**
     * Возвращает итератор по элементам итератора итераторов.
     *
     * @param it итератора итераторов.
     * @return итератор по элементам итератора итераторов.
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> next;

            @Override
            public boolean hasNext() {
                while ((next == null || !next.hasNext()) && it.hasNext()) {
                    next = it.next();
                }
                return next != null && next.hasNext();
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return next.next();
            }
        };
    }
}

