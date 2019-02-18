package ru.job4j.converter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Converter.
 * @author Andrey Chemezov
 * @version $Id$
 * @since 0.1
 */
public class ConverterTest {
    /**
     * Test: 60руб = 1дол.
     */
    @Test
    public void when60RubleToDollarThen1() {
        Converter converter = new Converter();
        int result = converter.rubleToDollar(60);
        assertThat(result, is(1));
    }

    /**
     * Test: 70руб = 1евро.
     */
    @Test
    public void when70RubleToEuroThen1() {
        Converter converter = new Converter();
        int result = converter.rubleToEuro(70);
        assertThat(result, is(1));
    }
    /**
     * Test: 2дол = 120руб.
     */
    @Test
    public void when2DollarToRubleThen120() {
        Converter converter = new Converter();
        int result = converter.dollarToRuble(2);
        assertThat(result, is(120));
    }
    /**
     * Test: 3евро = 210руб.
     */
    @Test
    public void when3EuroToRubleThen210() {
        Converter converter = new Converter();
        int result = converter.euroToRuble(3);
        assertThat(result, is(210));
    }
}
