package ru.pyshinskiy.socialnetwork.service;

import ru.pyshinskiy.socialnetwork.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    List<User> getUsers() throws SQLException;

    User getUser(Long id) throws SQLException;

    User save(User user) throws SQLException;

    User update(User user) throws SQLException;

    void delete(Long id) throws SQLException;
}
