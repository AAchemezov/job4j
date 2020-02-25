package ru.job4j.xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Конвертер XML -> XSTL.
 */

class ConverterXML2XSQT {

    private ConverterXML2XSQT() {
    }

    /**
     * Конвертирование XML в XSTL
     *
     * @param source исходный файл .xml
     * @param target целевой файл .xstl
     * @param scheme XSTL схема приеобразования
     * @throws TransformerException ошибка конвертирования
     */
    static void convert(File source, File target, File scheme) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(scheme));
        transformer.transform(new StreamSource(source), new StreamResult(target));
    }
}