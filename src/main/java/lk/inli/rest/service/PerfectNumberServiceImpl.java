package lk.inli.rest.service;

import lk.inli.rest.model.PerfectNumber;
import lk.inli.rest.model.PerfectNumbersInRange;
import lk.inli.rest.utils.PerfectNumberProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.List;

public class PerfectNumberServiceImpl implements PerfectNumberService {
    private static final Logger log = LogManager.getLogger(PerfectNumberServiceImpl.class);

    @Override
    public Response isPerfectNumber(Long input) {

        boolean isPerfect = PerfectNumberProcessor.isPerfectNumber(input);
        PerfectNumber perfectNumber = new PerfectNumber(input, isPerfect);

        return Response.status(Response.Status.OK).entity(perfectNumber).build();
    }

    @Override
    public Response getAllPerfectNumbers(Long start, Long end) {
        log.info("Finding perfect numbers from {} to {}", start, end);

        List<Long> perfectNumbers = PerfectNumberProcessor.getAllPerfectNumbersInRange(start, end);
        PerfectNumbersInRange numbersInRange = new PerfectNumbersInRange(start, end, perfectNumbers);

        return Response.status(Response.Status.OK).entity(numbersInRange).build();
    }

    @Override
    public Response heartbeat() {
        return Response.ok().build();
    }
}
