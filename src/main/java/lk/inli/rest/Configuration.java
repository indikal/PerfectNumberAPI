package lk.inli.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public enum Configuration {
    INSTANCE;

    private static final Logger log = LogManager.getLogger(Configuration.class);
    private static Properties properties;

    public void initialize() {
        log.info("Initializing configuration from config.properties file ...");
        properties = new Properties();

        try {
            properties.load(Configuration.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.fatal("Initializing configuration properties failed.", e);
        }

        log.info("Finished initializing configuration from config.properties file");
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
