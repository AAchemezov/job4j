package ru.job4j.tracker;

public class StartUI {
    private final Input input;
    private final Tracker tracker;

    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public void init() {
        MenuTracker menu = new MenuTracker(input, tracker);
        int rangeValue = menu.fillAction();
        int[] range = new int[rangeValue];
        fillRange(range);
        int key;
        do {
            menu.show(System.out::println);
            key = input.ask("Выбор. ", range);
            menu.select(key);
        } while (key != 6);
    }

    private void fillRange(int[] range) {
        for (int value = 0; value < range.length; value++) {
            range[value] = value;
        }
    }

    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}
