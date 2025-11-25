package com.technopark.iro.dto;

import com.technopark.iro.model.NewsStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//TODO: apply same refactoring for news domain
public record CreateNewsRequest(@NotBlank(message = "Title cannot be empty")
                                   @Size(max = 255, message = "Title cannot exceed 255 characters")
                                   String title,

                                @NotBlank(message = "Content cannot be empty")
                                   String content,

                                @Size(max = 512, message = "Image URL is too long")
                                   String imageUrl,

                                NewsStatus status) {
}
