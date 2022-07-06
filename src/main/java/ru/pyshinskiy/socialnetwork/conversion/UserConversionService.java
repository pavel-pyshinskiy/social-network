package ru.pyshinskiy.socialnetwork.conversion;

import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.dto.UserDto;
import ru.pyshinskiy.socialnetwork.entity.User;

@Component
public class UserConversionService {

    public User convert(UserDto userDto) {
        return User.builder().email(userDto.getEmail())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .sex(userDto.getSex())
                .interests(userDto.getInterests())
                .id(userDto.getId())
                .build();
    }

    public UserDto convert(User user) {
        return UserDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .sex(user.getSex())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .interests(user.getInterests())
                .build();
    }
}
