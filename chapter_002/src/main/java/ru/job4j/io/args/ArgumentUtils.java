package ru.job4j.io.args;

import java.io.File;
import java.util.regex.Pattern;

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
            s = s.substring(i);
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
        return new AbstractArgs.Argument<String>(s).setValidating(s1 -> !s.isEmpty()).setMapping(s1 -> s1);
    }

    /**
     * Возвращает простой аргумент - флаг
     */
    public static AbstractArgs.Argument<String> getWithoutArgument(String s) {
        return new AbstractArgs.Argument<String>(s).setValidating(""::equals).setWithoutStartingValue(true);
    }

    /**
     * Возвращает строку регулярного выражения по маске
     *
     * @param mask маска имени файла, [*] заменяет один или более символов,
     *             например: Имя*файла, *фай*, Им**файла
     */
    public static String getMaskTemplate(String mask) {
        return "^" + Pattern.quote(mask).replace("*", "\\E.+\\Q") + "$";
    }

    /**
     * Возвращает строку регулярного выражения полного совпадения имени.
     *
     * @param name имя файла с расширением, например: class.java, .properties
     */
    public static String getFullNameTemplate(String name) {
        return "^" + Pattern.quote(name) + "$";
    }
}
