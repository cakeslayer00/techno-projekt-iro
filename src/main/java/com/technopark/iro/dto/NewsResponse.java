package com.technopark.iro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.technopark.iro.model.NewsStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "News article response")
public record NewsResponse(@Schema(description = "News ID", example = "1")
                           Long id,

                           @Schema(description = "News title", example = "BREAKING NEWS!")
                           String title,

                           @Schema(description = "News content", example = "There's new Education Block opening up in University!")
                           String content,

                           @Schema(description = "Creation timestamp", example = "2025-01-15")
                           @JsonFormat(pattern = "yyyy-MM-dd")
                           @JsonProperty("created_at")
                           LocalDate createdAt,

                           @Schema(description = "Image URL", example = "https://storage.com/image.jpg")
                           @JsonProperty("image_url")
                           String imageUrl,

                           @Schema(description = "News status", example = "DRAFT")
                           NewsStatus status) {
}