package ru.pyshinskiy.socialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pyshinskiy.socialnetwork.conversion.UserConversionService;
import ru.pyshinskiy.socialnetwork.dto.UserDto;
import ru.pyshinskiy.socialnetwork.security.jwt.JWTUtil;
import ru.pyshinskiy.socialnetwork.service.UserService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;
    private final UserConversionService conversionService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody UserDto user) throws SQLException {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = conversionService.convert(userService.save(conversionService.convert(user)));
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody UserDto body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }
}

