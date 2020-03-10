package ru.job4j.design.lsp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Хранилище продуктов.
 */
public class FoodStore {
    private List<IFood> foods;

    public FoodStore() {
        this(new LinkedList<>());
    }

    public FoodStore(List<IFood> foods) {
        this.foods = foods;
    }

    public boolean contain(IFood o) {
        return foods.contains(o);
    }

    public int size() {
        return foods.size();
    }

    public Iterator<IFood> getFoodIterator() {
        return foods.iterator();
    }

    boolean addFood(IFood food) {
        return foods.add(food);
    }

    boolean removeFood(IFood food) {
        return foods.remove(food);
    }

}
