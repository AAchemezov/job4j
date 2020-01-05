package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация простого контейнерана базе связанного списка.
 *
 * @author Andrey Chemezov
 * @since 03.01.2020
 */
public class LinkedContainer<E> implements ISimpleContainer<E> {

    int modCount = 0;
    private Node<E> first;
    private Node<E> last;

    @Override
    public void add(E o) {
        modCount++;
        if (last == null || first == null) {
            last = new Node<>(null, null, o);
            first = last;
        } else {
            last = new Node<>(null, last, o);
            last.prev.next = last;
        }
    }

    @Override
    public E get(int index) {
        Node<E> caret = first;
        int i = 0;
        while (caret != null) {
            if (i == index) {
                return caret.data;
            }
            i++;
            caret = caret.next;
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            private int expectedModCount = modCount;
            private Node<E> carr = first;

            @Override
            public boolean hasNext() {
                checkModification();
                return carr != null;
            }

            @Override
            public E next() {
                checkModification();
                checkNextElement();
                E data = carr.data;
                carr = carr.next;
                return data;
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
             * Проверка доступности следующего элемента коллекции
             * @throws NoSuchElementException если следующий элемент не существует
             */
            private void checkNextElement() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    protected static class Node<E> {
        Node<E> next;
        Node<E> prev;
        E data;

        Node(Node<E> next, Node<E> prev, E data) {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }
    }
}
