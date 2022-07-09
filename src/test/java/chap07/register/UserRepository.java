package chap07.register;

public interface UserRepository {
    User findById(String id);
    void save(User user);
}
