package chap07.register;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;


public class UserRegisterTest {
    private UserRegister userRegister;
    private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();
    private EmailNotifier mockEmailNotifier = new Mockito().mock(EmailNotifier.class);

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker,fakeRepository,mockEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    public void weakPassword() throws Exception{
        //give
        stubWeakPasswordChecker.setWeak(true);
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw"))
                .willReturn(true);
        //when

        //then
        assertThrows(WeakPasswordException.class, ()->{
            userRegister.register("id", "pw", "email");
        });
    }
//
//    @DisplayName("약한 암호가 아니면 가입 성공")
//    @Test
//    public void strongPassword() {
//        //give
//        stubWeakPasswordChecker.setWeak(false);
//        //when
//
//        //then
//        assertEquals(true,);
//    }
    @DisplayName("동일 ID 가진 회원존재하면 가입 실패")
    @Test
    public void dupIdExists() throws Exception{
        //give
        fakeRepository.save(new User("id","pw1","email@email.com"));
        //when

        //then
        assertThrows(DupIdException.class,()->{
            userRegister.register("id", "pw", "email");
        });
    }

    @DisplayName("동일 ID 가진 회원 존재하지 않으면 가입 설공")
    @Test
    public void noDupId_RegisterSuccess() {
        //give

        //when
        userRegister.register("id", "pw", "email");
        User savedUser = fakeRepository.findById("id");
        //then
        assertEquals("id",savedUser.getId());
        assertEquals("email",savedUser.getEmail());
    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    public void when_register_then_send_mail() {
        //give
        userRegister.register("id", "pw", "email@email.com");
        //when

        //then
        assertTrue(spyEmailNotifier.isCalled());
        assertEquals("email@email.com",spyEmailNotifier.getEmail());
    }
}
