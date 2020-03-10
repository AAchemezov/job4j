package ru.job4j.design.lsp.foods;

import ru.job4j.design.lsp.Food;

import java.util.Date;

/**
 * Продукт-хлеб
 */
public class Bread extends Food {

    private String type;

    public Bread(String name, Date createDate, Date expaireDate, double price, double disscount) {
        super(name, createDate, expaireDate, price, disscount);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
