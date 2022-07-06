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
import java.util.*;

@Component
@AllArgsConstructor
public class DefaultUserRepository implements UserRepository {

    private final DataSource dataSource;

    private final InterestRepository interestRepository;

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        String FIND_BY_EMAIL_SQL_QUERY = "SELECT * FROM users WHERE email=?";

        User user;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_BY_EMAIL_SQL_QUERY)) {
            pst.setString(1, email);
            user = extractUser(pst);
        }
        if(user != null) {
            user.setInterests(interestRepository.getAll(user.getId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() throws SQLException {
        String FIND_ALL_SQL_QUERY = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL_SQL_QUERY);
             ResultSet rs = pst.executeQuery()
        ) {
            User user;
            while(rs.next()) {
                user = new User();
                user.setId(rs.getLong( "id"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString( "email"));
                user.setFirstName(rs.getString( "firstname"));
                user.setLastName(rs.getString( "lastname"));
                user.setSex(Sex.valueOf(rs.getString( "sex")));
                user.setInterests(interestRepository.getAll(user.getId()));
                users.add(user);
            }

        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String FIND_BY_EMAIL_SQL_QUERY = "SELECT * FROM users WHERE id=?";
        User user;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_BY_EMAIL_SQL_QUERY)) {
            pst.setLong(1, id);
            user = extractUser(pst);
        }
        if(user != null) {
            user.setInterests(interestRepository.getAll(user.getId()));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) throws SQLException {
        final String SAVE_USER_SQL_QUERY = "INSERT INTO users (email, password, firstname, lastname, sex) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SAVE_USER_SQL_QUERY)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFirstName());
            pst.setString(4, user.getLastName());
            pst.setString(5, user.getSex().toString());
            pst.executeUpdate();
        }
        Optional<User> savedUser = findByEmail(user.getEmail());
        final String LINK_INTEREST_TO_USER_SQL_QUERY = "INSERT INTO interest_to_user (interest_id, user_id)" +
                " VALUES (?, ?)";
        for(Interest interest : user.getInterests()) {
            try (Connection con = dataSource.getConnection();
                 PreparedStatement pst = con.prepareStatement(LINK_INTEREST_TO_USER_SQL_QUERY)) {
                pst.setLong(1, interest.getId());
                pst.setLong(2, savedUser.get().getId());
                pst.executeUpdate();
            }
        }
        return user;
    }

    @Override
    public User update(User user) throws SQLException {
        String SAVE_USER_SQL_QUERY = "UPDATE users SET email=?, password=?, firstname=?, lastname=?, sex=? WHERE id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SAVE_USER_SQL_QUERY)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFirstName());
            pst.setString(4, user.getLastName());
            pst.setString(5, user.getSex().toString());
            pst.setLong(6, user.getId());
            pst.executeUpdate();
        }
        return user;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String SAVE_USER_SQL_QUERY = "DELETE FROM users WHERE id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SAVE_USER_SQL_QUERY)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        }
    }

    private User extractUser(PreparedStatement pst) throws SQLException {
        User user;
        try (ResultSet rs = pst.executeQuery()) {
            rs.next();
            user = new User();
            user.setId(rs.getLong( "id"));
            user.setEmail(rs.getString( "email"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString( "firstname"));
            user.setLastName(rs.getString( "lastname"));
            user.setSex(Sex.valueOf(rs.getString( "sex")));
        }
        return user;
    }
}
