package ru.job4j.find;

import ru.job4j.io.args.AbstractArgs;
import ru.job4j.io.args.ArgumentUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Реализация {@link AbstractArgs} для параметров архивации.
 * Ключи:
 * -d - директория в которая начинать поиск
 * -n - имя файл, маска, либо регулярное выражение
 * -m - искать по маске
 * -f - полное совпадение имени
 * -r - регулярное выражение
 * -o - файл записи результатов поиска
 */
public class FindArgs extends AbstractArgs {

    private Argument<File> aDirectory;
    private Argument<File> aOutput;
    private Argument<String> aName;
    private Argument aMask;
    private Argument aFull;
    private Argument aRegex;

    FindArgs(String[] strings) {
        super(strings);
        initArguments();
    }

    @Override
    protected Set<Argument> getArguments() {
        aDirectory = new Argument<File>("-d")
                .setValidating(ArgumentUtils::validateDirectory)
                .setMapping(File::new);
        aOutput = new Argument<File>("-o")
                .setValidating(ArgumentUtils::validateNewFileName)
                .setMapping(File::new);
        aName = ArgumentUtils.getEmptyStringArgument("-n");
        aMask = ArgumentUtils.getWithoutArgument("-m");
        aFull = ArgumentUtils.getWithoutArgument("-f");
        aRegex = ArgumentUtils.getWithoutArgument("-r");
        return Set.of(aDirectory, aOutput, aName, aMask, aFull, aRegex);
    }

    @Override
    public boolean isAllArgumentsValid() {
        return isValidArgument(aDirectory) && isValidArgument(aOutput) && validateMode() && validateAName();
    }

    /**
     * Проверяет валидность параметров [-f, -r, -m]
     */
    boolean validateMode() {
        return getValidModes().size() == 1;
    }

    /**
     * Возвращает список валидных параметров из [-f, -r, -m]
     */
    List<String> getValidModes() {
        List<String> list = new LinkedList<>();

        if (isValidArgument(aMask)) {
            list.add(aMask.getName());
        }
        if (isValidArgument(aFull)) {
            list.add(aFull.getName());
        }
        if (isValidArgument(aRegex)) {
            list.add(aRegex.getName());
        }
        return list;
    }


    @Override
    public ArrayList<String> getInValidArguments() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (!isValidArgument(aDirectory)) {
            arrayList.add("-d");
        }
        if (!isValidArgument(aOutput)) {
            arrayList.add("-o");
        }
        if (!validateAName()) {
            arrayList.add("-n");
        }
        return arrayList;
    }

    /**
     * Проверяет валидность параметра -n в зависимости от режима поиска
     */
    private boolean validateAName() {
        if (!isValidArgument(aName)) {
            return false;
        }
        switch (getMode()) {
            case REGEX:
                try {
                    Pattern.compile(getValueArgument(aName));
                    return true;
                } catch (PatternSyntaxException e) {
                    return false;
                }
            case MASK:
            case FULL_NAME:
                return true;
            default:
                return false;
        }
    }

    /**
     * Возвращает режим поиска.
     * Если заданно больше 1, то возвражщает первый в порядке: маска, полное имя, регулярное выражение.
     *
     * @return режим поиска
     */
    private Mode getMode() {
        if (isValidArgument(aMask)) {
            return Mode.MASK;
        }
        if (isValidArgument(aFull)) {
            return Mode.FULL_NAME;
        }
        if (isValidArgument(aRegex)) {
            return Mode.REGEX;
        }
        return Mode.ERROR;
    }

    /**
     * Перечисление режимов поиска
     */
    private enum Mode {
        //искать по маске
        MASK,
        //полное совпадение имени
        FULL_NAME,
        //регулярное выражение
        REGEX,
        //неверно задан режим поиска
        ERROR
    }

    /**
     * Возвращает - имя файла, маску или регулярное выражение в зависимости от режима поиска.
     */
    String getANameValue() {
        switch (getMode()) {
            case MASK:
                return ArgumentUtils.getMaskTemplate(getValueArgument(aName));
            case FULL_NAME:
                return ArgumentUtils.getFullNameTemplate(getValueArgument(aName));
            case REGEX:
                return getValueArgument(aName);
            default:
                return null;
        }
    }

    /**
     * Возвращает {@link File} - дирректория поиска
     */
    File getDirectory() {
        return getValueArgument(aDirectory);
    }

    /**
     * Возвращает {@link File} - файл записи результатов поиска
     */
    File getOutput() {
        return getValueArgument(aOutput);
    }
}
