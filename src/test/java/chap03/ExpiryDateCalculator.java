package chap03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        return payData.getPayDate().plusMonths(1);
    }
}
