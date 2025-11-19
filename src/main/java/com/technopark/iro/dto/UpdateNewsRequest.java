package com.technopark.iro.dto;

import com.technopark.iro.model.NewsStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateNewsRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Content cannot be blank")
        String content,

        String imageUrl,

        NewsStatus status
) {}