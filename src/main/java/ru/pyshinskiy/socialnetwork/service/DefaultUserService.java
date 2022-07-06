package ru.pyshinskiy.socialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.User;
import ru.pyshinskiy.socialnetwork.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() throws SQLException {
        return userRepository.getAll();
    }

    @Override
    public User getUser(Long id) throws SQLException {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Could not findUser with id = " + id));
    }

    @Override
    public User save(User user) throws SQLException {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws SQLException {
        return userRepository.update(user);
    }

    @Override
    public void delete(Long id) throws SQLException {
        userRepository.delete(id);
    }
}
