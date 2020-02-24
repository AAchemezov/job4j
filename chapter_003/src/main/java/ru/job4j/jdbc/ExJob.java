package ru.job4j.jdbc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExJob {

    private static final Logger LOG = LogManager.getLogger(ExJob.class.getName());

    public static void main(String[] args) {
        double version = 1.0034;
        LOG.trace("trace message {}", version);
        LOG.debug("trace message {}", version);
        LOG.info("trace message {}", version);
        LOG.warn("trace message {}", version);
        LOG.error("trace message {}", version);
    }
}
