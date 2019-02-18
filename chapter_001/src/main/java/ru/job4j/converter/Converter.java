package ru.job4j.converter;

/**
 * Корвертор валюты.
 *
 * @author Andrey Chemezov
 * @since 18.02.2019
 */
public class Converter {

    /** Курс рубль-доллар. */
    static final int DOLLAR = 60;
    /**
     * Курс рубль-евро.
     */
    static final int EURO = 70;

    /**
     * Конвертируем рубли в евро.
     *
     * @param value рубли.
     * @return Евро.
     */
    public int rubleToEuro(int value) {
        return value / EURO;
    }

    /**
     * Конвертируем рубли в доллары.
     *
     * @param value рубли.
     * @return Доллары
     */
    public int rubleToDollar(int value) {
        return value / DOLLAR;
    }

    /**
     * Конвертируем евро в рубли.
     *
     * @param value евро.
     * @return рубли.
     */
    public int euroToRuble(int value) {
        return value * EURO;
    }

    /**
     * Конвертируем доллары в рубли.
     *
     * @param value доллары.
     * @return рубли
     */
    public int dollarToRuble(int value) {
        return value * DOLLAR;
    }
}

