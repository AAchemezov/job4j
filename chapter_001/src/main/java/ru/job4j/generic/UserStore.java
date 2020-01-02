package ru.job4j.generic;

public class UserStore extends AbstractStore<User> {

    UserStore(int capacity) {
        super(capacity);
    }
}
