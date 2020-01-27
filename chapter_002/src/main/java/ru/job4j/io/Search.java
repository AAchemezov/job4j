package ru.job4j.io;

import java.io.File;
import java.util.*;

/**
 * Класс поиска файлов.
 */
public class Search {

    /**
     * Поиск файлов по списку расширений файлов.
     *
     * @param parent путь до каталога, с которого нужно осуществлять поиск
     * @param exts   расширения файлов в виде ".расширение", Поиск файлов без расширения - "".
     * @return найденные файлы
     */
    List<File> files(String parent, List<String> exts) {
        final List<File> files = new ArrayList<>();
        if (parent != null && exts != null) {
            File root = new File(parent);
            if (root.isDirectory()) {
                final Queue<File> queueFiles = new LinkedList<>();
                queueFiles.offer(root);
                while (!queueFiles.isEmpty()) {
                    Arrays.stream(Objects.requireNonNull(queueFiles.poll().listFiles())).forEach(file -> {
                        if (file.isDirectory()) {
                            queueFiles.offer(file);
                        } else {
                            int i = file.getName().lastIndexOf('.');
                            if (exts.contains(i < 0 ? "" : file.getName().substring(i))) {
                                files.add(file);
                            }
                        }
                    });
                }
            }
        }
        return files;
    }
}
