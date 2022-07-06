package ru.pyshinskiy.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyshinskiy.socialnetwork.conversion.UserConversionService;
import ru.pyshinskiy.socialnetwork.dto.UserDto;
import ru.pyshinskiy.socialnetwork.service.FriendshipService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

    private final UserConversionService conversionService;

    @PostMapping("/{userId}/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable("userId") Long userId,
                          @PathVariable("friendId") Long friendId) throws SQLException {
        friendshipService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable("userId") Long userId,
                             @PathVariable("friendId") Long friendId) throws SQLException {
        friendshipService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getFriends(@PathVariable("userId") Long userId) throws SQLException {
        return ResponseEntity.ok().body(friendshipService.getFriends(userId)
                .stream()
                .map(conversionService::convert)
                .collect(Collectors.toList()));
    }

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getRecommendations(@RequestBody UserDto user) throws SQLException {
        return ResponseEntity.ok().body(friendshipService.getRecommendations(conversionService.convert(user))
                .stream()
                .map(conversionService::convert)
                .collect(Collectors.toList()));
    }
}
