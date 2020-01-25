package ru.job4j.tree;

import java.util.*;

public class SimpleTree<E extends Comparable<E>> implements ISimpleTree<E> {
    final private Node<E> root;
    private int modCount = 0;

    public SimpleTree(E e) {
        root = new Node<>(e);
    }

    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> childNode = findBy(child);
        if (!childNode.isPresent()) {
            Optional<Node<E>> parentNode = findBy(parent);
            if (parentNode.isPresent()) {
                parentNode.get().add(new Node<>(child));
                modCount++;
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    /**
     * Метод  проверяет количество дочерних элементов в дереве. Если их <= 2 - то дерево бинарное.
     *
     * @return <code>true</code> - дерево бинарное, иначе <code>false</code>
     */
    public boolean isBinary() {
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(root);
        while (!data.isEmpty()) {
            List<Node<E>> leaves = data.poll().leaves();
            if (leaves.size() > 2) {
                return false;
            }
            for (Node<E> child : leaves) {
                data.offer(child);
            }
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            Node<E> node = root;
            Queue<Node<E>> data = new LinkedList<>() {
                {
                    offer(node);
                }
            };

            @Override
            public boolean hasNext() {
                checkModification();
                return !data.isEmpty();
            }

            @Override
            public E next() {
                checkModification();
                checkNextElement();
                Node<E> result = data.poll();
                for (Node<E> child : result.leaves()) {
                    data.offer(child);
                }
                return result.getValue();
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

}
