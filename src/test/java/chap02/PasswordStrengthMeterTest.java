package chap02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
* 암호검사기
* 규칙
* 1. 길이가 8글자 이상
* 2. 0부터 9사이의 숫자를 포함
* 3. 대문자 포함
*
* 3개 만족= 강함, 2개 만족 보통 1개 이하 만족 약함
* */


public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr,result);
    }

    @Test
    void meetsAllCriteria_then_Strong() {
        assertStrength("ab12!@AB",PasswordStrength.STRONG);
        assertStrength("abc1!@Add",PasswordStrength.STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("a1!@AB",PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_Number_Then_Normal() {
        assertStrength("ab!@ABqwer",PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_UpperCase_Then_Normal() {
        assertStrength("ab!@ab12qwer",PasswordStrength.NORMAL);
    }

    @Test
    void meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("abababqwer",PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyNumberCriteria_Then_Weak() {
        assertStrength("12345",PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyUppercaseCriteria_Then_Weak() {
        assertStrength("ABCD",PasswordStrength.WEAK);
    }

    @Test
    void meetsNoCriteria_Then_Weak() {
        assertStrength("abc",PasswordStrength.WEAK);
    }

    //예외적인 경우 고려

    @Test
    void nullInput_then_Invalid() {
        assertStrength(null,PasswordStrength.INVALID);
    }

    @Test
    void emptyInput_then_Invalid() {
        assertStrength("",PasswordStrength.INVALID);
    }


}
