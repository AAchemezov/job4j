package ru.job4j.xml;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static ru.job4j.xml.ElementConfiguration.Entry;

public class MainProgram {

    private static String pathSource = "chapter_003/src/main/resources/source.xml";
    private static String pathTarget = "chapter_003/src/main/resources/target.xml";
    private static String pathSchema = "chapter_003/src/main/resources/schema.xsl";
    private static final int SIZE = 1_000_000;

    public static void main(String[] args) {
        File source = new File(pathSource);
        File target = new File(pathTarget);
        File schema = new File(pathSchema);

        System.out.println("Элементов: " + SIZE);

        try (StoreSQL sql = new StoreSQL(new Config().init())) {
            sql.initDataBase();
            sql.generate(SIZE);
//            or
//           sql.generateWithBatch(SIZE);
            List<Entry> listEntry = sql.obtainListEntry();
            StoreXML storeXML = new StoreXML(source);
            storeXML.save(listEntry);
            ConverterXML2XSQT.convert(source, target, schema);
            List<Entry> list = ParserXML.parse(target.getPath());

            long sum = 0;
            for (Entry i : list) {
                sum += i.getValue();
            }

            System.out.println("Арифметическая сумма значений всех атрибутов field: " + sum);

        } catch (SQLException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
