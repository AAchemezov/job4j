package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;

public class Analyze {
    private final static List<String> SERVER_LOST = List.of("400", "500");
    private final static List<String> SERVER_FOUND = List.of("200", "300");

    /**
     * Метод должен находить диапазоны, когда сервер не работал.
     * Начальное время - это время когда статус 400 или 500.
     * Конечное время это когда статус меняется с 400 или 500 на 200 или 300.
     * Формат файла:начала периода;конец периода;
     *
     * @param source путь к файлу-источнику
     *               Он имеет следующий формат
     *               TYPE date
     *               Type - может иметь 4 значения 200, 300, 400, 500
     *               Date - это время проверки 10:56:01 (формат часы:минуты:секунды)
     *               Например server.log
     *               200 10:56:01
     *               200 10:57:01
     *               400 10:58:01
     *               200 10:59:01
     * @param target путь к файлу записи результата
     *               Формат записи:
     *               [Начальное время]:[Конечное время]
     *               Пример:
     *               15:01:30;15:02:32
     *               15:10:30;23:12:32
     */
    public void unavailable(String source, String target) {

        try (BufferedReader read = new BufferedReader(new FileReader(source))) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
                String start = null;
                while (read.ready()) {
                    String[] typeDate = read.readLine().split(" ");
                    if (start == null) {
                        if (SERVER_LOST.contains(typeDate[0])) {
                            start = typeDate[1];
                        }
                    } else {
                        if (SERVER_FOUND.contains(typeDate[0])) {
                            out.println(start + ";" + typeDate[1]);
                            start = null;
                        }
                    }
                }
                if (start != null) {
                    out.println(start + ";");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}