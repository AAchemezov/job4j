package ru.job4j.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Бот - мудрый Оракл.
 * Серверная сторона.
 */
public class ServerWiseOracle {
    //Расположение файла с ответами
    private static final String MESSAGES_PATH = "chapter_002/data/Oracle.txt";
    private final Socket socket;
    private Map<String, List<String>> answers = new HashMap<>();

    ServerWiseOracle(Socket socket) {
        this.socket = socket;
    }

    /**
     * Конструктор.
     *
     * @param socket      сокет
     * @param pathAnswers путь к файлу с вопросами
     */
    ServerWiseOracle(Socket socket, String pathAnswers) {
        this(socket);
        loadAnswers(pathAnswers);
    }

    /**
     * Загрузка вопросов и ответов
     *
     * @param pathAnswers путь к файлу с вопросами и ответами в формате:
     *                    текст вопроса/n
     *                    ответ/n
     *                    /n
     *                    текст вопроса/n
     *                    ответ/n
     *                    продолжение ответа/n
     *                    /n
     *                    /n
     */
    private void loadAnswers(String pathAnswers) {
        try (BufferedReader read = new BufferedReader(new FileReader(pathAnswers))) {
            List<String> massages = read.lines().collect(Collectors.toList());
            boolean collect = false;
            String key = null;
            List<String> list = null;
            for (String s : massages) {
                if (collect) {
                    list.add(s);
                    if ("".equals(s)) {
                        answers.put(key, list);
                        collect = false;
                    }
                } else {
                    key = s;
                    list = new LinkedList<>();
                    collect = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запуск чата.
     */
    void runOracle() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String ask = "";
                System.out.println("wait command ...");
                out.println("wait command ...");
                out.println();
                boolean exit = false;
                do {
                    ask = in.readLine();
                    System.out.println(ask);
                    if (OracleUtils.isExitCommand(ask)) {
                        exit = true;
                        continue;
                    }
                    if (OracleUtils.isHelloCommand(ask)) {
                        out.println("Hello, dear friend, I'm a Oracle.");
                        out.println();
                        continue;
                    }
                    if (answers.containsKey(ask)) {
                        for (String s : answers.get(ask)) {
                            out.println(s);
                        }
                        continue;
                    }
                    out.println("Dear friend, I don't understand you.");
                    out.println();
                } while (!exit);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerWiseOracle(
                new ServerSocket(OracleUtils.getPort()).accept(),
                MESSAGES_PATH
        ).runOracle();
    }
}
