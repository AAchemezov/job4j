package ru.job4j.io;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Программа 'Консольный чат'.
 * Пользователь вводит слово-фразу, программа берет случайную фразу из текстового файла и выводит в ответ.
 * Программа замолкает если пользователь вводит слово «стоп», при этом он может продолжать отправлять сообщения в чат.
 * Если пользователь вводит слово «продолжить», программа снова начинает отвечать.
 * При вводе слова «закончить» программа прекращает работу.
 * Запись диалога включая, слова-команды стоп/продолжить/закончить записать в текстовый лог.
 *
 * @author Andrey Chemezov
 * @since 15.02.2020
 */
public class ConsoleChat {
    //Расположение файла с ответами
    private static final String MESSAGES_PATH = "chapter_002/data/consoleChatMessages.txt";
    //Файл сохраняемого лога
    private static final String LOG_PATH = "chapter_002/data/chatMessages.log";
    //Имя пользователя
    private static final String DEFAULT_USER = "Незнакомец";
    //Имя бота
    private static final String BOT_USER = "Бендер";
    //Команды
    private static final String COMMAND_CONTINUE = "продолжить";
    private static final String COMMAND_STOP = "стоп";
    private static final String COMMAND_END = "закончить";

    private final String messFilePath;
    private final String logPath;
    private final String userName;
    private final String botName;
    private List<String> history;
    private List<String> massages;
    private Scanner in;
    private PrintStream out;

    /**
     * Конструктор без аргументов, используются параметры по умолчанию.
     */
    ConsoleChat() {
        this(DEFAULT_USER, BOT_USER);
    }

    /**
     * Конструктор с возможностью указать имя пользователя и имя бота.
     *
     * @param userName имя пользователя
     * @param botName  имя бота
     */
    ConsoleChat(String userName, String botName) {
        this(userName, botName, MESSAGES_PATH, LOG_PATH);
    }

    /**
     * Конструктор с возможностью указать файл с ответами бота, путь сохранения лога, имя пользователя, имя бота.
     *
     * @param userName    имя пользователя
     * @param botName     имя бота
     * @param massagePath путь к файлу с ответами бота
     * @param outLogPath  путь к файлу с логами
     */
    ConsoleChat(String userName, String botName, String massagePath, String outLogPath) {
        this.messFilePath = massagePath;
        this.logPath = outLogPath;
        this.userName = userName;
        this.botName = botName;
        start();
    }

    /**
     * Процедура инициализации чата.
     * Загрузка сообщений бота из файла.
     */
    private void start() {
        try (BufferedReader read = new BufferedReader(new FileReader(messFilePath))) {
            massages = read.lines().collect(Collectors.toList());
            history = new LinkedList<>();
            in = new Scanner(System.in);
            out = System.out;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Процесс чата.
     * Обработка сообщений/команд пользователя и формирование ответов бота.
     */
    void run() {
        Random r = new Random();
        String massage = "";
        boolean stop = false;
        boolean end = false;
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("[hh:mm:ss] ");
        do {
            out.print(userName + " : ");
            massage = in.nextLine();
            history.add(formatForDateNow.format(new Date()) + userName + " : " + massage);
            switch (massage) {
                case COMMAND_END:
                    end = true;
                    continue;
                case COMMAND_STOP:
                    if (!stop) {
                        stop = true;
                        continue;
                    }
                    break;
                case COMMAND_CONTINUE:
                    if (stop) {
                        stop = false;
                        continue;
                    }
                    break;
                case "":
                    continue;
                default:
            }
            if (!stop) {
                String response = massages.get(r.nextInt(massages.size()));
                history.add(formatForDateNow.format(new Date()) + botName + " : " + response);
                out.println(botName + " : " + response);
            }
        } while (!end);
        end();
    }

    /**
     * Процедура выхода из чата.
     * Сохранение лога.
     */
    private void end() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(logPath))) {
            for (String line : history) {
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ConsoleChat consoleChat = new ConsoleChat();
        consoleChat.run();
    }
}
