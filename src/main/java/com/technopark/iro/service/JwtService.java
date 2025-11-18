package com.technopark.iro.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token);

    String extractUsername(String token);

}