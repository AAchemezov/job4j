package ru.job4j.list;

public class Node<T> {
    private static int cycleFindIndex;
    private int cycleIndex;

    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }

    /**
     * Реализация алгоритма, определяющего что список содержит замыкания
     */
    static boolean hasCycle(Node first) {
        cycleFindIndex++;
        while (first != null) {
            if (first.cycleIndex == cycleFindIndex) {
                return true;
            } else {
                first.cycleIndex = cycleFindIndex;
                first = first.next;
            }
        }
        return false;
    }
}
