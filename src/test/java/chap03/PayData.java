package chap03;

import java.time.LocalDate;

public class PayData {
    private LocalDate firstPayDate;
    private LocalDate payDate;
    private int payAmount;

    private PayData() {}

    public PayData(LocalDate firstPayDate,LocalDate payDate, int payAmount) {
        this.firstPayDate =firstPayDate;
        this.payDate = payDate;
        this.payAmount = payAmount;
    }

    public LocalDate getFirstPayDate() {
        return firstPayDate;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private PayData data = new PayData();

        public Builder firstPayDate(LocalDate firstPayDate) {
            data.firstPayDate = firstPayDate;
            return this;
        }
        public Builder payDate(LocalDate payDate) {
            data.payDate = payDate;
            return this;
        }

        public Builder payAmount(int payAmount) {
            data.payAmount = payAmount;
            return this;
        }

        public PayData build() {
            return data;
        }
    }
}
