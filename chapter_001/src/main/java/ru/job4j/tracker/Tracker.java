package ru.job4j.tracker;

import java.util.*;
import java.util.stream.Collectors;

public class Tracker implements ITracker {

    private ArrayList<Item> items = new ArrayList<>();

    private int position = 0;

    private static final Random RN = new Random();

    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(position++, item);
        return item;
    }

    public void replace(String id, Item item) {
        for (Item i : items) {
            if (i.getId().equals(id)) {
                item.setId(i.getId());
                items.remove(i);
                items.add(item);
                break;
            }
        }
    }

    public void delete(String id) {
        for (Item index : items) {
            if (index.getId().equals(id)) {
                items.remove(index);
                break;
            }
        }
    }

    public ArrayList<Item> findAll() {
        return this.items;
    }

    public List<Item> findByName(String key) {
        return this.items.stream().filter(i -> (i.getName().equals(key))).collect(Collectors.toList());
    }

    public Item findById(String id) {
        return this.items.stream().filter(i -> (i.getId().equals(id))).findFirst().orElse(null);
    }

    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
}