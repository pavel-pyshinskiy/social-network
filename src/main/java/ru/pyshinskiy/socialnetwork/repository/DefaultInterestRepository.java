package ru.pyshinskiy.socialnetwork.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.Interest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class DefaultInterestRepository implements InterestRepository {

    private final DataSource dataSource;

    @Override
    public Set<Interest> getAll() throws SQLException {
        String FIND_ALL_INTERESTS_SQL_QUERY = "SELECT * FROM interests";
        Set<Interest> interests = new HashSet<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_INTERESTS_SQL_QUERY);
             ResultSet rs = pst.executeQuery()
        ) {
            Interest interest;
            while(rs.next()) {
                interest = new Interest();
                interest.setId(rs.getLong( "id"));
                interest.setName(rs.getString( "name"));
                interests.add(interest);
            }
        }
        return interests;
    }

    @Override
    public Set<Interest> getAll(Long userId) throws SQLException {
        String FIND_INTERESTS_SQL_QUERY = "SELECT id, name FROM (SELECT interest_id FROM interest_to_user WHERE" +
                " user_id=?) iu INNER JOIN interests i ON iu.interest_id = i.id";
        Set<Interest> interests = new HashSet<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_INTERESTS_SQL_QUERY)
        ) {
            pst.setLong(1, userId);
            try(ResultSet rs = pst.executeQuery()) {
                Interest interest;
                while(rs.next()) {
                    interest = new Interest();
                    interest.setId(rs.getLong( "id"));
                    interest.setName(rs.getString( "name"));
                    interests.add(interest);
                }
            }
        }
        return interests;
    }
}
