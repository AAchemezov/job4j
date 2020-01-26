package ru.job4j.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AnalyzeTest {
    private static String target = "./data/unavailable.csv";

    @Test
    public void whenTwoCircle() {
        try {
            String source = "./data/server.logs";
            new Analyze().unavailable(source, target);
            BufferedReader read = new BufferedReader(new FileReader(target));
            assertThat(read.readLine(), is("10:57:01;10:59:01"));
            assertThat(read.readLine(), is("11:01:02;11:02:02"));
        } catch (Exception e) {
            fail("Проблемы с чтением файла:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void whenStartLostAndNextConnectAndEndLog() {
        try {
            String source = "./data/server1.logs";
            new Analyze().unavailable(source, target);
            BufferedReader read = new BufferedReader(new FileReader(target));
            assertThat(read.readLine(), is("10:57:01;11:02:02"));
        } catch (Exception e) {
            fail("Проблемы с чтением файла:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void whenStartLostAndEndLog() {
        try {
            String source = "./data/server2.logs";
            new Analyze().unavailable(source, target);
            BufferedReader read = new BufferedReader(new FileReader(target));
            assertThat(read.readLine(), is("10:57:01;"));
        } catch (Exception e) {
            fail("Проблемы с чтением файла:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void whenNotLostConnect() {
        try {
            String source = "./data/server3.logs";
            new Analyze().unavailable(source, target);
            BufferedReader read = new BufferedReader(new FileReader(target));
            assertThat(read.ready(), is(false));
        } catch (Exception e) {
            fail("Проблемы с чтением файла:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void whenEmptyLog() {
        try {
            String source = "./data/server3.logs";
            new Analyze().unavailable(source, target);
            BufferedReader read = new BufferedReader(new FileReader(target));
            assertThat(read.ready(), is(false));
        } catch (Exception e) {
            fail("Проблемы с чтением файла:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
