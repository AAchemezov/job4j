package ru.job4j.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Бот - мудрый Оракл.
 * Клиент.
 */
public class ClientWiseOracle {
    private final Socket socket;
    private final InputStream in;
    private final PrintStream out;

    ClientWiseOracle(Socket socket) {
        this(socket, System.in, System.out);
    }

    /**
     * Конструктор.
     *
     * @param socket сокет
     * @param in     InputStream ввода пользователя
     * @param out    PrintStream вывод пользователя
     */
    ClientWiseOracle(Socket socket, InputStream in, PrintStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    /**
     * Запуск чата.
     */
    void runOracleClient() {
        try (PrintWriter outSocket = new PrintWriter(socket.getOutputStream(), true)) {
            try (BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                Scanner console = new Scanner(in);
                String str = "";
                do {
                    str = inSocket.readLine();
                    while (!str.isEmpty()) {
                        out.println(str);
                        str = inSocket.readLine();
                    }
                    str = (console.nextLine());
                    outSocket.println(str);
                } while (!OracleUtils.isExitCommand(str));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new ClientWiseOracle(
                new Socket(InetAddress.getByName(OracleUtils.getIp()), OracleUtils.getPort())
        ).runOracleClient();
    }
}
