package ru.job4j.io;

import ru.job4j.io.args.Args;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private final static String START_ZIP = "Начинаю архивирование ...";
    private final static String END_ZIP = "Архивирование закончено.";
    private final static String NOT_INIT_PARAM = "Параметры %s не инициализированы.";
    private final static String INVALID_PARAM = "Параметры %s не корректны.";
    private final static String TRASH_PARAM = "Параметры %s не распознанны.";

    /**
     * Упаковывает все файлы из дирректории source в файл target, исключая файлы с расширениями ext.
     * Файлы сохраняются по относительному пути.
     *
     * @param source архивируемая директория
     * @param ext    исключаемые расширения файлов
     * @param target файл, куда сохраняется архив
     */
    public void pack(File source, File target, String[] ext) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            List<File> files = new Search().files(source.getPath(), List.of(ext), true);
            for (File file : files) {
                zip.putNextEntry(
                        new ZipEntry(
                                file.getCanonicalPath()
                                        .replace(source.getCanonicalPath() + "\\", "")));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Упаковывает все файлы sources в файл target.
     * Иерархия файлов сохраняется по абсолютному пути.
     *
     * @param target  файл, куда сохраняется архив
     * @param sources архивируемые файлы
     */
    public void pack(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File file : sources) {
                zip.putNextEntry(new ZipEntry(file.getAbsolutePath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ищет файлы в директории root исключая файлы с расширениями ext.
     *
     * @param root директория поиска файлов
     * @param ext  исключаемые расширения файлов
     */
    public List<File> seekBy(String root, String[] ext) {
        return new Search().files(root, List.of(ext), true);
    }


    private static void outLog(String s) {
        System.out.println(s);
    }

    private static void outLog(String template, List s) {
        if (!s.isEmpty()) {
            outLog(String.format(template, s.toString()));
        }
    }

    public static void main(String[] args) {
        Args zipArgs = new Args(args);

        if (zipArgs.isAllArgumentsValid() && zipArgs.getTrashData().isEmpty()) {
            outLog(START_ZIP);
            Zip zip = new Zip();
            zip.pack(
                    zip.seekBy(zipArgs.directory(), zipArgs.exclude()),
                    zipArgs.output()
            );
            outLog(END_ZIP);
        } else {
            outLog(TRASH_PARAM, zipArgs.getTrashData());
            outLog(INVALID_PARAM, zipArgs.getInValidArguments());
            outLog(NOT_INIT_PARAM, zipArgs.getNotInitArguments());
        }
    }

}
