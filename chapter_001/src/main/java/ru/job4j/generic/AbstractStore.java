package ru.job4j.generic;

public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> store;

    AbstractStore(int capacity) {
        store = new SimpleArray<>(capacity);
    }

    @Override
    public void add(T model) {
        store.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        int index = getIndexById(id);
        if (index != -1) {
            store.set(index, model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            return store.get(index);
        }
        return null;
    }

    private int getIndexById(String id) {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
