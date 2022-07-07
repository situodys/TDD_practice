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

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);

        assertEquals(expectedExpiryDate,realExpiryDate);
    }

    @Test
    void expiryDate_pay_10000won_then_one_month_later() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2022,7,1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022,8,1)
        );
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(1999,3,1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(1999,4,1)
        );
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음_만원_납부() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2022,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022,2,28)
        );
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2022,5,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2022,6,30)
        );
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020,1,31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020,2,29)
        );
    }


}
