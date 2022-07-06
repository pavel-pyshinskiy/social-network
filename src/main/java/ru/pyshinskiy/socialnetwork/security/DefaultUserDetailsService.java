package ru.pyshinskiy.socialnetwork.security;

import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.pyshinskiy.socialnetwork.entity.User;
import ru.pyshinskiy.socialnetwork.repository.UserRepository;

@Component
@AllArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userRes = userRepository.findByEmail(email);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with email = " + email);
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
