package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public interface ITracker {
    Item add(Item item);

    void replace(String id, Item item);

    void delete(String id);

    ArrayList<Item> findAll();

    List<Item> findByName(String key);

    Item findById(String id);
}
