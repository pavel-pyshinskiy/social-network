package ru.pyshinskiy.socialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.Interest;
import ru.pyshinskiy.socialnetwork.repository.InterestRepository;

import java.sql.SQLException;
import java.util.Set;

@Component
@AllArgsConstructor
public class DefaultInterestService implements InterestService {

    private final InterestRepository interestRepository;

    @Override
    public Set<Interest> getAll() throws SQLException {
        return interestRepository.getAll();
    }
}
