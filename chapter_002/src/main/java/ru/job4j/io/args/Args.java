package ru.job4j.io.args;

import java.io.File;
import java.util.Set;

/**
 * Реализация {@link AbstractArgs} для параметров архивации.
 * -d - directory - которую мы хотим архивировать
 * -e - exclude - исключить файлы *.xml
 * -o - output - во что мы архивируем
 */
public class Args extends AbstractArgs {
    private Argument<String> aDirectory;
    private Argument<File> aOutput;
    private Argument<String[]> aExclude;

    /**
     * Конструктор.
     * Вызывается метод инициализации аргументов
     *
     * @param strings массив аргументов в виде {"-d", "directory", "-e","exclude" ,"-o", "output"}.
     */
    public Args(String[] strings) {
        super(strings);
        initArguments();
    }

    @Override
    protected Set<Argument> getArguments() {
        aDirectory = new Argument<String>("-d")
                .setValidating(ArgumentUtils::validateDirectory)
                .setMapping(s -> s);

        aOutput = new Argument<File>("-o")
                .setValidating(ArgumentUtils::validateNewFileName)
                .setMapping(File::new);

        aExclude = new Argument<String[]>("-e")
                .setPreparing(ArgumentUtils::removeAllStars)
                .setValidating(s -> s.startsWith("."))
                .setMapping(ArgumentUtils::mappingStringToArrayStrings);

        return Set.of(aDirectory, aExclude, aOutput);
    }

    /**
     * Возвращает {@link File} - архивируемая дирректория
     */
    public String directory() {
        return getValueArgument(aDirectory);
    }
    /**
     * Массив строк расширений, которые нужно исключить из архивации
     */
    public String[] exclude() {
        return getValueArgument(aExclude);
    }

    /**
     * Путь и/или имя файла создаваемого архива
     */
    public File output() {
        return getValueArgument(aOutput);
    }
}
