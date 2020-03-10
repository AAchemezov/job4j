package ru.job4j.design.lsp.foods;

import ru.job4j.design.lsp.Food;

import java.util.Date;

/**
 * Продукт-чай
 */
public class Tea extends Food {

    private String type;

    public Tea(String name, Date createDate, Date expaireDate, double price, double disscount) {
        super(name, createDate, expaireDate, price, disscount);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
