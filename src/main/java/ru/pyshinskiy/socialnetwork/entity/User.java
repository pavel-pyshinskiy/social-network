package ru.pyshinskiy.socialnetwork.entity;

import java.util.Set;

import lombok.*;
import ru.pyshinskiy.socialnetwork.enumeration.Sex;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Sex sex;

    private Set<Interest> interests;

    private Set<User> friends;
}
