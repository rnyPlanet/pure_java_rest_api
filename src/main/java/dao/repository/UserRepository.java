package dao.repository;

import dao.models.dto.NewUser;
import dao.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    String create(NewUser user);

    List<User> getAll();

    Optional<User> findById(String id);

    void deleteById(String id);
}
