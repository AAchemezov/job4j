package ru.job4j.design.lsp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Класс обработчик перераспределения продуктов в место использования.
 * Класс должен перераспределять еду по хранилищам в зависимости от условиый.
 * <p>
 * 1. Если срок годности израсходован меньше чем на 25% направить в Warehouse.
 * 2 Если срок годности от 25% до 75% направить в Shop
 * 3. Если срок годности больше 75% то выставить скидку на продукт и отправить в Shop
 * 4. Если срок годности вышел. Отправить продукт в мусорку.
 */
public class ControlQuality extends AbstractCaseStrategy<IFood> {

    private final static double TO_WAREHOUSE = 25;
    private final static double TO_SHOP = 75;
    private final static double TO_DISSCOUNT = 100;
    private final static double DISSCOUNT = .1;

    private FoodStore warehouse;
    private FoodStore shop;
    private FoodStore trash;
    private Date forDate;

    /**
     * Конструктор распределителя.
     *
     * @param warehouse хранилище
     * @param shop      магазин
     * @param trash     мусор
     */
    ControlQuality(FoodStore warehouse, FoodStore shop, FoodStore trash) {
        super();
        this.warehouse = warehouse;
        this.shop = shop;
        this.trash = trash;
        setForDate(new Date());
    }

    /**
     * Установить дату, для которой производится распределение.
     *
     * @param forDate дата распределения
     */
    void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    @Override
    protected List<Strategy<IFood>> generationStrategy() {
        List<Strategy<IFood>> strategies = new LinkedList<>();
        strategies.add(getShelfLifeStrategy(percent -> percent < TO_WAREHOUSE, food -> warehouse.addFood(food)));
        strategies.add(getShelfLifeStrategy(percent -> percent <= TO_SHOP, food -> shop.addFood(food)));
        strategies.add(getShelfLifeStrategy(percent -> percent < TO_DISSCOUNT,
                food -> {
                    food.setDisscount(DISSCOUNT);
                    shop.addFood(food);
                }));
        strategies.add(getShelfLifeStrategy(percent -> true, food -> trash.addFood(food)));
        return strategies;
    }

    /**
     * Возвращает стратегию, условие которой зависит от процента срока годности продукта
     *
     * @param percentPredicate сколько процентов срока годности прошло
     * @param consumer         действие над продуктом
     * @return стратегия
     */
    private Strategy<IFood> getShelfLifeStrategy(Predicate<Double> percentPredicate, Consumer<IFood> consumer) {
        return new Strategy<>(
                food -> percentPredicate.test(percentOfShelfLife(food.getCreateDate(), food.getExpaireDate(), this.forDate)),
                consumer
        );
    }

    /**
     * возврашает в процентах сколько прошло от начала до конца периода, где now - текущее положение.
     *
     * @param start начало перида
     * @param end   конец периода
     * @param now   текущее положение
     * @return сколько процентов периода прошло([-1] - если период задан неверно)
     */
    private static double percentOfShelfLife(Date start, Date end, Date now) {
        if (start.after(end)) {
            return -1;
        }
        if (start.after(now)) {
            return 0;
        }
        long start2end = end.getTime() - start.getTime();
        long start2now = now.getTime() - start.getTime();

        if (start2now == 0) {
            return 0;
        }
        if (start2end == 0) {
            return 100;
        }
        return Math.min(100, start2now * 100.0 / start2end);
    }
}
