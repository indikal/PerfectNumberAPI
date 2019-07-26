package lk.inli.rest.server;

import lk.inli.rest.Configuration;
import lk.inli.rest.interceptor.AuthenticationHandler;
import lk.inli.rest.interceptor.InputValidator;
import lk.inli.rest.service.PerfectNumberServiceImpl;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import java.util.*;

public class RestfullServer {

    private static final Logger log = LogManager.getLogger(RestfullServer.class);

    private JAXRSServerFactoryBean factoryBean;

    public RestfullServer initialize() {
        //Initialize configurations from config.properties
        Configuration.INSTANCE.initialize();

        this.factoryBean = new JAXRSServerFactoryBean();
        this.factoryBean.setResourceClasses(PerfectNumberServiceImpl.class);
        this.factoryBean.setResourceProvider(new SingletonResourceProvider(new PerfectNumberServiceImpl()));
        log.debug("Resource class and providers set");

        Map<Object, Object> extensionMappings = new HashMap<Object, Object>();
        extensionMappings.put("json", MediaType.APPLICATION_JSON);
        this.factoryBean.setExtensionMappings(extensionMappings);
        log.debug("Extension mapping done");

        List<Object> providers = new ArrayList<Object>();
        providers.add(new AuthenticationHandler());
        providers.add(new InputValidator());
        providers.add(new JacksonJsonProvider());
        this.factoryBean.setProviders(providers);
        log.debug("JSON provider set");

        this.factoryBean.setAddress(Configuration.INSTANCE.getProperty("application.server.address"));
        log.info("Server initialized and ready to start");

        return this;
    }

    public Server run() {
        Server server = this.factoryBean.create();
        log.info("Server is ready");

        return server;
    }
}
