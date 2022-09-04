package ru.pyshinskiy.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.pyshinskiy.socialnetwork.entity.Interest;
import ru.pyshinskiy.socialnetwork.enumeration.Sex;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Sex sex;

    private String city;

    private Set<Interest> interests;
}
