package chap03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(LocalDate payDate, int payAmount) {
        return payDate.plusMonths(1);
    }
}
