package lk.inli.rest.model;

import java.util.ArrayList;
import java.util.List;

public class PerfectNumbersInRange {
    private Long rangeStart;
    private Long rangeEnd;
    private List<Long> perfectNumbers = new ArrayList<>();

    public PerfectNumbersInRange() {
    }

    public PerfectNumbersInRange(Long rangeStart, Long rangeEnd, List<Long> perfectNumbers) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.perfectNumbers = perfectNumbers;
    }

    public Long getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Long rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Long getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Long rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public List<Long> getPerfectNumbers() {
        return perfectNumbers;
    }

    public void setPerfectNumbers(List<Long> perfectNumbers) {
        this.perfectNumbers = perfectNumbers;
    }
}
