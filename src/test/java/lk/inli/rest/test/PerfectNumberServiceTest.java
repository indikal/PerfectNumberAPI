package lk.inli.rest.test;

import lk.inli.rest.model.ErrorResponse;
import lk.inli.rest.model.PerfectNumber;
import lk.inli.rest.model.PerfectNumbersInRange;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PerfectNumberServiceTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(PerfectNumberServiceTest.class);

    @Test
    public void testNoQueryParam() throws IOException {
        HttpGet get = new HttpGet(BASE_URL);
        String credential = Base64.getEncoder().encodeToString( (USERNAME + ":" + PASSWORD).getBytes("UTF-8"));
        log.info("Authentication credentials {}", credential);
        get.setHeader("Authorization", "Basic " + credential);

        HttpResponse response = client.execute(get);
        int respCode = response.getStatusLine().getStatusCode();

        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse message = mapper.readValue(response.getEntity().getContent(), ErrorResponse.class);

        assertEquals(400, respCode);
        assertEquals(400, message.getStatusCode());
    }

    @Test
    public void testValidPerfectNumber() throws IOException {
        HttpGet get = new HttpGet(BASE_URL + "/28");
        String credential = Base64.getEncoder().encodeToString( (USERNAME + ":" + PASSWORD).getBytes("UTF-8"));
        log.info("Authentication credentials {}", credential);
        get.setHeader("Authorization", "Basic " + credential);

        HttpResponse response = client.execute(get);

        ObjectMapper mapper = new ObjectMapper();
        PerfectNumber message = mapper.readValue(response.getEntity().getContent(), PerfectNumber.class);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertTrue(message.getPerfect());
    }

    @Test
    public void testValidRange() throws IOException {
        HttpGet get = new HttpGet(BASE_URL + "?start=1&end=28");
        String credential = Base64.getEncoder().encodeToString( (USERNAME + ":" + PASSWORD).getBytes("UTF-8"));
        log.info("Authentication credentials {}", credential);
        get.setHeader("Authorization", "Basic " + credential);

        HttpResponse response = client.execute(get);

        ObjectMapper mapper = new ObjectMapper();
        PerfectNumbersInRange message = mapper.readValue(response.getEntity().getContent(), PerfectNumbersInRange.class);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertTrue(message.getPerfectNumbers().size() > 0);
    }

    @Test
    public void testHeartBeatSuccess() throws IOException {
        HttpGet get = new HttpGet(BASE_URL + "/ping");
        String credential = Base64.getEncoder().encodeToString( (USERNAME + ":" + PASSWORD).getBytes("UTF-8"));
        log.info("Authentication credentials {}", credential);
        get.setHeader("Authorization", "Basic " + credential);

        HttpResponse response = client.execute(get);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testAuthenticationFailure() throws IOException {
        HttpGet get = new HttpGet(BASE_URL + "/ping");
        String credential = Base64.getEncoder().encodeToString( (USERNAME + "123:" + PASSWORD).getBytes("UTF-8"));
        log.info("Authentication credentials {}", credential);
        get.setHeader("Authorization", "Basic " + credential);

        HttpResponse response = client.execute(get);
        assertEquals(401, response.getStatusLine().getStatusCode());
    }
}
