package ru.job4j.design.lsp;

import java.util.Date;

/**
 * Продукт
 */
public class Food implements IFood {

    private String name;
    private Date expaireDate;
    private Date createDate;
    private double price;
    private double disscount;

    public Food(String name, Date createDate, Date expaireDate, double price, double disscount) {
        this.name = name;
        this.expaireDate = expaireDate;
        this.createDate = createDate;
        this.price = price;
        this.disscount = disscount;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setDisscount(double disscount) {
        this.disscount = disscount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getExpaireDate() {
        return expaireDate;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getDisscount() {
        return disscount;
    }
}
