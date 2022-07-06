package ru.pyshinskiy.socialnetwork.repository;

import ru.pyshinskiy.socialnetwork.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface FriendshipRepository {

    List<User> getFriends(Long userId) throws SQLException;

    List<User> getRecommendations(User user) throws SQLException;

    void addFriend(Long userId, Long friendId) throws SQLException;

    void deleteFriend(Long userId, Long friendId) throws SQLException;
}
