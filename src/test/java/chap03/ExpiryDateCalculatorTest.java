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

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
        PayData payData = PayData.builder()
        .firstPayDate(LocalDate.of(2019,1,31))
        .payDate(LocalDate.of(2019,2,28))
        .payAmount(10_000).build();

        assertExpiryDate(payData,LocalDate.of(2019,3,31));

        PayData payData2 = PayData.builder()
                .firstPayDate(LocalDate.of(2019,1,30))
                .payDate(LocalDate.of(2019,2,28))
                .payAmount(10_000).build();

        assertExpiryDate(payData2,LocalDate.of(2019,3,30));

        PayData payData3 = PayData.builder()
                .firstPayDate(LocalDate.of(2019,5,31))
                .payDate(LocalDate.of(2019,6,30))
                .payAmount(10_000).build();

        assertExpiryDate(payData3,LocalDate.of(2019,7,31));
    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2022,3,1))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2022,5,1)
        );

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2022,3,1))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2022,6,1)
        );
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2019,1,31))
                        .payDate(LocalDate.of(2019,2,28))
                        .payAmount(20_000)
                        .build(),
                LocalDate.of(2019,4,30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2019,1,31))
                        .payDate(LocalDate.of(2019,2,28))
                        .payAmount(40_000)
                        .build(),
                LocalDate.of(2019,6,30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2019,3,31))
                        .payDate(LocalDate.of(2019,4,30))
                        .payAmount(30_000)
                        .build(),
                LocalDate.of(2019,7,31)
        );
    }

    @Test
    void 십만원을_납부하면_1년_제공() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2019,1,28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020,1,28)
        );
    }

    @Test
    void 윤달_마지막날_십만원_납부() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020,2,29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021,2,28)
        );
    }

    @Test
    void 십만원이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020,1,1))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2021,4,1)
        );

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020,1,1))
                        .payAmount(230_000)
                        .build(),
                LocalDate.of(2022,4,1)
        );

        assertExpiryDate(
                PayData.builder()
                        .payDate(LocalDate.of(2020,3,31))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2021,6,30)
        );
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_십만원이상_납부() {
        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020,1,1))
                        .payDate(LocalDate.of(2020,2,27))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021,2,27)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstPayDate(LocalDate.of(2020,1,1))
                        .payDate(LocalDate.of(2020,2,27))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2021,5,27)
        );
    }
}
