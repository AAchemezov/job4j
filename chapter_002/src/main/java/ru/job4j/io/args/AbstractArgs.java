package ru.job4j.io.args;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Абстрактный класс обработки строковых параметров.
 * Поддерживаются возможности подготовки строковых параметров, валидации и маппинга в соответствующие объекты
 * через использование типизированного класса аргумента {@link Argument}.
 */
abstract public class AbstractArgs {
    // Начальные данные
    private final List<String> startData;
    //то, что из начальных данных не было распознано
    private List<String> trashData;
    // карта с аргументами и их именами
    private Map<String, Argument> argumentMap;

    /**
     * Конструктор
     *
     * @param args массив аргументов в виде {"a1", "value1", "a2", "a3", "value3",...}.
     *             "a1", "a3" - это параметры со значением,
     *             "a2" - параметр без значения (флаг)
     */
    public AbstractArgs(String... args) {
        startData = Arrays.asList(args);
    }

    /**
     * Выполняет инициализацию агрументов.
     * Метод может быть вызван в теле конструктора класса наследника,
     * или после вызова конструктора для обеспечения ленивой инициализации.
     */
    public void initArguments() {
        trashData = new ArrayList<>();
        Argument initArg = null;
        for (String arg : startData) {
            if (getArgumentMap().containsKey(arg)) {
                initArg = getArgumentMap().get(arg);
                if (initArg.isWithoutStartingValue()) {
                    initArg.init("");
                    initArg = null;
                }
            } else {
                if (initArg != null) {
                    initArg.init(arg);
                    initArg = null;
                } else {
                    trashData.add(arg);
                }
            }
        }
    }

    /**
     * Возвращает карту <{@link Argument#name} ,{@link Argument}>.
     *
     * @return карта соотношений имён аргументов и их объектов
     */
    private Map<String, Argument> getArgumentMap() {
        if (argumentMap == null) {
            argumentMap = getArguments().stream().collect(Collectors.toMap(Argument::getName, argument -> argument));
        }
        return argumentMap;
    }

    /**
     * Метод получения множества агрументов {@link Argument}.
     * Определяется в наследниках.
     * Не допускаются null элементы и дубликаты.
     */
    abstract protected Set<Argument> getArguments();

    /**
     * Возвращает объект (T) из аргумента
     *
     * @param argument аргумент {@link Argument}
     * @return T object, null - если argument == null или argument.getValue() == null
     */
    protected static <T> T getValueArgument(Argument<T> argument) {
        return argument == null ? null : argument.getValue();
    }

    /**
     * Проверяет, невалидность аргумента
     *
     * @return true - в случаях:
     * агрумент == null,
     * агрумент не был инициализирован,
     * агрумент не прошел валидацию функцией валидации;
     * иначе - false
     */
    protected static boolean isValidArgument(Argument argument) {
        return argument != null && argument.isValid();
    }

    /**
     * Проверяет, что аргумент был инициализирован
     *
     * @return true - если агрумент != null и был инициализирован, иначе - false
     */
    protected static boolean isInitArgument(Argument argument) {
        return argument != null && argument.isInit();
    }

    /**
     * Возвращает опционал с аргументом по имени аргумента
     *
     * @param name имя аргумента
     * @return опционал аргумента с именем name
     */
    protected Optional<Argument> getArgumentByArgumentName(String name) {
        return Optional.of(getArgumentMap().get(name));
    }

