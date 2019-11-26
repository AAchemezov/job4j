package ru.job4j.iterator;

import java.util.Iterator;

/**
 * Реализация итератора для двухмерного массива.
 *
 * @author Andrey Chemezov
 * @since 26.11.2019
 */
public class MatrixIterator implements Iterator {
    private int[][] matrix;
    private int next;

    public MatrixIterator(int[][] matrix) {
        super();
        this.matrix = matrix;
    }

    @Override
    public boolean hasNext() {
        int sum = 0;
        for (int[] arr : matrix) {
            sum += arr.length;
            if (next < sum) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object next() {
        int sum = next;
        for (int[] arr : matrix) {
            if (sum >= arr.length) {
                sum -= arr.length;
                continue;
            }
            next++;
            return arr[sum];
        }
        return null;
    }
}
