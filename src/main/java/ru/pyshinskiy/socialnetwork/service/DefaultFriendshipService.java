package ru.pyshinskiy.socialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.User;
import ru.pyshinskiy.socialnetwork.repository.FriendshipRepository;

import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class DefaultFriendshipService implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    @Override
    public List<User> getFriends(Long userId) throws SQLException {
        return friendshipRepository.getFriends(userId);
    }

    @Override
    public List<User> getRecommendations(User user) throws SQLException {
        return friendshipRepository.getRecommendations(user);
    }

    @Override
    public void addFriend(Long userId, Long friendId) throws SQLException {
        friendshipRepository.addFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) throws SQLException {
        friendshipRepository.deleteFriend(userId, friendId);
    }
}
