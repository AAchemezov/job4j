package ru.job4j.socket;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerWiseOracleTest {
    private static String ln = System.getProperty("line.separator");
    private static String fileAnswers = System.getProperty("java.io.tmpdir") + "testOracle.txt";
    private static String[] answers = {
            "first quest",
            "first answer",
            "",
            "second quest",
            "second answer",
            "",
            "quest multiline",
            "first line",
            "second line",
            "...",
            ""
    };


    private void createAnswers() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileAnswers))) {
            for (String line : answers) {
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testServer(String input, String expect) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        createAnswers();
        ServerWiseOracle oracle = new ServerWiseOracle(socket, fileAnswers);
        oracle.runOracle();
        assertThat(out.toString(), is(expect));
    }

    @Test
    public void whenAskAnswerThenExit() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "exit",
                        ""
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        ln
                ));
    }

    @Test
    public void whenAskAnswerThenHello() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "hello",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        "",
                        "Hello, dear friend, I'm a Oracle.",
                        ln
                ));
    }

    @Test
    public void whenAskAnswerThenNotUnderstand() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "blah-blah",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        "",
                        "Dear friend, I don't understand you.",
                        ln
                ));
    }

    @Test
    public void whenAskFirstThenFirstAnswer() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "first quest",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        "",
                        "first answer",
                        ln
                ));
    }

    @Test
    public void whenAskFirstSecondThenFirstSecondAnswer() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "first quest",
                        "second quest",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        "",
                        "first answer",
                        "",
                        "second answer",
                        ln
                ));
    }

    @Test
    public void whenAskQuestThenMultilineAnswer() throws IOException {
        testServer(
                Joiner.on(ln).join(
                        "quest multiline",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "wait command ...",
                        "",
                        "first line",
                        "second line",
                        "...",
                        ln
                ));
    }
}
