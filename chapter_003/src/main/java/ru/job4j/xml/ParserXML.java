package ru.job4j.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static ru.job4j.xml.ElementConfiguration.Entry;

/**
 * Парсер xml.
 */
public class ParserXML {

    private static final Logger LOG = LogManager.getLogger(ParserXML.class);

    private static final String TAG_ENTRY = "entry";
    private static final String VALUE_NAME = "href";

    private ParserXML() {
    }

    /**
     * Парссинг xml.
     *
     * @return лист распарсенных объектов Entry's
     */
    static List<Entry> parse(String path) {
        List<Entry> list = new LinkedList<>();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(

                    new DefaultHandler() {
                        @Override
                        public void startElement(String url, String localName, String qName, Attributes attributes) {
                            if (qName.equals(TAG_ENTRY)) {
                                int integer = Integer.parseInt(attributes.getValue(VALUE_NAME));
                                Entry entry = new Entry(integer);
                                list.add(entry);
                            }
                        }

                    });
            xmlReader.parse(path);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }
}