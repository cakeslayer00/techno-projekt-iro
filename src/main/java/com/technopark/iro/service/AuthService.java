package com.technopark.iro.service;

import com.technopark.iro.dto.AuthRequestDto;
import com.technopark.iro.dto.AuthResponseDto;

public interface AuthService {

    AuthResponseDto authenticate(AuthRequestDto authRequestDto);

    void register(AuthRequestDto authRequestDto);

}
