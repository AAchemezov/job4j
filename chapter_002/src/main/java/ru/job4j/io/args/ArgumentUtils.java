package ru.job4j.io.args;

import java.io.File;

/**
 * Класс-хелпер для работы с {@link AbstractArgs.Argument}
 */
public class ArgumentUtils {

    private ArgumentUtils() {
    }

    /**
     * Проверяет, что переданная дирректория существует
     */
    public static boolean validateDirectory(String s) {
        return new File(s).isDirectory();
    }

    /**
     * Проверяет имя файла на запрещённые символы, проверяет, что существует путь, если переданная строка содержит путь.
     */
    public static boolean validateNewFileName(String s) {
        int i = Math.max(
                s.lastIndexOf("/"),
                s.lastIndexOf("\\")
        );
        if (++i > 0) {
            if (!validateDirectory(s.substring(0, i))) {
                return false;
            }
            s = s.substring(i, s.length());
        }
        return s.matches("^[^*&%?]+$");
    }

    /**
     * удаляет из строки все символы {*}
     */
    public static String removeAllStars(String s) {
        return s.replaceAll("[*]", "");
    }

    /**
     * Результат разделения переданной строки на подстроки, разделённые символом {,}
     */
    public static String[] mappingStringToArrayStrings(String s) {
        return s.split(",");
    }

    /**
     * Возвращает простой строковый аргумент
     */
    public static AbstractArgs.Argument<String> getEmptyStringArgument(String s) {
        return new AbstractArgs.Argument<String>(s).setMapping(s1 -> s1);
    }

    /**
     * Возвращает простой аргумент - флаг
     */
    public static AbstractArgs.Argument<String> getWithoutArgument(String s) {
        return new AbstractArgs.Argument<String>(s).setValidating(s1 -> "".equals(s1));
    }


}
