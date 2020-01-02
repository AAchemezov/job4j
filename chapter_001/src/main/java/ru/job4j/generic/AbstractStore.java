package ru.job4j.generic;

public abstract class AbstractStore<T extends Base> implements Store<T> {

    abstract protected SimpleArray<T> getArray();

    @Override
    public void add(T model) {
        getArray().add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        int index = getIndexById(id);
        if (index != -1) {
            getArray().set(index, model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            getArray().remove(index);
            return true;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            return getArray().get(index);
        }
        return null;
    }

    private int getIndexById(String id) {
        for (int i = 0; i < getArray().getSize(); i++) {
            if (getArray().get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
