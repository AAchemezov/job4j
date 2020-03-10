package ru.job4j.design.lsp;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Абстрактный класс выполнения действий по условию (аналог switch-case)
 */
public abstract class AbstractCaseStrategy<T> {

    private List<Strategy<T>> strategies;

    AbstractCaseStrategy() {
        this.strategies = generationStrategy();
    }

    void addStrategy(Strategy<T> strategy) {
        strategies.add(strategy);
    }

    /**
     * Метод возвращает лист стратегий.
     */
    abstract protected List<Strategy<T>> generationStrategy();

    /**
     * Выполнение стратегий для листа объектов.
     */
    public void distribution(List<T> list) {
        for (T object : list) {
            for (Strategy<T> strategy : strategies) {
                if (strategy.action(object) && strategy.isBreak()) {
                    break;
                }
            }
        }
    }

    /**
     * Класс стратегии условие-действие.
     */
    static class Strategy<T> {
        /**
         * Условие
         */
        Predicate<T> predicate;
        /**
         * Действие
         */
        Consumer<T> action;
        /**
         * Если true, то прервать следующие стратегии при выполнении условия
         */
        boolean isBreak;

        boolean isBreak() {
            return isBreak;
        }

        Strategy(Predicate<T> predicate, Consumer<T> action) {
            this(predicate, action, true);
        }

        Strategy(Predicate<T> predicate, Consumer<T> action, boolean isBreak) {
            this.predicate = predicate;
            this.action = action;
            this.isBreak = isBreak;
        }

        /**
         * Выполнение условия-действия для объекта
         *
         * @param object объект стратегии
         * @return true - условие выполнено, действие выполнено, иначе - false
         */
        boolean action(T object) {
            if (predicate.test(object)) {
                action.accept(object);
                return true;
            }
            return false;
        }
    }
}
