package ru.job4j.xml;

import java.io.InputStream;
import java.util.Properties;

/**
 * Настройки подключения к БД
 */
public final class Config {
    private static final String DEFAULT_PATH = "SQLLite.properties";
    private final Properties values = new Properties();

    /**
     * Инициализация ностроек.
     */
    Config init() {
        return init(DEFAULT_PATH);
    }

    /**
     * Инициализация ностроек из файла.
     */
    private Config init(String resourcePath) {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(resourcePath)) {
            values.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    /**
     * Получение значения настройки по ключу.
     */
    String get(String key) {
        return this.values.getProperty(key);
    }
}