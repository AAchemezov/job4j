package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashMap<K, V> implements IHashMap<K, V>, Iterable<SimpleHashMap.Node<K, V>> {

    private Node<K, V>[] map;
    private int capacity = 8;
    private int size = 0;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    SimpleHashMap() {
        map = (Node<K, V>[]) new Node[capacity];
    }

    @Override
    public boolean insert(K key, V value) {
        if (key == null) {
            return false;
        }
        int i = getIndex(key, capacity);
        if (map[i] == null) {
            map[i] = new Node<>(key, value);
            size++;
            modCount++;
            if (0.7 < ((float) size) / capacity) {
                capacity *= 2;
                map = reInsert(map, capacity);
            }
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int i = getIndex(key, capacity);
        if (map[i] != null) {
            return map[i].value;
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        if (key == null) {
            return false;
        }
        int i = getIndex(key, capacity);
        if (map[i] != null) {
            map[i] = null;
            modCount++;
            size--;
            return true;
        }
        return false;
    }

    /**
     * Возврашает новую хеш-таблицу с элементами старой. Некоторые данные могут быть утеряны при перехешировании.
     *
     * @param mapOld  старый массив
     * @param newSize емкость нового массива
     * @return новый массив
     */
    private static <K, V> Node<K, V>[] reInsert(Node<K, V>[] mapOld, int newSize) {
        @SuppressWarnings("unchecked")
        Node<K, V>[] mapNew = (Node<K, V>[]) new Node[newSize];
        for (Node<K, V> kvNode : mapOld) {
            if (kvNode != null) {
                mapNew[getIndex(kvNode.key, newSize)] = kvNode;
            }
        }
        return mapNew;
    }

    private static <K> int getIndex(K key, int capacity) {
        return Math.abs(key.hashCode() % capacity);
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
            private int expectedModCount = modCount;
            private int next = 0;
            private int colElements = 0;

            @Override
            public boolean hasNext() {
                checkModification();
                if (colElements != size) {
                    for (; next < capacity; next++) {
                        if (map[next] != null) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public Node<K, V> next() {
                checkModification();
                checkNextElement();
                colElements++;
                return new Node<>(map[next].key, map[next++].value);
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

    static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
