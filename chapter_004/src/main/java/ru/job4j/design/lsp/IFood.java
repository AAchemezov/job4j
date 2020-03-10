package ru.job4j.design.lsp;

import java.util.Date;

/**
 * Интерфейс продукта.
 */
public interface IFood {

    String getName();

    Date getExpaireDate();

    Date getCreateDate();

    void setPrice(double price);

    void setDisscount(double disscount);

    double getPrice();

    double getDisscount();
}
