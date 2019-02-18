package ru.job4j.condition;
/**
 * Класс Point описывает точку в системе координат. Задача "3.4. Расстояние между точками в системе координат[#112803]".
 * @author Andrey Chemezov.
 * @since 18.02.2019
 */
public class Point {
    private final int  x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Рассчёт расстояния от данной точки до другой точки.
     * @param that точка, до которой следует рассчитать расстояние.
     * @return  расстояние между точками.
     */
    public double distanceTo(Point that) {
        return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
    }

    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        System.out.println("x1 = " + a.x);
        System.out.println("y1 = " + a.y);
        System.out.println("x2 = " + b.x);
        System.out.println("y2 = " + b.y);
        double result = a.distanceTo(b);
        System.out.println("Расстояние между точками А и В : " + result);
    }
}