package ru.job4j.io;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Класс поиска файлов.
 */
public class Search {

    /**
     * Поиск файлов по списку расширений файлов.
     *
     * @param parent     путь до каталога, с которого нужно осуществлять поиск
     * @param extensions расширения файлов в виде ".extension", Поиск файлов без расширения - "."
     * @return найденные файлы
     */
    List<File> files(String parent, List<String> extensions) {
        return files(parent, extensions, false);
    }

    /**
     * Поиск файлов по списку расширений файлов.
     *
     * @param parent               путь до каталога, с которого нужно осуществлять поиск
     * @param extensions           расширения файлов в виде ".расширение", Поиск файлов без расширения - "."
     * @param isExcludedExtensions если <code>true</code> файлы с расширениями {@param extensions}
     *                             исключаются из результатов поиска,
     *                             иначе если <code>false</code> ищутся файлы с расширениями {@param extensions}
     * @return найденные файлы
     */
    List<File> files(String parent, List<String> extensions, boolean isExcludedExtensions) {
        final List<File> results = new ArrayList<>();
        if (parent == null || extensions == null) {
            return results;
        }
        getTemplate(extensions).ifPresent(
                pattern -> {
                    breadthFirstSearch(new File(parent),
                            file -> {
                                if (pattern.matcher(file.getName()).find() ^ isExcludedExtensions) {
                                    results.add(file);
                                }
                            });
                });
        return results;
    }

    /**
     * Формирует шаблон поиска файлов по листу расширений файлов.
     *
     * @param extensions лист с расширениями файлов в формате ".extension", для файлов без расширения - "."
     * @return Опционал с шаблоном
     */
    private Optional<Pattern> getTemplate(List<String> extensions) {
        return extensions
                .stream()
                .filter(s -> s.startsWith("."))
                .map(s -> s.equals(".") ? "^[^\\.]+$" : ".+" + s + "$")
                .reduce((s, s2) -> s + "|" + s2)
                .map(s -> Pattern.compile("(" + s + ")"));
    }

    /**
     * Выполняет обход дерева в ширину и для каждого файла выполняет действие.
     *
     * @param root         корневой каталог
     * @param fileConsumer действие, выполняемое для каждого файла
     */
    private void breadthFirstSearch(File root, Consumer<File> fileConsumer) {
        if (!root.isDirectory()) {
            return;
        }
        final Queue<File> queueFiles = new LinkedList<>();
        queueFiles.offer(root);
        while (!queueFiles.isEmpty()) {
            for (File file : Objects.requireNonNull(queueFiles.poll().listFiles())) {
                if (file.isDirectory()) {
                    queueFiles.offer(file);
                } else {
                    fileConsumer.accept(file);
                }
            }
        }
    }

    /**
     * Поиск файлов по соответствию имени файла паттерну.
     *
     * @param parent  путь до каталога, с которого нужно осуществлять поиск
     * @param pattern паттерн поиска
     * @return найденные файлы
     */
    public List<File> files(File parent, Pattern pattern) {
        final List<File> results = new ArrayList<>();
        if (parent == null) {
            return results;
        }
        breadthFirstSearch(parent,
                file -> {
                    if (pattern.matcher(file.getName()).find()) {
                        results.add(file);
                    }
                });
        return results;
    }

}
