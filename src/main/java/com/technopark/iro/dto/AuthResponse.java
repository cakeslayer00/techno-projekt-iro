package com.technopark.iro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing user details and access token")
public record AuthResponse(
        @Schema(
                description = "Authenticated username",
                example = "johndoe123"
        )
        String username,

        @Schema(
                description = "JWT access token for authenticated requests",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        String token) {
}