package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация универсальной обертки над массивом.
 *
 * @author Andrey Chemezov
 * @since 28.11.2019
 */
public class SimpleArray<T> implements Iterable<T> {
    private final static int DEFAULT_CAPACITY = 10;
    private Object[] objects;
    private int size;

    public SimpleArray(int capacity) {
        checkNegative(capacity);
        this.objects = new Object[capacity];
    }

    public SimpleArray() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Добавляет указанный элемент (model) в первую свободную ячейку
     *
     * @param model добавляемый элемент
     * @throws IndexOutOfBoundsException если в массиве нет свободных ячеек
     */
    public void add(T model) {
        checkSizeLimit();
        objects[size++] = model;
    }

    /**
     * Заменяет указанным элементом (model) элемент, находящийся по индексу index
     *
     * @param index индекс
     * @param model заменяющий элемент
     * @throws IllegalArgumentException  если index меньше нуля
     * @throws IndexOutOfBoundsException если индекс больше или равен числу элементов в массиве
     */
    public void set(int index, T model) {
        checkIndexLimit(index);
        objects[index] = model;
    }

    /**
     * удаляет элемент по указанному индексу
     *
     * @param index индекс
     * @return удалённый элемент
     * @throws IllegalArgumentException  если index меньше нуля
     * @throws IndexOutOfBoundsException если индекс больше или равен числу элементов в массиве
     */
    public T remove(int index) {
        checkIndexLimit(index);
        Object rem = objects[index];
        if (index < --size) {
            System.arraycopy(objects, index + 1, objects, index, size - index);
        }
        objects[size] = null;
        return (T) rem;
    }

    /**
     * Возвращает элемент, расположенный по указанному индексу
     *
     * @param index индекс
     * @return элемент в ячейке index
     * @throws IllegalArgumentException  если index меньше нуля
     * @throws IndexOutOfBoundsException если индекс больше или равен числу элементов в массиве
     */
    public T get(int index) {
        checkIndexLimit(index);
        return (T) objects[index];
    }

    /**
     * Возвращает число элементов в массиве
     *
     * @return число элементов
     */
    public int getSize() {
        return size;
    }

    /**
     * Проверяет, что массив пустой
     *
     * @return true - если число элементов равно 0, иначе - false
     */
    public boolean isEmpty() {
        return getSize() == 0;
    }

    /**
     * Контроль границ индекса элемента
     *
     * @throws IndexOutOfBoundsException если индекс больше или равен числу элементов в массиве
     */
    private void checkIndexLimit(int index) {
        checkNegative(index);
        if (index >= getSize()) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * Контроль отрицательного числа
     *
     * @throws IllegalArgumentException если число меньше нуля
     */
    private static void checkNegative(int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Контроль границ массива
     *
     * @throws IndexOutOfBoundsException если в массиве нет свободных ячеек
     */
    private void checkSizeLimit() {
        if (size == objects.length) {
            throw new IndexOutOfBoundsException(size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int caret;

            @Override
            public boolean hasNext() {
                return caret < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) objects[caret++];
            }
        };
    }
}
