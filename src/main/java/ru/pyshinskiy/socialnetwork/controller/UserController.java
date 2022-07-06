package ru.pyshinskiy.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pyshinskiy.socialnetwork.conversion.UserConversionService;
import ru.pyshinskiy.socialnetwork.dto.UserDto;
import ru.pyshinskiy.socialnetwork.service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final UserConversionService conversionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDto>> getAllUsers() throws SQLException {
        return ResponseEntity.ok().body(userService.getUsers()
                .stream()
                .map(conversionService::convert)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok().body(conversionService.convert(userService.getUser(id)));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) throws SQLException {
        return ResponseEntity.ok().body(conversionService.convert(userService.update(conversionService.convert(user))));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) throws SQLException {
        userService.delete(id);
    }

}
