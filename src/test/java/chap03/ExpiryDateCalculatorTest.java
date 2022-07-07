package chap03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

/*
* 매달 비용을 지불하는 유료 서비스
* 서비스 사용을 위해 매달 1만원을 선불로 납부한다. 납부일 기준 한달 뒤가 만료일
* 2개월 이상 요금을 납부할 수 있다
* 10만 원을 납부하면 서비스를 1년 제공
*/
public class ExpiryDateCalculatorTest {

    private void assertExpiryDate(LocalDate payDate, int payAmount, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payDate, payAmount);

        assertEquals(expectedExpiryDate,realExpiryDate);
    }

    @Test
    void expiryDate_pay_10000won_then_one_month_later() {
        assertExpiryDate(LocalDate.of(2022,7,1),10000,LocalDate.of(2022,8,1));
        assertExpiryDate(LocalDate.of(1999,1,1),10000,LocalDate.of(1999,2,1));
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(LocalDate.of(2022,1,31),10000,
                LocalDate.of(2022,2,28));
        assertExpiryDate(LocalDate.of(2022,5,31),10000,
                LocalDate.of(2022,6,30));
        assertExpiryDate(LocalDate.of(2020,1,31),10000,
                LocalDate.of(2020,2,29));
    }


}
