package ru.pyshinskiy.socialnetwork.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.Interest;
import ru.pyshinskiy.socialnetwork.entity.User;
import ru.pyshinskiy.socialnetwork.enumeration.Sex;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class DefaultFriendshipRepository implements FriendshipRepository {

    private final DataSource dataSource;

    private final InterestRepository interestRepository;

    @Override
    public List<User> getFriends(Long userId) throws SQLException {
        String FIND_ALL_FRIEND_SQL_QUERY = "select * from (select friend_id from friends where user_id=?) f " +
                "inner join users on users.id = f.friend_id";
        List<User> users = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_FRIEND_SQL_QUERY)
        ) {
            pst.setLong(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                User user;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setSex(Sex.valueOf(rs.getString("sex")));
                    user.setInterests(interestRepository.getAll(user.getId()));
                    users.add(user);
                }
            }

        }
        return users;
    }

    @Override
    public List<User> getRecommendations(User incomeUser) throws SQLException {
        String placeholders = String.join(",", Collections.nCopies(incomeUser.getInterests().size(), "?"));
        String FIND_ALL_FRIEND_SQL_QUERY = "select * from (" +
                "select user_id from (" +
                "        select id" +
                "        from interests" +
                "        where name IN ("+ placeholders +")" +
                "    ) i inner join interest_to_user iu on i.id = iu.interest_id" +
                ") ui inner join users u on ui.user_id = u.id WHERE user_id <> ?";
        List<User> users = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_FRIEND_SQL_QUERY)
        ) {
            int i = 1;
            for(Interest interest : incomeUser.getInterests()) {
                pst.setString(i++, interest.getName());
            }
            pst.setLong(i, incomeUser.getId());
            try (ResultSet rs = pst.executeQuery()) {
                User user;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setSex(Sex.valueOf(rs.getString("sex")));
                    user.setInterests(interestRepository.getAll(user.getId()));
                    users.add(user);
                }
            }

        }
        return users;
    }

    @Override
    public void addFriend(Long userId, Long friendId) throws SQLException {
        String ADD_FRIEND_SQL_QUERY = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(ADD_FRIEND_SQL_QUERY)) {
            pst.setLong(1, userId);
            pst.setLong(2, friendId);
            pst.executeUpdate();
        }

        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(ADD_FRIEND_SQL_QUERY)) {
            pst.setLong(1, friendId);
            pst.setLong(2, userId);
            pst.executeUpdate();
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) throws SQLException {
        String DELETE_FRIEND_SQL_QUERY = "DELETE FROM friends WHERE user_id=? AND friend_id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(DELETE_FRIEND_SQL_QUERY)) {
            pst.setLong(1, userId);
            pst.setLong(2, friendId);
            pst.executeUpdate();
        }
    }
}
