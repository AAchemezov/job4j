package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация простого контейнера на базе массива.
 *
 * @author Andrey Chemezov
 * @since 03.01.2020
 */
public class ArrayContainer<E> implements ISimpleContainer<E> {

    private final static int MIN_CAPACITY = 3;
    private final static int DEFAULT_CAPACITY = 10;
    private final static float DEFAULT_MULTIPLIER = 1.5f;

    private int capacity;
    private int size = 0;
    private int modCount = 0;
    private Object[] values;

    public ArrayContainer() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayContainer(int capacity) {
        checkNegativeCapacity(capacity);
        this.capacity = capacity;
        values = new Object[capacity];
    }

    @Override
    public void add(E o) {
        modCount++;
        if (checkCapacity()) {
            extendCapacity();
        }
        values[size++] = o;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) values[index];
    }

    /**
     * Отрицательного значения ёмкости массива
     *
     * @throws IllegalArgumentException если значение ёмкости отрицательное
     */
    private static void checkNegativeCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity + ". Capacity must be >= 0");
        }
    }

    /**
     * Проверка заполненности хранящего массива
     *
     * @return true - если массив заполнен
     */
    private boolean checkCapacity() {
        return capacity == size;
    }

    /**
     * Расширение ёмкости хранящего массива
     */
    private void extendCapacity() {
        int newCapacity = capacity < MIN_CAPACITY ? MIN_CAPACITY : Math.round(capacity * DEFAULT_MULTIPLIER);
        Object[] extValues = new Object[newCapacity];
        System.arraycopy(values, 0, extValues, 0, capacity);
        values = extValues;
        capacity = newCapacity;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int expectedModCount = modCount;
            private int next = 0;

            @Override
            public boolean hasNext() {
                checkModification();
                return next < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                checkModification();
                checkNextElement();
                return (E) values[next++];
            }

            /**
             * Проверка изменения колленкции с момента создания итератора
             * @throws ConcurrentModificationException если колекция была модифицирована
             */
            private void checkModification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }

            /**
             * Проверка доступности следующего елемента коллекции
             * @throws NoSuchElementException если следующий элемент не существует
             */
            private void checkNextElement() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
