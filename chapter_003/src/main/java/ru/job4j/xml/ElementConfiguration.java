package ru.job4j.xml;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

public class ElementConfiguration {

    @XmlRootElement
    public static class Entries {
        private List<Entry> entry;

        public Entries() {
        }

        public Entries(List<Entry> entry) {
            this.entry = entry;
        }

        public List<Entry> getEntry() {
            return entry;
        }

        public void setEntry(List<Entry> entry) {
            this.entry = entry;
        }
    }

    @XmlRootElement
    public static class Entry {
        private int value;

        public Entry() {
        }

        public Entry(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return value == entry.value;
        }

        @Override
        public int hashCode() {
            return value;
        }
    }

    public static void main(String[] args) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(
                new Entries(Arrays.asList(new Entry(1), new Entry(2))),
                System.out
        );
    }
}