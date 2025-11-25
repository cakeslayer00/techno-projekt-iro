package com.technopark.iro.dto;

import com.technopark.iro.model.NewsStatus;

public record NewsResponse(String title,
                           String content,
                           String imageUrl,
                           NewsStatus status) {
}
