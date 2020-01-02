package ru.job4j.generic;

public class RoleStore extends AbstractStore<Role> {
    private SimpleArray<Role> roles;

    public RoleStore(int capacity) {
        roles = new SimpleArray<>(capacity);
    }

    @Override
    protected SimpleArray<Role> getArray() {
        return roles;
    }
}
