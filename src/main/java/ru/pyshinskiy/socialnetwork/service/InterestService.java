package ru.pyshinskiy.socialnetwork.service;

import ru.pyshinskiy.socialnetwork.entity.Interest;

import java.sql.SQLException;
import java.util.Set;

public interface InterestService {

    Set<Interest> getAll() throws SQLException;
}
