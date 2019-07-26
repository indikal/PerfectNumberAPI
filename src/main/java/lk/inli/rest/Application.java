package lk.inli.rest;

import lk.inli.rest.server.RestfullServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(Application.class);
    }

    public static void main(String[] args) {
        RestfullServer server = new RestfullServer();
        server.initialize().run();

        logger.info("Server running on {}", Configuration.INSTANCE.getProperty("application.server.address"));
    }
}
