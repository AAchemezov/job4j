package ru.job4j.map;

public interface IHashMap<K, V> {
    /**
     * Добавить объект value в карту по ключу key
     *
     * @param key   ключ
     * @param value значение
     * @return <code>true</code> при успешном добавлении, иначе - <code>false</code>
     */
    boolean insert(K key, V value);

    /**
     * Получить объект value из карты по ключу key
     *
     * @param key ключ
     * @return <code>value</code> при наличиии записи по переданному ключу, иначе - <code>null</code>
     */
    V get(K key);

    /**
     * Удалить объект value из карты по ключу key
     *
     * @param key ключ
     * @return <code>true</code> при успешном удалении, иначе - <code>false</code>
     */
    boolean delete(K key);

}
