package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация итератора возвращающего только четные числа.
 *
 * @author Andrey Chemezov
 * @since 26.11.2019
 */
public class EvenNumbersIterator implements Iterator {
    private int[] numbers;
    private int next;

    public EvenNumbersIterator(final int[] numbers) {
        super();
        this.numbers = numbers;
    }

    @Override
    public boolean hasNext() {
        for (int i = next; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object next() {
        for (; next < numbers.length; next++) {
            if (numbers[next] % 2 == 0) {
                return numbers[next++];
            }
        }
        throw new NoSuchElementException();
    }
}
