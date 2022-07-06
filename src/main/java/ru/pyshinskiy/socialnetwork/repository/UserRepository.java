package ru.pyshinskiy.socialnetwork.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ru.pyshinskiy.socialnetwork.entity.User;

public interface UserRepository {

    Optional<User> findByEmail(String email) throws SQLException;

    List<User> getAll() throws SQLException;

    Optional<User> findById(Long id) throws SQLException;

    User save(User user) throws SQLException;

    User update(User user) throws SQLException;

    void delete(Long id) throws SQLException;
}
