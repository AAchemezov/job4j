package ru.job4j.list;

public class QueueOnTwoStacks<E> implements ISimpleQueue<E> {

    private ISimpleStack<E> leftStack = new StackOnLinkedContainer<>();
    private ISimpleStack<E> rigthStack = new StackOnLinkedContainer<>();

    @Override
    public E poll() {
        if (!rigthStack.isEmpty()) {
            return rigthStack.poll();
        }
        while (!leftStack.isEmpty()) {
            rigthStack.push(leftStack.poll());
        }
        return rigthStack.poll();
    }

    @Override
    public void push(E value) {
        leftStack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return leftStack.isEmpty() && rigthStack.isEmpty();
    }
}
