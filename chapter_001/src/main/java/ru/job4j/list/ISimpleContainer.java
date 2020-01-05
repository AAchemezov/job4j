package ru.job4j.list;

/**
 * Интерфейс простого контейнера объектов
 */
public interface ISimpleContainer<E> extends Iterable<E> {

    /**
     * Метод добавляет элемент в контейнер
     */
    void add(E e);

    /**
     * Метод возвращает элемент из контейнера по индексу
     */
    E get(int index);
}
