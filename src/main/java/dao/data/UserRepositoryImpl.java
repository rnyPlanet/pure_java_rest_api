package dao.data;

import dao.models.dto.NewUser;
import dao.models.User;
import dao.repository.UserRepository;

import java.util.*;

public class UserRepositoryImpl implements UserRepository {

    Map<String, User> userMap = new HashMap<>();

    public UserRepositoryImpl() {
        String id = "1";
        userMap.put(id,
                User.builder()
                    .id(id)
                    .login(id)
                    .password(id)
                    .build()
        );
    }

    @Override
    public String create(NewUser newUser) {
        String id = UUID.randomUUID().toString();

        User user = User.builder()
                .id(id)
                .login(newUser.getLogin())
                .password(newUser.getPassword())
                .build();

        userMap.put(id, user);

        return id;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return userMap.entrySet().stream()
                .filter(it -> it.getKey().equals(id))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    @Override
    public void deleteById(String id) {
        userMap.remove(id);
    }
}
