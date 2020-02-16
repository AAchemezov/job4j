package ru.job4j.find;

import ru.job4j.io.Search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Создать программу для поиска файла.
 * 1. Программа должна искать данные в заданном каталоге и подкаталогах.
 * 2. Имя файла может задаваться, целиком, по маске, по регулярному выражение(не обязательно).
 * 3. Программа должна собираться в jar и запускаться через java -jar find.jar -d c:/ -n *.txt -m -o log.txt
 * Ключи
 * -d - директория в которая начинать поиск.
 * -n - имя файл, маска, либо регулярное выражение.
 * -m - искать по макс, либо -f - полное совпадение имени. -r регулярное выражение.
 * -o - результат записать в файл.
 * 4. Программа должна записывать результат в файл.
 * 5. В программе должна быть валидация ключей и подсказка.
 */
public class Find {

    private final static String SELECT_MODE = "Нужно указать один из флагов: [-m, -f, -r]. Вы указали: %s.";
    private final static String INVALID_PARAM = "Параметры %s не корректны.";
    private final static String TRASH_PARAM = "Параметры %s не распознанны.";

    /**
     * Возвращает справочную информацию
     */
    private static String getHelp() {
        return "Программа для поиска файла. \n"
                + "Программа ищет данные в заданном каталоге и подкаталогах.\n"
                + "Имя файла может задаваться, целиком, по маске, по регулярному выражению.\n"
                + "Ключи: \n"
                + "-d - директория в которая начинать поиск.\n"
                + "-n - имя файл, маска, либо регулярное выражение.\n"
                + "-m - искать по маске\n"
                + "-f - полное совпадение имени\n"
                + "-r - регулярное выражение\n"
                + "-o - файл записи результатов поиска\n";
    }

    /**
     * Вывод в консоль
     */
    private static void outLog(String s) {
        System.out.println(s);
    }

    /**
     * Вывод в консоль форматируемой строки с параметром List
     */
    private static void outLog(String template, List s) {
        if (!s.isEmpty()) {
            outLog(String.format(template, s.toString()));
        }
    }

    /**
     * Сохранение результатов поиска в файл.
     *
     * @param output файл, куда сохранить пути найденных файлов
     * @param files  лист найденных файлов
     */
    private static void saveLog(File output, List<File> files) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(output))) {
            for (File line : files) {
                out.println(line.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Точка входа в приложение
     */
    public static void main(String[] args) {
        FindArgs findArgs = new FindArgs(args);
        if (findArgs.isAllArgumentsValid()) {
            List<File> files = new Search().files(findArgs.getDirectory(), Pattern.compile(findArgs.getANameValue()));
            files.forEach(f -> System.out.println(f.getAbsolutePath()));
            saveLog(findArgs.getOutput(), files);
        } else {
            System.out.println(getHelp());
            outLog(TRASH_PARAM, findArgs.getTrashData());
            outLog(INVALID_PARAM, findArgs.getInValidArguments());
            if (!findArgs.validateMode()) {
                outLog(String.format(SELECT_MODE, findArgs.getValidModes()));
            }
        }
    }
}
