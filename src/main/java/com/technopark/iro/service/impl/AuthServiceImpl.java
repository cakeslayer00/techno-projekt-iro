package com.technopark.iro.service.impl;

import com.technopark.iro.dto.AuthRequest;
import com.technopark.iro.dto.AuthResponse;
import com.technopark.iro.exception.InvalidUserCredentialsException;
import com.technopark.iro.exception.UserAlreadyExistsException;
import com.technopark.iro.model.entity.User;
import com.technopark.iro.repository.UserRepository;
import com.technopark.iro.service.AuthService;
import com.technopark.iro.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String ERR_USER_ALREADY_EXISTS = "User with username '%s' already exists";
    private static final String ERR_INVALID_USER_CREDENTIALS = "Invalid username or password";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.username(),
                            authRequest.password())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            log.info("User {} is authenticated", userDetails.getUsername());
            return new AuthResponse(authRequest.username(), token);
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException(ERR_INVALID_USER_CREDENTIALS);
        }
    }

    @Override
    @Transactional
    public AuthResponse register(AuthRequest authRequest) {
        if (userRepository.existsByUsername(authRequest.username())) {
            throw new UserAlreadyExistsException(ERR_USER_ALREADY_EXISTS.formatted(authRequest.username()));
        }

        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        userRepository.save(user);

        log.info("New user '{}' is created", user.getUsername());
        return authenticate(authRequest);
    }

}
