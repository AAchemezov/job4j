package ru.job4j.list;

public class StackOnLinkedContainer<E> implements ISimpleStack<E> {

    private SimpleArrayList<E> container = new SimpleArrayList<>();

    @Override
    public E poll() {
        return isEmpty() ? null : container.delete();
    }

    @Override
    public void push(E value) {
        container.add(value);
    }

    @Override
    public boolean isEmpty() {
        return container.getSize() == 0;
    }
}
