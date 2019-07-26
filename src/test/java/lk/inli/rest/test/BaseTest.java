package lk.inli.rest.test;

import lk.inli.rest.Configuration;
import lk.inli.rest.server.RestfullServer;
import org.apache.cxf.endpoint.Server;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected static String BASE_URL = "http://localhost:8080/perfectnumber";
    protected static final String USERNAME = "REST_USER";
    protected static final String PASSWORD = "REST_PASSWORD";
    protected static CloseableHttpClient client;
    protected static Server server;

    @BeforeClass
    public static void createClient() {
        Configuration.INSTANCE.initialize();
        log.info("server url {}", Configuration.INSTANCE.getProperty("application.server.address"));

        server = new RestfullServer().initialize().run();
        client = HttpClients.createDefault();
    }

    @AfterClass
    public static void closeClient() throws IOException {
        //Close the HTTP client
        client.close();

        //If the server is running stop it
        if (server.isStarted()) {
            server.stop();
        }
    }
}
