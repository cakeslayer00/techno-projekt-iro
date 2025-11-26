package com.technopark.iro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Authentication request containing user credentials")
public record AuthRequest(@NotBlank(message = "Username must contain at least 8 symbols")
                          @Size(min = 8, max = 255)
                          @Schema(
                                  description = "Username for authentication",
                                  example = "john_denver135",
                                  minLength = 8,
                                  maxLength = 255
                          )
                          String username,

                          @NotBlank(message = "Password must contain at least 8 symbols")
                          @Size(min = 8, max = 255)
                          @Schema(
                                  description = "User password",
                                  example = "securePassword123",
                                  minLength = 8,
                                  maxLength = 255
                          )
                          String password) {
}