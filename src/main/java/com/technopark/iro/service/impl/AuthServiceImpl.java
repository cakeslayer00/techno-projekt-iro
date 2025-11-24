package com.technopark.iro.service.impl;

import com.technopark.iro.dto.AuthRequest;
import com.technopark.iro.dto.AuthResponse;
import com.technopark.iro.model.entity.User;
import com.technopark.iro.repository.UserRepository;
import com.technopark.iro.service.AuthService;
import com.technopark.iro.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.username(),
                        authRequest.password())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        log.info("User {} is authenticated", userDetails.getUsername());
        return new AuthResponse(authRequest.username(), token);
    }

    @Override
    public void register(AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.username());
        user.setPassword(passwordEncoder.encode(authRequest.password()));

        log.info("New user '{}' is created", user.getUsername());
        userRepository.save(user);
    }

}
