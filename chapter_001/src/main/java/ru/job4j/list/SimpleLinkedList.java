package ru.job4j.list;

/**
 * Класс реализации односвязного списка
 */
public class SimpleLinkedList<E> {

    private int size;
    private Node<E> first;

    /**
     * Метод вставляет в начало списка данные
     */
    public void add(E data) {
        Node<E> newLink = new Node<>(data);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }

    /**
     * Метод удаления первого элемента в списке
     *
     * @throws NullPointerException если ({@link #first == null})
     */
    public E delete() {
        E data = first.data;
        first = first.next;
        size--;
        return data;
    }

    /**
     * Метод получения элемента по индексу
     *
     * @throws NullPointerException если ({@link #first == null})
     */
    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.data;
    }

    /**
     * Метод получения размера коллекции
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Класс предназначен для хранения данных
     */
    private static class Node<E> {

        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}