    /**
     * Проверяет, валидны ли все аргументы. По умолчанию проверяет все переданные аргумены.
     * Для реализации взаимоисключающих аргументов следует переопределить в наследниках.
     *
     * @return true - все аргументы прошли валидацию, иначе - false
     */
    public boolean isAllArgumentsValid() {
        for (Argument argument : getArgumentMap().values()) {
            if (!isValidArgument(argument)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает лист имен всех невалидных аргументов
     */
    public ArrayList<String> getInValidArguments() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Argument argument : getArgumentMap().values()) {
            if (!isValidArgument(argument)) {
                arrayList.add(argument.name);
            }
        }
        return arrayList;
    }

    /**
     * Возвращает лист всех текстовых параметров, которые не были распознаны
     */
    public List<String> getTrashData() {
        return trashData;
    }

    /**
     * Возвращает лист имен всех неинициализированных аргументов
     */
    public ArrayList<String> getNotInitArguments() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Argument argument : getArgumentMap().values()) {
            if (!isInitArgument(argument)) {
                arrayList.add(argument.name);
            }
        }
        return arrayList;
    }

    /**
     * Класса аргумента.
     * Поддерживаются возможности подготовки, валидации и маппинга строкового параметра в соответствующий ей объект.
     */
    protected static class Argument<T> {
        private final String name;

        private Function<String, String> preparing;
        private Predicate<String> validating;
        private Function<String, T> mapping;

        private boolean withoutStartingValue = false;
        private boolean valid = false;
        private boolean init = false;
        private T value;

        /**
         * Конструктор аргумента
         *
         * @param name имя аргумента
         * @throws IllegalArgumentException если имя == null,или пустое
         */
        public Argument(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name must not null and not empty.");
            }
            this.name = name;
        }

        /**
         * Конструктор аргумента
         *
         * @param name                 имя аргумента
         * @param withoutStartingValue если истина, то аргумент не ожидает строкого значения параметра (аргумент - флаг)
         * @param preparing            функция предобработки папаметра
         * @param validating           предикат валидации параметра
         * @param mapping              функция преобразования параметра в тип T
         * @throws IllegalArgumentException если name == null или пустое
         */
        protected Argument(String name, boolean withoutStartingValue, Function<String, String> preparing, Predicate<String> validating, Function<String, T> mapping) {
            this(name);
            setWithoutStartingValue(withoutStartingValue).setPreparing(preparing).setMapping(mapping).setValidating(validating);
        }

        /**
         * Установить функцию подготовки строки аргумента
         *
         * @return this
         */
        public Argument<T> setPreparing(Function<String, String> preparing) {
            this.preparing = preparing;
            return this;
        }

        /**
         * Установить функцию валидации строки аргумента
         *
         * @return this
         */
        public Argument<T> setValidating(Predicate<String> validating) {
            this.validating = validating;
            return this;
        }

        /**
         * Установить функцию преобразования строкового аргумента в объект <T>
         *
         * @return this
         */
        public Argument<T> setMapping(Function<String, T> mapping) {
            this.mapping = mapping;
            return this;
        }

        /**
         * Установить флаг, определяющий, что данный аргумента не имеет строкового значения.
         * При инициализации в качестве строкового параметра будет передана пустая строка - "".
         *
         * @return this
         */
        protected Argument<T> setWithoutStartingValue(boolean noValue) {
            this.withoutStartingValue = noValue;
            return this;
        }

        /**
         * Инициализация аргумента.
         * Порядок инициализации:
         * 1. подготовка строки
         * 2. валидация
         * 3. маппинг в объект
         * <p>
         * Если не задана функция подготовки, то строка передаётся без подготовки
         * Если не задана функция валидации, то аргумент считается валидным
         * Если не задана функция маппинга, то возвращаемое значение == null
         */
        private void init(String param) {
            init = true;
            String sValue = preparing == null ? param : preparing.apply(param);
            valid = validating == null || validating.test(sValue);
            value = valid && mapping != null ? mapping.apply(sValue) : null;
        }

        public boolean isInit() {
            return init;
        }

        /**
         * Возвращает имя аргумента
         */
        public String getName() {
            return name;
        }

        /**
         * Возвращает true, если аргумент валидный, иначе - false
         */
        boolean isValid() {
            return valid;
        }

        /**
         * Возвращает значение аргумента
         */
        T getValue() {
            return value;
        }

        /**
         * Возвращает true, если аргумент не ожидает наличия строкового параметра при инициализации (аргумент - флаг)
         */
        boolean isWithoutStartingValue() {
            return withoutStartingValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Argument<?> argument = (Argument<?>) o;
            return name.equals(argument.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
