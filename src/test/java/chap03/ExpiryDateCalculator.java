package chap03;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {

        int addedMonths = payData.getPayAmount()/10_000;

        if (addedMonths >= 10) {
            return expiryDateOverTenThousandPay(payData, addedMonths);
        }
        if (payData.getFirstPayDate() != null) {
            return expiryDateUsingFirstPayDate(payData, addedMonths);
        }else{
            return payData.getPayDate().plusMonths(addedMonths);
        }
    }

    private LocalDate expiryDateOverTenThousandPay(PayData payData, int addedMonths) {
        int addedYears= addedMonths /10;
        int addedMonthExceptYear = addedMonths %10;
        return payData.getPayDate().plusYears(addedYears).plusMonths(addedMonthExceptYear);
    }

    private LocalDate expiryDateUsingFirstPayDate(PayData payData, int addedMonths) {
        LocalDate candidateExp = payData.getPayDate().plusMonths(addedMonths);
        final int dayOfFirstPay = payData.getFirstPayDate().getDayOfMonth();
        if (isSameDayOfMonth(candidateExp, dayOfFirstPay)) {
            return candidateExp;
        }
        return getCandidateDateWithModifiedDay(candidateExp, dayOfFirstPay);
    }

    private boolean isSameDayOfMonth(LocalDate candidateExp, int dayOfFirstPay) {
        return dayOfFirstPay == candidateExp.getDayOfMonth();
    }

    private LocalDate getCandidateDateWithModifiedDay(LocalDate candidateExp, int dayOfFirstPay) {
        final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth();
        if(dayLenOfCandiMon < dayOfFirstPay){
            return candidateExp.withDayOfMonth(dayLenOfCandiMon);
        }
        return candidateExp.withDayOfMonth(dayOfFirstPay);
    }
}
