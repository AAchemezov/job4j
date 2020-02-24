package ru.job4j.tracker;

import java.util.Objects;

public class Item {
    private String name;
    private String description;
    private long create;
    private String id;

    public Item(String name, String description, long create) {
        this.name = name;
        this.description = description;
        this.create = create;
    }

    public Item(String id, String name, String description, long create) {
        this(name, description, create);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public long getCreate() {
        return this.create;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return create == item.create
                && Objects.equals(name, item.name)
                && Objects.equals(description, item.description)
                && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, create, id);
    }
}