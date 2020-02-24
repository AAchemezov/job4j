package ru.job4j.tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;


public class MenuTracker {
    private Input input;
    private ITracker tracker;
    public ArrayList<UserAction> actions = new ArrayList<>();

    private static String pattern = "MM-dd-yyyy hh:mm:ss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

    MenuTracker(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    public int fillAction() {
        String nameAddItem = "Добавление новой заявки";
        this.actions.add(new MenuTracker.AddItem(0, nameAddItem));
        String nameShowItems = "Показать все заявки";
        this.actions.add(new MenuTracker.ShowItems(1, nameShowItems));
        String nameEditItem = "Редактирование заявки";
        this.actions.add(new EditItem(2, nameEditItem));
        String nameDeleteItem = "Удаление заявки";
        this.actions.add(new DeleteItem(3, nameDeleteItem));
        String nameSearchById = "Поиск заявки по ID";
        this.actions.add(new SearchById(4, nameSearchById));
        String nameSearchByName = "Поиск заявки по имени";
        this.actions.add(new SearchByName(5, nameSearchByName));
        String nameExist = "Выход из программы";
        this.actions.add(new Exit(6, nameExist));
        return actions.size();
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    public void show(Consumer<String> print) {
        for (UserAction action : this.actions) {
            if (action != null) {
                print.accept(action.info());
            }
        }
    }

    private class AddItem extends BaseAction {
        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            System.out.println("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            String description = input.ask("Введите описание заявки :");
            long time = new Date().getTime();
            Item item = new Item(name, description, time);
            tracker.add(item);
            System.out.println("------------ Добавленна новая заявка с ID: " + item.getId() + "-----------");

        }
    }

    private static class EditItem extends BaseAction {

        private EditItem(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, ITracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("-----------------Изменение заявки--------------------");
                String id = input.ask("Введите id заявки, которую нужно изменить: ");
                String name = input.ask("Введите имя новой заявки: ");
                String desc = input.ask("Введите описание новой заявки: ");
                Item item = new Item(name, desc, new Date().getTime());
                tracker.replace(id, item);
                System.out.println("------------------Изменена заявка ID " + id + "-------------------");
            }
        }
    }

    private class ShowItems extends BaseAction {

        private ShowItems(int key, String note) {
            super(key, note);
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            List<Item> items = tracker.findAll();
            System.out.println("Список всех заявок:" + items.size());
            for (Item item : items) {
                System.out.println("Имя: " + item.getName() + " ID: " + item.getId());
            }
        }
    }

    private class SearchById extends BaseAction {

        private SearchById(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, ITracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("-------------------Поиск заявки--------------------");
                String id = input.ask("Введите id заявки, которую нужно найти: ");
                Item item = tracker.findById(id);
                if (item != null) {
                    System.out.println("Заявка id: " + item.getId() + ", name: " + item.getName()
                            + ", description: " + item.getDescription() + ", created: " + dateFormat.format(item.getCreate()));
                } else {
                    System.out.println("Заявка не найдена");
                }
                System.out.println("-------------------Поиск заявки завершен----------------------");
            }
        }
    }

    private class SearchByName extends BaseAction {

        private SearchByName(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, ITracker tracker) {
            if (tracker.findAll().isEmpty()) {
                System.out.println("Список заявок пуст");
            } else {
                System.out.println("---------------------Поиск заявок по имени---------------------");
                String name = input.ask("Введите name заявки, которую нужно найти: ");
                List<Item> items = tracker.findByName(name);
                if (items.size() > 0) {
                    for (Item item : items) {
                        System.out.println("Заявка id: " + item.getId() + ", name: " + item.getName()
                                + ", description: " + item.getDescription() + ", created: " + dateFormat.format(item.getCreate()));
                    }
                } else {
                    System.out.println("Заявки с именем " + name + " не найдены");
                }
                System.out.println("---------------------Конец списка-------------------------");
            }
        }
    }

    private class Exit extends BaseAction {

        private Exit(int key, String note) {
            super(key, note);
        }

        public void execute(Input input, ITracker tracker) {
        }
    }

    private class DeleteItem extends BaseAction {

        public DeleteItem(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, ITracker tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String question = input.ask("Введите ID удаяемой заявки :");
            Item item = tracker.findById(question);
            if (item != null) {
                tracker.delete(question);
            } else {
                System.out.println("Заявка не найдена, выберите другой ID");
            }
        }
    }
}

