package ru.job4j.calculator;

/**
 * Реализация простого калькулятора.
 * @author Andrey Chemezov
 * @since 18.02.2019
 */
public class Calculator {
    private double result;

    /**
     * Операция сложения.
     * @param first первый аргумент.
     * @param second первый аргумент.
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Операция вычитания.
     * @param first первый аргумент.
     * @param second первый аргумент.
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     * Операция деления.
     * @param first первый аргумент.
     * @param second первый аргумент.
     */
    public void div(double first, double second) {
        this.result = first / second;
    }

    /**
     * Операция умножения.
     * @param first первый аргумент.
     * @param second первый аргумент.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
     * Возврат результата последней выполненной операции.
     * @return result результат выполнения операции.
     */
    public double getResult() {
        return this.result;
    }
}

