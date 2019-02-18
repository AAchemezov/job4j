package ru.job4j.converter;

/**
 * Корвертор валюты.
 *
 * @author Andrey Chemezov
 * @since 18.02.2019
 */
public class Converter {
    /**
     * Курс рубль-доллар
     */
    static int dollar = 60;
    /**
     * Курс рубль-евро
     */
    static int euro = 70;

    /**
     * Конвертируем рубли в евро.
     *
     * @param value рубли.
     * @return Евро.
     */
    public int rubleToEuro(int value) {
        return value / euro;
    }

    /**
     * Конвертируем рубли в доллары.
     *
     * @param value рубли.
     * @return Доллары
     */
    public int rubleToDollar(int value) {
        return value / dollar;
    }

    /**
     * Конвертируем евро в рубли.
     *
     * @param value евро.
     * @return рубли.
     */
    public int euroToRuble(int value) {
        return value * euro;
    }

    /**
     * Конвертируем доллары в рубли.
     *
     * @param value доллары.
     * @return рубли
     */
    public int dollarToRuble(int value) {
        return value * dollar;
    }
}

