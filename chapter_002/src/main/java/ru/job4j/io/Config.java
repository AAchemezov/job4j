package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Загружает конфигурацию из файла и ппредоставляет доступ к ней.
 * Конфигурация представляется в виде пары 'ключ=значение'.
 */
public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    /**
     * Конструктор
     *
     * @param path путь до файла конфигурации
     */
    public Config(final String path) {
        this.path = path;
    }

    /**
     * Выполняет загрузку конфигурации из файла, путь до которого передан в конструкторе. Выполняет маппинг строк файла.
     * Символ '#' определяет комментарий до конца строки.
     * Прервый символ '=' является разделителем имени ключа и значения конфигурации.
     * Допускаются: 'ключ=', ключ=#комментарий',
     * [пробельные символы]ключ[пробельные символы]=[пробельные символы]значение[пробельные символы],
     * Не учитываются: '=Значение'.
     * ключ==значение=> {ключ}{=значение}.
     */
    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            while (read.ready()) {
                String[] strings = read.readLine().replaceAll("#.+", "").split("=", 2);
                if (strings.length > 1 && !"".equals(strings[0].trim())) {
                    values.put(strings[0].trim(), strings[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращение значения конфигурации по ключу.
     *
     * @param key ключ
     * @return значение
     */
    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }
}