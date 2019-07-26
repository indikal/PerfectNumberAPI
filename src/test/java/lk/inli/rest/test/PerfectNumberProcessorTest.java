package lk.inli.rest.test;

import lk.inli.rest.utils.PerfectNumberProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PerfectNumberProcessorTest {
    private static final Logger log = LogManager.getLogger(PerfectNumberProcessorTest.class);

    @Test
    public void testValidPerfectNumber() {
        log.info("Test for a valid perfect number");
        assertTrue(PerfectNumberProcessor.isPerfectNumber(28L));
    }

    @Test
    public void testInvalidPerfectNumber() {
        log.info("Test for an invalid perfect number");
        assertFalse(PerfectNumberProcessor.isPerfectNumber(29L));
    }

    @Test
    public void testPerfectNumbersInRange() {
        log.info("Test for perfect numbers between 1 to 29");
        List<Long> numbersInRange = PerfectNumberProcessor.getAllPerfectNumbersInRange(1L, 29L);

        assertEquals(2, numbersInRange.size());
        assertEquals(6, numbersInRange.get(0).longValue());
        assertEquals(28, numbersInRange.get(1).longValue());
    }
}
