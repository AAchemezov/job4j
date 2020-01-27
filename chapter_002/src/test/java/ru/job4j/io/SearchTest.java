package ru.job4j.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование {@link Search} класса поиска файлов по листу расширений.
 */
public class SearchTest {
    private static Search search = new Search();
    private static String rootDir = System.getProperty("java.io.tmpdir") + "testFileDir" + new Date().getTime();

    private static String getNamesFileList(List<File> files) {
        return files.stream().map(File::getName).sorted().collect(Collectors.toList()).toString();
    }

    @Before
    public void before() {
        new ArrayList<File>() {
            {
                add(new File(rootDir));
                add(new File(rootDir + "/folder1/folder1/"));
                add(new File(rootDir + "/folder1/folder2/"));
                add(new File(rootDir + "/folder2/folder3/folder4"));
                add(new File(rootDir + "/folder3/"));
            }
        }.forEach(File::mkdirs);

        new ArrayList<File>() {
            {
                add(new File(rootDir + "/1.txt"));
                add(new File(rootDir + "/folder1/sos.abs"));
                add(new File(rootDir + "/folder1/.sos.abs.1"));
                add(new File(rootDir + "/folder1/ txt.abs"));
                add(new File(rootDir + "/folder1/1   .1.abs"));
                add(new File(rootDir + "/folder1/folder1/text.txt"));
                add(new File(rootDir + "/folder1/folder1/abs"));
                add(new File(rootDir + "/folder1/folder1/sos"));
                add(new File(rootDir + "/folder1/folder1/txt"));
                add(new File(rootDir + "/folder1/folder2/1"));
                add(new File(rootDir + "/folder2/.....txt"));
                add(new File(rootDir + "/folder2/folder.lnk"));
                add(new File(rootDir + "/folder2/folder3/_@#$@%%$^$&%^%^&.sos"));
                add(new File(rootDir + "/folder2/folder3/1.sos"));
                add(new File(rootDir + "/folder2/folder3/folder4/fold.lnk"));

            }
        }.forEach(f -> {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println(f.getAbsolutePath());
                e.printStackTrace();
            }
        });
    }


    @Test
    public void whenSearchTxt() {
        List<String> fileExtensions = List.of(".txt");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[.....txt, 1.txt, text.txt]"));
    }

    @Test
    public void whenSearchSos() {
        List<String> fileExtensions = List.of(".sos");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[1.sos, _@#$@%%$^$&%^%^&.sos]"));
    }

    @Test
    public void whenSearchEmpty() {
        List<String> fileExtensions = List.of("");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[1, abs, sos, txt]"));
    }

    @Test
    public void whenSearchLnk() {
        List<String> fileExtensions = List.of(".lnk");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[fold.lnk, folder.lnk]"));
    }

    @Test
    public void whenSearchOne() {
        List<String> fileExtensions = List.of(".1");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[.sos.abs.1]"));
    }

    @Test
    public void whenSearchOneAndEmpty() {
        List<String> fileExtensions = List.of(".1", "");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[.sos.abs.1, 1, abs, sos, txt]"));
    }

    @Test
    public void whenSearchAbs() {
        List<String> fileExtensions = List.of(".abs");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[ txt.abs, 1   .1.abs, sos.abs]"));
    }

    @Test
    public void whenSearchNotValid() {
        List<String> fileExtensions = List.of("abs", "tx", ".txt ", "1.", ".", "testFileDir", "folder1", " ");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[]"));
    }

    @Test
    public void whenSearchNotValidAndOneAndEmpty() {
        List<String> fileExtensions = List.of(".1", "abs", "tx", ".txt ", "1.", ".", "testFileDir", "folder1", " ", "");
        String files = getNamesFileList(search.files(rootDir, fileExtensions));
        assertThat(files, is("[.sos.abs.1, 1, abs, sos, txt]"));
    }

    @Test
    public void whenSearchNotValidDir() {
        List<String> fileExtensions = List.of(".txt");
        String files = getNamesFileList(search.files("text.txt", fileExtensions));
        assertThat(files, is("[]"));
    }

    @Test
    public void whenSearchNullDir() {
        List<String> fileExtensions = List.of(".txt");
        String files = getNamesFileList(search.files(null, fileExtensions));
        assertThat(files, is("[]"));
    }

    @Test
    public void whenSearchNullFileExt() {
        String files = getNamesFileList(search.files(rootDir, null));
        assertThat(files, is("[]"));
    }
}
