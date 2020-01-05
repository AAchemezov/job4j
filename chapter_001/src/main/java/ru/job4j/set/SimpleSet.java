package ru.job4j.set;

import ru.job4j.list.ArrayContainer;

import java.util.Iterator;

public class SimpleSet<E> implements Iterable<E> {
    private ArrayContainer<E> list = new ArrayContainer<>();

    /**
     * Метод добавляет значение в множество
     * * @param value значение
     *
     * @throws NullPointerException если значение рано null
     */
    public void add(E value) {
        if (value == null) {
            throw new NullPointerException();
        }
        for (E e : list) {
            if (value.equals(e)) {
                return;
            }
        }
        list.add(value);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
