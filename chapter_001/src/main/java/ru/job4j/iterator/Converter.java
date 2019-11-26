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
                if (next != null && next.hasNext()) {
                    return true;
                }
                while (it.hasNext()) {
                    next = it.next();
                    if (next.hasNext()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return next.next();
                }
                throw new NoSuchElementException();
            }
        };
    }
}

