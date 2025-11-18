package com.technopark.iro.service.impl;

import com.technopark.iro.dto.AuthRequestDto;
import com.technopark.iro.dto.AuthResponseDto;
import com.technopark.iro.model.Role;
import com.technopark.iro.model.entity.User;
import com.technopark.iro.repository.UserRepository;
import com.technopark.iro.service.AuthService;
import com.technopark.iro.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.username(),
                        authRequestDto.password())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDto(authRequestDto.username(), token);
    }

    @Override
    public void register(AuthRequestDto authRequestDto) {
        User user = new User();
        user.setUsername(authRequestDto.username());
        user.setPassword(passwordEncoder.encode(authRequestDto.password()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }

}
