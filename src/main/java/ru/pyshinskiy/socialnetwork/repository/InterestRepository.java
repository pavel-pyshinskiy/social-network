package ru.pyshinskiy.socialnetwork.repository;

import ru.pyshinskiy.socialnetwork.entity.Interest;

import java.sql.SQLException;
import java.util.Set;

public interface InterestRepository {

    Set<Interest> getAll() throws SQLException;

    Set<Interest> getAll(Long userId) throws SQLException;
}
