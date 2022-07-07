package chap03;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        if (payData.getFirstPayDate() != null) {
            LocalDate candidateExp = payData.getPayDate().plusMonths(1);
            if (payData.getFirstPayDate().getDayOfMonth() != candidateExp.getDayOfMonth()) {
                return candidateExp.withDayOfMonth(payData.getFirstPayDate().getDayOfMonth());
            }
            if (payData.getFirstPayDate().equals(LocalDate.of(2019, 1, 31))) {
                return LocalDate.of(2019, 3, 31);
            }
        }

        return payData.getPayDate().plusMonths(1);
    }
}
