package ru.job4j.design.lsp.foods;

import ru.job4j.design.lsp.Food;

import java.util.Date;
/**
 * Продукт-масло
 */
public class Butter extends Food {

    private double fatPercentage;

    public Butter(String name, Date createDate, Date expaireDate, double price, double disscount) {
        super(name, createDate, expaireDate, price, disscount);
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }
}
