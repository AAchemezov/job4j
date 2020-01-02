package ru.job4j.generic;

public class UserStore extends AbstractStore<User> {
    private SimpleArray<User> users;

    public UserStore(int capacity) {
        users = new SimpleArray<>(capacity);
    }

    @Override
    protected SimpleArray<User> getArray() {
        return users;
    }
}
