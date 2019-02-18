package ru.job4j.calculator;

/**
 * Класс вычисления результата простых арифметических операций.
 * @author Andrey Chemezov
 * @since 18.02.2019
 */
public class Calculator {
    private double result;

    /**
     * Method add - операция сложения.
     * @param first - первый аргумент.
     * @param second - первый аргумент.
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Method subtract - операция вычитания.
     * @param first - первый аргумент.
     * @param second - первый аргумент.
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     * Method div - операция деления.
     * @param first - первый аргумент.
     * @param second - первый аргумент.
     */
    public void div(double first, double second) {
        this.result = first / second;
    }

    /**
     * Method multiple - операция умножения.
     * @param first - первый аргумент.
     * @param second - первый аргумент.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
     * Method getResult - возврат результата  последней выполненной операции.
     * @return result - результат выполнения операции.
     */
    public double getResult() {
        return this.result;
    }
}

