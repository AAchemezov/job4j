package ru.job4j.design.lsp;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.design.lsp.foods.Bread;
import ru.job4j.design.lsp.foods.Butter;
import ru.job4j.design.lsp.foods.Tea;
import ru.job4j.design.lsp.foodstores.Shop;
import ru.job4j.design.lsp.foodstores.Trash;
import ru.job4j.design.lsp.foodstores.Warehouse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FoodStoreTest {
    private Warehouse warehouse;
    private Shop shop;
    private Trash trash;
    private List<IFood> foods;
    private DateFormat sDate = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    private IFood bread;
    private IFood baguette;
    private IFood butter;
    private IFood tea;

    @Before
    public void setUp() throws ParseException {

        warehouse = new Warehouse();
        shop = new Shop();
        trash = new Trash();

        bread = new Bread("Багет",
                sDate.parse("01.01.2020"),
                sDate.parse("15.01.2020"), 50, 0);
        baguette = new Bread("Хлеб белый",
                sDate.parse("01.01.2020"),
                sDate.parse("31.01.2020"), 30, 0);
        butter = new Butter("Сливочное масло",
                sDate.parse("01.01.2020"),
                sDate.parse("15.03.2020"), 120, 0);
        tea = new Tea("Индийский чай",
                sDate.parse("01.01.2020"),
                sDate.parse("31.10.2020"), 50, 0);

        foods = List.of(bread, baguette, butter, tea);
    }

    private void distributionForQualityDate(Date date) {
        ControlQuality quality = new ControlQuality(warehouse, shop, trash);
        quality.setForDate(date);
        quality.distribution(foods);
    }

    private void assertStoreSizes(int warehouseZ, int shopZ, int trashZ) {
        assertThat(warehouse.size(), is(warehouseZ));
        assertThat(shop.size(), is(shopZ));
        assertThat(trash.size(), is(trashZ));
    }

    @Test
    public void whenAllInWarehouse() throws ParseException {
        distributionForQualityDate(sDate.parse("01.01.2020"));
        assertStoreSizes(4, 0, 0);
        assertThat(warehouse.contain(bread), is(true));
        assertThat(warehouse.contain(baguette), is(true));
        assertThat(warehouse.contain(butter), is(true));
        assertThat(warehouse.contain(tea), is(true));
    }

    @Test
    public void whenInAllStores() throws ParseException {
        distributionForQualityDate(sDate.parse("15.01.2020"));
        assertStoreSizes(2, 1, 1);
        assertThat(warehouse.contain(butter), is(true));
        assertThat(warehouse.contain(tea), is(true));
        assertThat(shop.contain(baguette), is(true));
        assertThat(trash.contain(bread), is(true));
    }

    @Test
    public void whenButterDiscount() throws ParseException {
        distributionForQualityDate(sDate.parse("10.03.2020"));
        assertStoreSizes(1, 1, 2);
        assertThat(shop.contain(butter), is(true));
        assertThat(butter.getDisscount(), is(0.1));
    }

    @Test
    public void whenAllInTrash() throws ParseException {
        distributionForQualityDate(sDate.parse("01.01.2021"));
        assertStoreSizes(0, 0, 4);
        assertThat(trash.contain(bread), is(true));
        assertThat(trash.contain(baguette), is(true));
        assertThat(trash.contain(butter), is(true));
        assertThat(trash.contain(tea), is(true));
    }
}
