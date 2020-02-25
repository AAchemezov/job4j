package ru.job4j.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

import static ru.job4j.xml.ElementConfiguration.Entries;
import static ru.job4j.xml.ElementConfiguration.Entry;

/**
 * Конвертер данных из БД в XML
 */
public class StoreXML {
    private static final Logger LOG = LogManager.getLogger(StoreXML.class);

    /**
     * Файл, куда будет сохраняться данные.
     */
    private File target;

    public StoreXML(File target) {
        this.target = target;
    }

    /**
     * Метод сохраняет данные из list в файл target.
     */
    public void save(List<Entry> list) {
        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Entries(list), target);
        } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}