package lk.inli.rest.model;

public class PerfectNumber {
    private Long number;
    private Boolean isPerfect;

    public PerfectNumber() {
    }

    public PerfectNumber(Long number, Boolean isPerfect) {
        this.number = number;
        this.isPerfect = isPerfect;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Boolean getPerfect() {
        return isPerfect;
    }

    public void setPerfect(Boolean perfect) {
        isPerfect = perfect;
    }
}
