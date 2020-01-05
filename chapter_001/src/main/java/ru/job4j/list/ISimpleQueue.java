package ru.job4j.list;

/**
 * Интерфейс очереди объектов
 */
public interface ISimpleQueue<T> {
    /**
     * Возвращает объект и удалять его из очереди
     *
     * @return ОБъект очереди или null, если очередь пуста
     */
    T poll();

    /**
     * Добавляет значение в очередь
     */
    void push(T value);

    /**
     * Проверяет пустая ли очередь
     *
     * @return true - очередь пуста, иначе - false
     */
    boolean isEmpty();
}
