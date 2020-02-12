package ru.job4j.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnalyzeTest {
    private Analyze analyze = new Analyze();

    private List<String> getStringsAnalyze(String source) {
        String target = "./data/unavailable.csv";
        analyze.unavailable(source, target);
        try (BufferedReader read = new BufferedReader(new FileReader(target))) {
            return read.lines().collect(Collectors.toList());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void whenTwoCircle() {
        String source = "./data/server.logs";
        List<String> lines = getStringsAnalyze(source);
        assertThat(lines != null && lines.size() == 2, is(true));
        assertThat(lines.get(0), is("10:57:01;10:59:01"));
        assertThat(lines.get(1), is("11:01:02;11:02:02"));
    }

    @Test
    public void whenStartLostAndNextConnectAndEndLog() {
        String source = "./data/server1.logs";
        List<String> lines = getStringsAnalyze(source);
        assertThat(lines != null && lines.size() == 1, is(true));
        assertThat(lines.get(0), is("10:57:01;11:02:02"));

    }

    @Test
    public void whenStartLostAndEndLog() {
        String source = "./data/server2.logs";
        List<String> lines = getStringsAnalyze(source);
        assertThat(lines != null && lines.size() == 1, is(true));
        assertThat(lines.get(0), is("10:57:01;"));
    }

    @Test
    public void whenNotLostConnect() {
        String source = "./data/server3.logs";
        List<String> lines = getStringsAnalyze(source);
        assertThat(lines != null && lines.size() == 0, is(true));
    }

    @Test
    public void whenEmptyLog() {
        String source = "./data/server3.logs";
        List<String> lines = getStringsAnalyze(source);
        assertThat(lines != null && lines.size() == 0, is(true));
    }
}
