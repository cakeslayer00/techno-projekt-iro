package com.technopark.iro.dto;

import com.technopark.iro.model.NewsStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a new news article")
public record CreateNewsRequest(@NotBlank(message = "Title is required")
                                @Size(max = 255, message = "Title must not exceed 255 characters")
                                @Schema(description = "News title", example = "University Partnership Announcement", requiredMode = Schema.RequiredMode.REQUIRED)
                                String title,

                                @NotBlank(message = "Content is required")
                                @Schema(description = "News content", example = "We are pleased to announce...", requiredMode = Schema.RequiredMode.REQUIRED)
                                String content,

                                @NotBlank(message = "Image URL is required")
                                @Size(max = 512, message = "Image URL must not exceed 512 characters")
                                @Schema(description = "Image URL", example = "https://storage.com/image.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
                                String imageUrl,

                                @Schema(description = "News status", example = "DRAFT", requiredMode = Schema.RequiredMode.REQUIRED)
                                NewsStatus status) {
}
