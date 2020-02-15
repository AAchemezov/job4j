package ru.job4j.socket;

import java.util.List;

/**
 * Бот - мудрый Оракл.
 * Утилитный класс.
 */
public class OracleUtils {

    private static int port = 11111;
    private static String ip = "127.0.0.1";
    private static List<String> exitCommands = List.of("exit", "пока", "прощай", "всего хорошего");
    private static List<String> helloCommands = List.of("hello", "привет", "hi", "здравствуй");

    /**
     * Возвращает порт подключения
     */
    public static int getPort() {
        return port;
    }

    /**
     * Возвращает ip подключения
     */
    public static String getIp() {
        return ip;
    }

    /**
     * Проверяем что сообщение является командой заверщения чата
     *
     * @param ask сообщение
     */
    public static boolean isExitCommand(String ask) {
        return exitCommands.contains(ask.toLowerCase());
    }

    /**
     * Проверяем что сообщение является приветствием
     *
     * @param ask сообщение
     */
    public static boolean isHelloCommand(String ask) {
        return helloCommands.contains(ask.toLowerCase());
    }
}
