package ru.job4j.list;

public class StackOnLinkedContainer<E> implements ISimpleStack<E> {

    private SimpleLinkedList<E> container = new SimpleLinkedList<>();

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
