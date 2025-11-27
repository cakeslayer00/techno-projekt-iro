package com.technopark.iro.service;

import com.technopark.iro.dto.AuthRequest;
import com.technopark.iro.dto.AuthResponse;

public interface AuthService {

    AuthResponse authenticate(AuthRequest authRequest);

    AuthResponse register(AuthRequest authRequest);

}
