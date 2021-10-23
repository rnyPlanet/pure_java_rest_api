package dao.service.impl;

import dao.models.dto.NewUser;
import errors.user.UserDoesNotExistException;
import lombok.Value;
import dao.models.User;
import dao.repository.UserRepository;
import dao.service.UserService;

import java.util.List;

@Value
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(NewUser newUser) {
        return userRepository.create(newUser);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
