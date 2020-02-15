package ru.job4j.socket;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientWiseOracleTest {
    private static String ln = System.getProperty("line.separator");

    private void testClient(String inServerMessages, String inClientMessages, String expectServer, String expectClient) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream inServer = new ByteArrayInputStream(inServerMessages.getBytes());
        ByteArrayOutputStream outServer = new ByteArrayOutputStream();

        ByteArrayInputStream inClient = new ByteArrayInputStream(inClientMessages.getBytes());
        ByteArrayOutputStream outClient = new ByteArrayOutputStream();

        when(socket.getInputStream()).thenReturn(inServer);
        when(socket.getOutputStream()).thenReturn(outServer);

        ClientWiseOracle oracle = new ClientWiseOracle(socket, inClient, new PrintStream(outClient));
        oracle.runOracleClient();

        assertThat(outServer.toString(), is(expectServer));
        assertThat(outClient.toString(), is(expectClient));
    }

    @Test
    public void whenAskAnswerThenExit() throws IOException {
        testClient(
                Joiner.on(ln).join(
                        "Hello, I am Oracle!",
                        "" + ln
                ),
                "exit",
                "exit" + ln,
                "Hello, I am Oracle!" + ln
        );
    }

    @Test
    public void whenAskAnswerThenHelloExit() throws IOException {
        testClient(
                Joiner.on(ln).join(
                        "Wait you...",
                        "",
                        "Hello, I am Oracle!",
                        "I know a lot",
                        "" + ln
                ),
                Joiner.on(ln).join(
                        "hello",
                        "exit"
                ),
                Joiner.on(ln).join(
                        "hello",
                        "exit" + ln
                ),
                Joiner.on(ln).join(
                        "Wait you...",
                        "Hello, I am Oracle!",
                        "I know a lot" + ln
                ));
    }
}
