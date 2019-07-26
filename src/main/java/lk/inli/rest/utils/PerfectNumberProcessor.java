package lk.inli.rest.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * This is a utility class to check if a given number is a perfect number and
 * to find all perfect numbers for a given range.
 *
 * A perfect number is a one where all divisors (excluding itself) sum up to the given number.
 **/
public class PerfectNumberProcessor {

    /**
     * This method will loop to the square root of num to determine if it is a perfect number.
     * When a number is a valid divisor it'll be added to the sum as well as it's inverse.
     * Eg: if the num is 28, and the test is 2, then add both 2 and 14 to the sum.
     * @param number The number to be tested
     * @return A boolean indicating whether the number is perfect or not
     */
    public static boolean isPerfectNumber(final Long number) {
        if (number == 1) return false;

        return LongStream.rangeClosed(2, (long) Math.sqrt(number))
                .reduce(1, (sum, test) -> number % test == 0 ? sum + test + (number / test) : sum) == number;
    }

    /**
     * This method will find all perfect numbers with in the given range of numbers including both start and end
     * numbers. It will use the isPerfectNumber method to test if a number is perfect or not.
     *
     * TODO: this algorithm is not perfect for very large number range and need improvement
     * @param start Range starts with this number
     * @param end Range ends with this number
     * @return A list of perfect numbers
     * */
    public static List<Long> getAllPerfectNumbersInRange(final Long start, final Long end) {
        return LongStream.rangeClosed(start, end)
                .parallel()
                .filter(PerfectNumberProcessor::isPerfectNumber)
                .boxed()
                .collect(Collectors.toList());
    }
}
