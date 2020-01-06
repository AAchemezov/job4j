package ru.job4j.list;

public class Node<T> {
//    private static int cycleFindIndex;
//    private int cycleIndex;

    T value;
    Node<T> next;

    Node(T value) {
        this.value = value;
    }

//    /**
//     * Реализация алгоритма, определяющего что список содержит замыкания
//     */
//    static boolean hasCycleMy(Node first) {
//        cycleFindIndex++;
//        while (first != null) {
//            if (first.cycleIndex == cycleFindIndex) {
//                return true;
//            } else {
//                first.cycleIndex = cycleFindIndex;
//                first = first.next;
//            }
//        }
//        return false;
//    }

    /**
     * Реализация алгоритма, определяющего что список содержит замыкания
     */
    static boolean hasCycle(Node first) {
        Node rabbit = first;
        Node turtle = first;
        boolean upSpeed = false;
        while (rabbit != null) {
            rabbit = rabbit.next;
            if (upSpeed) {
                turtle = turtle.next;
            }
            upSpeed = !upSpeed;
            if (rabbit == turtle) {
                return true;
            }
        }
        return false;
    }
}
