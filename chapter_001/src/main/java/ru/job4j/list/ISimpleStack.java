package ru.job4j.list;

/**
 * Интерфейс стека объектов
 */
public interface ISimpleStack<T> {

    /**
     * Возвращает объект и удалять его из стека
     *
     * @return объект очереди или null, если очередь пуста
     */
    T poll();

    /**
     * Добавляет значение в очередь
     */
    void push(T value);

    /**
     * Проверяет пустой ли стек
     *
     * @return true - стек пуст, иначе - false
     */
    boolean isEmpty();
}
