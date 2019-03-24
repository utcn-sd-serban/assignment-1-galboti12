package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User entity);
    void remove(User entity);
    Optional<User> findById(int id);
    List<User> findAll();
    Optional<User> findByUserName(String username);
}
